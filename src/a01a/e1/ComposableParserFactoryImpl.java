package a01a.e1;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComposableParserFactoryImpl implements ComposableParserFactory {

    private static class ComposableParserImpl<T> implements ComposableParser<T> {
        private Set<Iterator<T>> iterators;

        public ComposableParserImpl(Set<Iterator<T>> iterators) {
            this.iterators = Set.copyOf(iterators);
        }

        @Override
        public boolean parse(T t) {
            iterators = iterators
                    .stream()
                    .filter(it -> it.hasNext())
                    .filter(it -> {
                        T next = it.next();
                        return next == null ? t == null : next.equals(t);
                    })
                    .collect(Collectors.toSet());
            return !iterators.isEmpty();
        }

        @Override
        public boolean end() {
            return iterators.stream().anyMatch(it -> !it.hasNext());
        }

    }

    @Override
    public <X> ComposableParser<X> empty() {
        return new ComposableParserImpl<X>(Set.of(Collections.<X>emptyList().iterator()));

    }

    @Override
    public <X> ComposableParser<X> one(X x) {
        return new ComposableParserImpl<X>(Set.of(List.of(x).iterator()));
    }

    @Override
    public <X> ComposableParser<X> fromList(List<X> list) {
        return new ComposableParserImpl<X>(Set.of(list.iterator()));
    }

    @Override
    public <X> ComposableParser<X> fromAnyList(Set<List<X>> input) {
        return new ComposableParserImpl<>(input.stream()
                .map(List::iterator)
                .collect(Collectors.toSet()));
    }

    @Override
    public <X> ComposableParser<X> seq(ComposableParser<X> parser, List<X> list) {
        // Set<Iterator<X>> set = asStream(parser)
        // .map(it -> new Iterator<X>() {
        // private final Iterator<X> first = it;
        // private final Iterator<X> second = list.iterator();

        // @Override
        // public boolean hasNext() {
        // return first.hasNext() || second.hasNext();
        // }

        // @Override
        // public X next() {
        // return first.hasNext() ? first.next() : second.next();
        // }
        // })
        // .collect(Collectors.toSet());
        // return new ComposableParserImpl<>(set);

        return new ComposableParser<X>() {
            Iterator<X> it = list.iterator();

            @Override
            public boolean parse(X t) {
                if (!parser.end()) {
                    return parser.parse(t);
                } else {
                    if (it.hasNext()) {
                        return t.equals(it.next());
                    } else {
                        return false;
                    }
                }
            }

            @Override
            public boolean end() {
                return parser.end() && !it.hasNext();
            }

        };
    }

    @Override
    public <X> ComposableParser<X> or(ComposableParser<X> p1, ComposableParser<X> p2) {
        // Set<Iterator<X>> set = Stream.concat(
        // ((ComposableParserImpl<X>) p1).iterators.stream(),
        // ((ComposableParserImpl<X>) p2).iterators.stream())
        // .collect(Collectors.toSet());
        // return new ComposableParserImpl<>(set);
        // }
        return new ComposableParser<X>() {
            @Override
            public boolean parse(X t) {
                boolean r1 = p1.parse(t);
                boolean r2 = p2.parse(t);
                return r1 || r2;
            }

            @Override
            public boolean end() {
                return p1.end() || p2.end();
            }
        };
    }
}
