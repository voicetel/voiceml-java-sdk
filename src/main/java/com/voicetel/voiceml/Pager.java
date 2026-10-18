package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.PaginationException;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Lazy auto-pagination over a list endpoint.
 *
 * <p>Nothing is requested until iteration starts. Each page is fetched only when the previous
 * one has been consumed, by following the link the server returned ({@code next_page_uri} on
 * {@code /2010-04-01} lists, {@code meta.next_page_url} on {@code /v1} lists) verbatim, so a
 * {@code PageToken} in it is carried forward. Iteration ends when the link is null or empty, or a
 * page comes back empty; a link that repeats the one just followed raises
 * {@link PaginationException} instead of looping. Stopping early (a {@code break}, or
 * {@link Stream#limit(long)} on {@link #stream()}) sends no further requests. No more than one
 * page is held in memory.
 *
 * <p>Each call to {@link #iterator()} or {@link #stream()} starts again from the first page.
 *
 * @param <T> item type
 */
public final class Pager<T> implements Iterable<T> {

    private final Supplier<? extends Iterator<T>> iterators;

    private Pager(Supplier<? extends Iterator<T>> iterators) {
        this.iterators = iterators;
    }

    /**
     * Build a pager from page-level operations.
     *
     * @param firstPage fetches the first page (called once per iteration)
     * @param items extracts the items of a page
     * @param nextLink extracts the next-page link of a page ({@code null} or empty when done)
     * @param fetchLink fetches the page a link points at
     * @param <P> page type
     * @param <T> item type
     * @return the pager
     */
    public static <P, T> Pager<T> create(Supplier<P> firstPage,
                                         Function<? super P, List<T>> items,
                                         Function<? super P, String> nextLink,
                                         Function<String, P> fetchLink) {
        Objects.requireNonNull(firstPage, "firstPage");
        Objects.requireNonNull(items, "items");
        Objects.requireNonNull(nextLink, "nextLink");
        Objects.requireNonNull(fetchLink, "fetchLink");
        return new Pager<>(() -> new PageIterator<>(firstPage, items, nextLink, fetchLink));
    }

    @Override
    public Iterator<T> iterator() {
        return iterators.get();
    }

    /**
     * A sequential, lazy stream over every item.
     *
     * @return the stream
     */
    public Stream<T> stream() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator(), Spliterator.ORDERED),
                false);
    }

    private static final class PageIterator<P, T> implements Iterator<T> {
        private final Supplier<P> firstPage;
        private final Function<? super P, List<T>> items;
        private final Function<? super P, String> nextLink;
        private final Function<String, P> fetchLink;

        private P page;
        private Iterator<T> current = Collections.emptyIterator();
        private String lastLink;
        private boolean started;
        private boolean done;

        PageIterator(Supplier<P> firstPage, Function<? super P, List<T>> items,
                     Function<? super P, String> nextLink, Function<String, P> fetchLink) {
            this.firstPage = firstPage;
            this.items = items;
            this.nextLink = nextLink;
            this.fetchLink = fetchLink;
        }

        @Override
        public boolean hasNext() {
            while (!current.hasNext()) {
                if (done) {
                    return false;
                }
                if (!started) {
                    started = true;
                    page = firstPage.get();
                } else {
                    String link = page == null ? null : nextLink.apply(page);
                    if (link == null || link.isEmpty()) {
                        done = true;
                        return false;
                    }
                    if (link.equals(lastLink)) {
                        done = true;
                        throw new PaginationException("next page link does not advance: " + link);
                    }
                    lastLink = link;
                    page = fetchLink.apply(link);
                }
                List<T> pageItems = page == null ? null : items.apply(page);
                if (pageItems == null || pageItems.isEmpty()) {
                    done = true;
                    return false;
                }
                current = pageItems.iterator();
            }
            return true;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return current.next();
        }
    }
}
