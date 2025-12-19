package a02a.e1;

import java.lang.foreign.Linker.Option;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.*;

public class SpreadRowImpl implements SpreadRow {

    private interface Cell {
        Optional<Integer> calculate(List<Optional<Integer>> rowValues);
    }

    // 1. Cella Vuota (Semplice)
    private class EmptyCell implements Cell {
        @Override
        public Optional<Integer> calculate(List<Optional<Integer>> rowValues) {
            return Optional.empty();
        }
    }

    // 2. Cella Numero (Semplice)
    private class NumberCell implements Cell {
        private final int value;

        public NumberCell(int value) {
            this.value = value;
        }

        @Override
        public Optional<Integer> calculate(List<Optional<Integer>> rowValues) {
            return Optional.of(value);
        }
    }

    // 3. LA SVOLTA: Una sola classe Formula per tutto!
    // Questa classe accetta una "funzione" (la strategia) nel costruttore.
    private class GenericFormula implements Cell {
        // Usa una Function che prende la lista dei risultati e restituisce il risultato
        private final java.util.function.Function<List<Optional<Integer>>, Optional<Integer>> function;

        public GenericFormula(Function<List<Optional<Integer>>, Optional<Integer>> function) {
            this.function = function;
        }

        @Override
        public Optional<Integer> calculate(List<Optional<Integer>> rowValues) {
            // Delega il calcolo alla funzione che gli abbiamo passato
            return function.apply(rowValues);
        }
    }

    private List<Cell> cells = new LinkedList<>();;

    public SpreadRowImpl(int i) {
        for (int index = 0; index < i; index++) {
            this.cells.add(new EmptyCell());
        }
    }

    @Override
    public int size() {
        return this.cells.size();
    }

    @Override
    public boolean isFormula(int index) {
        return this.cells.get(index) instanceof GenericFormula;
    }

    @Override
    public boolean isNumber(int index) {
        return this.cells.get(index) instanceof NumberCell;
    }

    @Override
    public boolean isEmpty(int index) {
        return this.cells.get(index) instanceof EmptyCell;
    }

    @Override
    public List<Optional<Integer>> computeValues() {
        List<Optional<Integer>> result = new ArrayList<>();
        for (Cell cell : this.cells) {
            Optional<Integer> val = cell.calculate(result);
            result.add(val);

        }
        return result;
    }

    @Override
    public void putNumber(int index, int number) {
        this.cells.set(index, new NumberCell(number));
    }

    @Override
    public void putSumOfTwoFormula(int resultIndex, int index1, int index2) {

        this.cells.set(resultIndex, new GenericFormula(r -> {
            var n1 = r.get(index1);
            var n2 = r.get(index2);
            if (n1.isPresent() && n2.isPresent()) {
                return Optional.of(n1.get() + n2.get());
            } else {
                return Optional.empty();
            }

        }));
    }

    @Override
    public void putMultiplyElementsFormula(int resultIndex, Set<Integer> indexes) {

        this.cells.set(resultIndex, new GenericFormula( r -> {
            int moltiplication = 1;
            for (Integer index : indexes) {
                if(r.get(index).isPresent()){
                    moltiplication = moltiplication * (r.get(index).get());
                }else{
                    moltiplication = 0;
                }
            }
            return Optional.of(moltiplication);
        }));
    }

}
