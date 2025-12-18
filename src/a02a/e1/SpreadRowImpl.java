package a02a.e1;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SpreadRowImpl implements SpreadRow {

    public SpreadRowImpl(int i) {

    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'size'");
    }

    @Override
    public boolean isFormula(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFormula'");
    }

    @Override
    public boolean isNumber(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNumber'");
    }

    @Override
    public boolean isEmpty(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public List<Optional<Integer>> computeValues() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'computeValues'");
    }

    @Override
    public void putNumber(int index, int number) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putNumber'");
    }

    @Override
    public void putSumOfTwoFormula(int resultIndex, int index1, int index2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putSumOfTwoFormula'");
    }

    @Override
    public void putMultiplyElementsFormula(int resultIndex, Set<Integer> indexes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putMultiplyElementsFormula'");
    }

}
