package a03b.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DependencyArrayFactoryImpl implements DependencyArrayFactory {

    private class ImmutableArray<E> implements DependencyArray<E> {

        private List<E> value;

        public ImmutableArray(List<E> initial) {
            this.value = new LinkedList<>(initial);

        }

        @Override
        public int size() {
            return this.value.size();
        }

        @Override
        public E read(int position) {
            return this.value.get(position);
        }

        @Override
        public void write(int position, Object value) {
            throw new UnsupportedOperationException("Unimplemented method 'write'");
        }

        @Override
        public List<E> elements() {
            return this.value;
        }

    }

    private class MutableArray<E> implements DependencyArray<E> {

        private List<E> value;

        public MutableArray(List<E> initial) {
            this.value = new LinkedList<>(initial);

        }

        @Override
        public int size() {
            return this.value.size();
        }

        @Override
        public E read(int position) {
            return this.value.get(position);
        }

        @Override
        public void write(int position, E value) {
            this.value.set(position, value);
        }

        @Override
        public List<E> elements() {
            return this.value;
        }

    }

    @Override
    public <E> DependencyArray<E> immutable(List<E> initial) {
        return new ImmutableArray<E>(initial);
    }

    @Override
    public <E> DependencyArray<E> mutable(List<E> initial) {
        return new MutableArray<>(initial);
    }

    @Override
    public DependencyArray<Integer> withSumOfElementsAtTheEnd(List<Integer> initial) {
        List<Integer> values = new LinkedList<>(initial);
        return new DependencyArray<Integer>() {
            @Override
            public int size() {
                return values.size() + 1;
            }

            @Override
            public Integer read(int position) {
                if (position == values.size()) {
                    return values.stream().mapToInt(i -> i).sum();
                } else {
                    return values.get(position);
                }
            }

            @Override
            public void write(int position, Integer value) {
                if (position == values.size()) {
                    throw new UnsupportedOperationException("Unimplemented method 'write'");
                } else {
                    values.set(position, value);
                }
            }

            @Override
            public List<Integer> elements() {
                List<Integer> out = new LinkedList<>(values);
                out.add(this.read(values.size()));
                return out;
            }

        };
    }

    @Override
    public <E> DependencyArray<E> clonedWithOneRandom(DependencyArray<E> array, int pos, List<E> randomElements) {
        List<E> values = new LinkedList<>(array.elements());
        return new DependencyArray<E>() {

            @Override
            public int size() {
                return values.size();
            }

            @Override
            public E read(int position) {
                Random random = new Random();
                int x = random.nextInt(randomElements.size());
                if(position == pos){
                    return randomElements.get(x);
                }
                else{
                    return values.get(position);
                }

            }

            @Override
            public void write(int position, E value) {
                if(position == pos){
                    throw new UnsupportedOperationException("Unimplemented method 'write'");
                }else{
                values.set(position, value);
                }
            }

            @Override
            public List<E> elements() {
                return values;
            }


        };
    }

    @Override
    public DependencyArray<Integer> clonedWithAddedProduct(DependencyArray<Integer> array) {
        List<Integer> values = new LinkedList<>(array.elements());
        return new DependencyArray<Integer>() {

            @Override
            public int size() {
                return values.size() + 1;
            }

            @Override
            public Integer read(int position) {
                if (position == values.size()) {
                    int product = 1;
                    for (int integer : values) {
                        product *= integer;
                    }
                    return product;
                } else {
                    return values.get(position);
                }
            }

            @Override
            public void write(int position, Integer value) {
                if (position == values.size()) {
                    throw new UnsupportedOperationException("Unimplemented method 'write'");
                } else {
                    values.set(position, value);
                }
            }

            @Override
            public List<Integer> elements() {
                List<Integer> out = new LinkedList<>(values);
                out.add(this.read(values.size()));
                return out;
            }

        };
    }
}
