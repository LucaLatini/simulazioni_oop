package a01b.e2;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

public class LogicImpl implements Logic {

    private Set<Position> marks = new HashSet<>();
    private boolean over = false;

    private Position Centre(Position position, Position position2) {
        return new Position(position.x(), (position.y() - position2.y()) / 2);
    }
    @Override
    public boolean hit(Position position) {
        if (this.over) {
            this.reset();
            return false;
        } else if (marks.size() == 2) {
            this.over = true;
            return false;
        } else if (marks.isEmpty()) {
            marks.add(position);
            return true;
        } else if (!marks.stream().anyMatch(p -> p.x() == position.x() &&
                Math.abs(p.y() - position.y()) % 2 == 0)) {
            this.over = true;
            return false;
        } else {
            marks.add(position);
            return true;
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
    public boolean isSelected(Position value) {
        if (marks.size() != 2 || !this.over) {
            return false;
        } else {
            Position up = marks.stream()
                    .min(Comparator.comparing(Position::y))
                    .get();

            Position down = marks.stream()
                    .max(Comparator.comparing(Position::y))
                    .get();

            int vDiff = value.x() - value.y();
            int downDiff = down.x() - down.y();
            int upDiff = up.x() - up.y();

            int vSum = value.x() + value.y();
            int upSum = up.x() + up.y();
            int downSum = down.x() + down.y();

            boolean leftCond = (vDiff >= downDiff) && (vDiff <= upDiff);
            boolean rightCond = (vSum >= upSum) && (vSum <= downSum);

            return leftCond && rightCond;
        }
    }

}
