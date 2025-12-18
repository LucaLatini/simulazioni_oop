package a01b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;

public class IteratorsCombinersImpl implements IteratorsCombiners {

    @Override
    public <X> Iterator<X> alternate(Iterator<X> i1, Iterator<X> i2) {
        List<X> it = new ArrayList<>();
        while (i1.hasNext() && i2.hasNext()) {
            it.add(i1.next());
            it.add(i2.next());
        }
        if (!i1.hasNext()) {
            while (i2.hasNext()) {
                it.add(i2.next());

            }
        } else if (!i2.hasNext()) {
            while (i1.hasNext()) {
                it.add(i1.next());
            }

        }
        return it.iterator();

    }

    @Override
    public <X> Iterator<X> seq(Iterator<X> i1, Iterator<X> i2) {
        return new Iterator<X>() {
            @Override
            public boolean hasNext() {
                return i1.hasNext() || i2.hasNext();
            }

            @Override
            public X next() {
                return i1.hasNext() ? i1.next() : i2.next();
            }
        };
    }

    @Override
    public <X> Iterator<X> map2(Iterator<X> i1, Iterator<X> i2, BinaryOperator<X> operator) {
        List<X> it = new ArrayList<>();
        while (i1.hasNext() && i2.hasNext()) {
            it.add(operator.apply(i1.next(), i2.next()));
        }
        return it.iterator();
    }

    @Override
    public <X, Y, Z> Iterator<Pair<X, Y>> zip(Iterator<X> i1, Iterator<Y> i2) {
        List<Pair<X, Y>> it = new ArrayList<>();
        while (i1.hasNext() && i2.hasNext()) {
            it.add(new Pair<X, Y>(i1.next(), i2.next()));
        }
        return it.iterator();
    }

}
