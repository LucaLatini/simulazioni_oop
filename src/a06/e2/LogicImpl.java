package a06.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicImpl implements Logic {

    private boolean over = false;
    private Map<Position, Integer> marks = new HashMap<>();
    private int size;

    public LogicImpl(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.marks.put(new Position(j, i), 0);
            }
        }

    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    @Override
    public void reset() {
        this.over = false;
        this.marks.clear();
    }

    @Override
    public Integer hit(Position position) {
        int value = this.marks.get(position);
        int newValue = (value % this.size) + 1;
        this.marks.replace(position, newValue);
        if (this.checkRow() && this.checkColumn()) {
            this.over = true;
        }
        return newValue;
    }

    private boolean checkRow() {

        for (int y = 0; y < this.size; y++) {
            List<Integer> values = new ArrayList<>();
            for (int x = 0; x < this.size; x++) {
                int val = this.marks.get(new Position(x, y));
                if (val == 0 || values.contains(val)) {
                    return false;
                }
                values.add(val);
            }

        }
        return true;
    }

    private boolean checkColumn() {

        for (int x = 0; x < this.size; x++) {
            List<Integer> values = new ArrayList<>();
            for (int y = 0; y < this.size; y++) {
                int val = this.marks.get(new Position(x, y));
                if (val == 0 || values.contains(val)) {
                    return false;
                }
                values.add(val);
            }

        }
        return true;
    }

}
