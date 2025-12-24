package a03a.e1;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;

public class CellsFactoryImpl implements CellsFactory {

    private class MutableValueCell<E> implements Cell<E> {

        private E value;

        public MutableValueCell(E initial) {
            this.value = initial;
        }

        @Override
        public E getResult() {
            return this.value;
        }

        @Override
        public boolean isModifiable() {
            return true;
        }

        @Override
        public Set<Cell<E>> dependsFrom() {
            return new HashSet<>();
        }

        @Override
        public void write(E value) {
            this.value = value;
        }

    }

    private class UnMutableValueCell<E> implements Cell<E> {

        private Set<Cell<E>> dependences;
        private BinaryOperator<E> sumOperator;
        private E zero;

        public UnMutableValueCell(Set<Cell<E>> dependences,
                E zero,
                BinaryOperator<E> sumOperator) {
            this.dependences = dependences;
            this.zero = zero;
            this.sumOperator = sumOperator;
        }

        @Override
        public E getResult() {
            E sum = zero;
            for (Cell<E> cell : dependences) {
                sum = sumOperator.apply(sum, cell.getResult());
            }
            return sum;
        }

        @Override
        public boolean isModifiable() {
            return false;
        }

        @Override
        public Set<Cell<E>> dependsFrom() {
            return this.dependences;
        }

        @Override
        public void write(E value) {
            throw new UnsupportedOperationException("Unimplemented method 'write'");
        }

    }

    @Override
    public <E> Cell<E> mutableValueCell(E initial) {
        return new MutableValueCell<>(initial);
    }

    @Override
    public Cell<Integer> sumOfTwo(Cell<Integer> c1, Cell<Integer> c2) {
        return new Cell<Integer>() {
            private Set<Cell<Integer>> dependences = Set.of(c1, c2);

            @Override
            public Integer getResult() {
                int sum = 0;
                for (Cell<Integer> cell : this.dependences) {
                    sum += cell.getResult();
                }
                return sum;
            }

            @Override
            public boolean isModifiable() {
                return false;
            }

            @Override
            public Set<Cell<Integer>> dependsFrom() {
                return this.dependences;
            }

            @Override
            public void write(Integer value) {
                throw new UnsupportedOperationException("Unimplemented method 'write'");
            }

        };
    }

    @Override
    public Cell<String> concatOfThree(Cell<String> c1, Cell<String> c2, Cell<String> c3) {
        return new Cell<String>() {

            private Set<Cell<String>> dependences = new LinkedHashSet<>(List.of(c1,c2,c3));

            @Override
            public String getResult() {
                String sum = "";
                for (Cell<String> cell : this.dependences) {
                    sum += cell.getResult();
                }
                return sum;
            }

            @Override
            public boolean isModifiable() {
                return false;
            }

            @Override
            public Set<Cell<String>> dependsFrom() {
                return this.dependences;
            }

            @Override
            public void write(String value) {
                throw new UnsupportedOperationException("Unimplemented method 'write'");
            }

        };
    }

    @Override
    public List<Cell<Integer>> cellsWithSumOnLast(List<Integer> values) {
        List<Cell<Integer>> out = new LinkedList<>();
        for (Integer value : values) {
            out.add(new MutableValueCell<Integer>(value));
        }
        int sum = 0;
        for (Cell<Integer> cell : out) {
            sum += cell.getResult();
        }
        Set<Cell<Integer>> dependencies = new HashSet<>(out);
        out.add(new UnMutableValueCell<>(dependencies, 0, Integer::sum));
        return out;
    }

    @Override
    public List<Cell<String>> addConcatenationOnAll(List<Cell<String>> cellList) {
        List<Cell<String>> out = new LinkedList<>();
        for (Cell<String> value : cellList) {
            out.add(new MutableValueCell<String>(value.getResult()));
        }
        Set<Cell<String>> dependencies = new LinkedHashSet(out);
        out.add(new UnMutableValueCell<>(dependencies, "", String::concat));
        //  out.add(new Cell<String>() {
        //     @Override
        //     public String getResult() {
        //         String sum = "";
        //         for (Cell<String> cell : dependencies) {
        //             sum += cell.getResult();
        //         }
        //         return sum;
        //     }

        //     @Override
        //     public boolean isModifiable() {
        //         return false;
        //     }

        //     @Override
        //     public Set<Cell<String>> dependsFrom() {
        //         return dependencies;
        //     }

        //     @Override
        //     public void write(String value) {
        //         throw new UnsupportedOperationException("Unimplemented method 'write'");
        //     }

        //  });//
        return out;

    }

    //Non avrei saputo farlo.
    @Override
    public <E> Cell<E> fromReduction(List<Cell<E>> cellList, BinaryOperator<E> op) {
        return new Cell<E>() {
        // Le dipendenze sono tutte le celle nella lista
        private final Set<Cell<E>> dependencies = new LinkedHashSet<>(cellList);

        @Override
        public E getResult() {
            // Usiamo un iteratore perché la lista non è vuota (come da specifica)
            var iterator = cellList.iterator();
            E result = iterator.next().getResult(); // Partiamo dal primo elemento
            while (iterator.hasNext()) {
                // Applichiamo l'operatore tra il risultato parziale e la cella successiva
                result = op.apply(result, iterator.next().getResult());
            }
            return result;
        }

        @Override
        public boolean isModifiable() {
            return false;
        }

        @Override
        public Set<Cell<E>> dependsFrom() {
            return this.dependencies;
        }

        @Override
        public void write(E value) {
            throw new UnsupportedOperationException();
        }
    };
    }

}
