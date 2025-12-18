package a01c.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class SimpleIteratorFactoryImpl implements SimpleIteratorFactory {

    @Override
    public SimpleIterator<Integer> naturals() {
        return new SimpleIterator<Integer>() {
            int current = 0;

            @Override
            public Integer next() {
                return current++;
            }

        };
    }

    @Override
    public <X> SimpleIterator<X> circularFromList(List<X> list) {
        return new SimpleIterator<X>() {
            Iterator<X> it = list.iterator();

            @Override
            public X next() {
                if (!it.hasNext()) {
                    it = list.iterator();
                }
                return it.next();
            }

        };
    }

    @Override
    public <X> SimpleIterator<X> cut(int size, SimpleIterator<X> simpleIterator) {
        return new SimpleIterator<X>() {
            int counter = size;

            @Override
            public X next() {
                while (counter > 0) {
                    counter--;
                    return simpleIterator.next();
                }
                throw new NoSuchElementException();

            }

        };
    }

    @Override
    public <X> SimpleIterator<Pair<X, X>> window2(SimpleIterator<X> simpleIterator) {
        return new SimpleIterator<Pair<X, X>>() {
            private X previous = simpleIterator.next();

            @Override
            public Pair<X, X> next() {
                X current = simpleIterator.next();
                var other = new Pair<X, X>(previous, current);
                previous = current;
                return other;
            }

        };
    }

    @Override
    public SimpleIterator<Integer> sumPairs(SimpleIterator<Integer> simpleIterator) {
        return new SimpleIterator<Integer>() {
            private int previous = simpleIterator.next();

            @Override
            public Integer next() {
                int current = simpleIterator.next();
                int sum = previous + current;
                previous = current;
                return sum;
            }

        };
    }

    @Override
    public <X> SimpleIterator<List<X>> window(int windowSize, SimpleIterator<X> simpleIterator) {
        return new SimpleIterator<List<X>>() {
            private LinkedList<X> buffer = new LinkedList<X>();
            // Quel for dentro alle graffe è un instance initializer dell’oggetto anonimo:
            // viene eseguito una sola volta quando crei il new SimpleIterator<List<X>>()
            // { ... }. È l’equivalente di un costruttore per la classe anonima
            // (le classi anonime non possono dichiarare un costruttore esplicito).
            // consumo windowsSize elementi

            {
                for (int i = 0; i < windowSize; i++) {
                    buffer.addLast(simpleIterator.next());
                }
            }

            @Override
            public List<X> next() {
                List<X> out = new ArrayList<>(buffer);
                X next = simpleIterator.next(); // windowSize+1
                buffer.removeFirst();
                buffer.addLast(next);
                return out;
            }

        };
    }

}
