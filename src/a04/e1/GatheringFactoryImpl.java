package a04.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class GatheringFactoryImpl implements GatheringFactory {

    @Override
    public <E> Gathering<E, List<E>> scanToList() {
        return new Gathering<E, List<E>>() {
            public Supplier<List<E>> produce(Supplier<E> supplier) {
                List<E> state = new ArrayList<>();
                return new Supplier<List<E>>() {

                    @Override
                    public List<E> get() {
                        state.add(supplier.get());
                        return new ArrayList<>(state);
                    }

                };

            }

        };
    }


    @Override
    public <E> Gathering<E, List<E>> slide(int size) {
        return new Gathering<E, List<E>>() {

            @Override
            public Supplier<List<E>> produce(Supplier<E> supplier) {
                List<E> state = new ArrayList<>();
                return new Supplier<List<E>>() {

                    @Override
                    public List<E> get() {
                        if (state.size() == size) {
                            state.remove(0);
                            state.add(supplier.get());
                        } else {
                            while (state.size() < size) {
                                state.add(supplier.get());
                            }
                        }
                        return new ArrayList<>(state);
                    }

                };
            }

        };
    }

    @Override
    public <E> Gathering<E, Pair<E, E>> pairs() {
        return new Gathering<E,Pair<E,E>>() {

            @Override
            public Supplier<Pair<E, E>> produce(Supplier<E> supplier) {
                return new Supplier<Pair<E,E>>() {
                    private E last;
                    @Override
                    public Pair<E, E> get() {
                        if(last == null){
                            last = supplier.get();
                        }
                        E current = supplier.get();
                        Pair<E,E> p = new Pair<>(last,current);
                        last = current;
                        return p;
                    }

                };
            }

        };
    }

    @Override
    public Gathering<Integer, Integer> sumLastThree() {
        Gathering<Integer, List<Integer>> slidingGathering = slide(3);
        return new Gathering<Integer,Integer>() {
            @Override
            public Supplier<Integer> produce(Supplier<Integer> supplier) {
                Supplier<List<Integer>> listSupplier = slidingGathering.produce(supplier);
                return new Supplier<Integer>() {
                     @Override
                    public Integer get() {
                        List<Integer> out = listSupplier.get();
                        return out.stream().mapToInt(Integer::intValue).sum();
                    }

                };
            }

        };
    }

    @Override
    public <E> Gathering<E, E> scanAndReduce(BinaryOperator<E> op) {
        Gathering<E,List<E>> slGathering = scanToList();
        return new Gathering<E,E>() {

            @Override
            public Supplier<E> produce(Supplier<E> supplier) {
                Supplier<List<E>> lSupplier = slGathering.produce(supplier);
                return new Supplier<E>() {

                    @Override
                    public E get() {
                        List<E> out = lSupplier.get();
                        E result = out.getFirst();
                        out.remove(0);
                        for (E e : out) {
                            result = op.apply(result, e);
                        }
                        return result;
                    }

                };
            }

        };
    }

}
