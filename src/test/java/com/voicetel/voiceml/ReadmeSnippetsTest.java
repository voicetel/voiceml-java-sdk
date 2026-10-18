package com.voicetel.voiceml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import static org.assertj.core.api.Assertions.assertThat;

/** C14: every ```java block in README.md compiles against this checkout. */
class ReadmeSnippetsTest {

    private static final Pattern BLOCK = Pattern.compile("```java\\n(.*?)```", Pattern.DOTALL);
    private static final Pattern DECLARES_CLIENT = Pattern.compile("\\bVoicemlClient\\s+client\\s*=");

    @TempDir
    Path work;

    @Test
    void readmeJavaExamplesCompile() throws IOException, URISyntaxException {
        String readme = Files.readString(Path.of("README.md"), StandardCharsets.UTF_8);
        Matcher m = BLOCK.matcher(readme);
        List<File> sources = new ArrayList<>();
        int n = 0;
        while (m.find()) {
            String code = m.group(1);
            n++;
            String cls = "ReadmeSnippet" + n;
            String unit;
            if (code.contains("public class ")) {
                unit = code.replaceFirst("public class \\w+", "public class " + cls);
            } else {
                StringBuilder imports = new StringBuilder();
                StringBuilder body = new StringBuilder();
                for (String line : code.split("\n", -1)) {
                    if (line.startsWith("import ")) {
                        imports.append(line).append('\n');
                    } else {
                        body.append("        ").append(line).append('\n');
                    }
                }
                String param = DECLARES_CLIENT.matcher(code).find() ? "" : "VoicemlClient client";
                unit = "import com.voicetel.voiceml.*;\nimport com.voicetel.voiceml.models.*;\n" + imports
                        + "public class " + cls + " {\n    static void run(" + param + ") throws Exception {\n"
                        + body + "    }\n}\n";
            }
            Path file = work.resolve(cls + ".java");
            Files.writeString(file, unit, StandardCharsets.UTF_8);
            sources.add(file.toFile());
        }
        assertThat(n).as("java blocks in README.md").isGreaterThanOrEqualTo(8);

        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        StringWriter out = new StringWriter();
        try (StandardJavaFileManager fm = javac.getStandardFileManager(null, null, StandardCharsets.UTF_8)) {
            Iterable<? extends JavaFileObject> units = fm.getJavaFileObjectsFromFiles(sources);
            List<String> options = List.of("-proc:none", "-d", work.resolve("out").toString(),
                    "-classpath", classpath());
            boolean ok = javac.getTask(out, fm, null, options, null, units).call();
            assertThat(ok).as("README snippets compile:\n" + out).isTrue();
        }
    }

    private static String classpath() throws URISyntaxException {
        List<String> entries = new ArrayList<>();
        for (Class<?> c : new Class<?>[] {VoicemlClient.class, ObjectMapper.class, JsonFactory.class, JsonProperty.class}) {
            entries.add(Path.of(c.getProtectionDomain().getCodeSource().getLocation().toURI()).toString());
        }
        return String.join(File.pathSeparator, entries);
    }
}
