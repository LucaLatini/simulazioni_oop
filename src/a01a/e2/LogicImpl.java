package a01a.e2;

import java.util.HashSet;
import java.util.Set;

public class LogicImpl implements Logic {

    private Set<Position> marks = new HashSet<>();
    private boolean over = false;

    private int minX() {
        return marks.stream().mapToInt(Position::x).min().getAsInt() - 1;
    }

    private int maxX() {
        return marks.stream().mapToInt(Position::x).max().getAsInt() + 1;
    }

    private int minY() {
        return marks.stream().mapToInt(Position::y).min().getAsInt() - 1;
    }

    private int maxY() {
        return marks.stream().mapToInt(Position::y).max().getAsInt() + 1;
    }

    @Override
    public boolean hit(Position position) {
        if (over) {
            this.reset();
            return false;
        } else {
            if (marks.contains(position)) {
                this.over = true;
                return false;
            } else {
                marks.add(position);
                return true;
            }
        }
    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    @Override
    public boolean isSelected(Position position) {
        if (this.over == false) {
            return marks.contains(position);
        } else {
            if (marks.isEmpty())
                return false;
            int minX = this.minX();
            int maxX = this.maxX();
            int minY = this.minY();
            int maxY = this.maxY();
            boolean onPerimeter = ((position.y() == minY || position.y() == maxY) && position.x() >= minX
                    && position.x() <= maxX)
                    || ((position.x() == minX || position.x() == maxX) && position.y() >= minY && position.y() <= maxY);
            return onPerimeter && !isVertex(position);
        }
    }

    @Override
    public boolean isVertex(Position position) {
        if (this.over && !marks.isEmpty()) {
            int minX = this.minX();
            int maxX = this.maxX();
            int minY = this.minY();
            int maxY = this.maxY();
            if (position.equals(new Position(maxX, maxY)) || position.equals(new Position(maxX, minY))
                    || position.equals(new Position(minX, maxY)) || position.equals(new Position(minX, minY))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void reset() {
        this.over = false;
        this.marks.clear();
    }
}
