package a05.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SkippingFactoryImpl implements SkippingFactory {

    @Override
    public <E> Skipping<E> skipByPredicate(Predicate<E> predicate) {
        return new Skipping<E>() {

            @Override
            public Supplier<E> produce(Supplier<E> supplier) {
                return new Supplier<E>() {

                    @Override
                    public E get() {
                        E out;
                        do {
                            out = supplier.get();
                        } while (predicate.test(out));
                        return out;
                    }

                };
            }

        };
    }

    @Override
    public <E> Skipping<E> skipConsecutiveDuplicates() {
        return new Skipping<E>() {

            @Override
            public Supplier<E> produce(Supplier<E> supplier) {

                return new Supplier<E>() {
                    E last = null;
                    boolean first = true;

                    @Override
                    public E get() {
                        E out;
                        do {
                            out = supplier.get();
                        } while (!first && last.equals(out));
                        last = out;
                        first = false;
                        return out;
                    }

                };
            }

        };
    }

    @Override
    public <E> Skipping<E> skipAndRetain(int skip, int retain) {
        return new Skipping<E>() {

            @Override
            public Supplier<E> produce(Supplier<E> supplier) {
                return new Supplier<E>() {

                    boolean skipping = true; // stato: true = SKIP, false = RETAIN
                    int count = 0; // contatore dello stato corrente

                    @Override
                    public E get() {
                        // FASE SKIP: consuma elementi senza restituirli
                        if (skipping) {
                            for (int i = 0; i < skip; i++) {
                                supplier.get(); // scartati
                            }
                            skipping = false;
                            count = 0; // reset contatore per RETAIN
                        }

                        // FASE RETAIN: restituisce elementi
                        E out = supplier.get();
                        count++;

                        if (count == retain) {
                            skipping = true;
                            count = 0; // reset contatore per SKIP
                        }

                        return out;
                    }
                };
            }
        };
    }

    @Override
    public Skipping<Integer> skipUntilSumReaches(int sum) {
        return new Skipping<Integer>() {

            @Override
            public Supplier<Integer> produce(Supplier<Integer> supplier) {
                return new Supplier<Integer>() {
                    List<Integer> state = new LinkedList<>();
                    @Override
                    public Integer get() {
                       do{
                        state.add(supplier.get());

                       }while(state.stream().mapToInt(Integer::intValue).sum() < sum);

                       int out = state.getLast();
                       state.clear();
                       return out;
                    }

                };
            }


        };
    }

}
