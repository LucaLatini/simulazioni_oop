package a05.e2;

import java.util.LinkedList;
import java.util.List;

public class LogicImpl implements Logic {
    private boolean over = false;
    private List<Position> marks = new LinkedList<>();
    private List<Position> centres = new LinkedList<>();

    @Override
    public boolean isOver() {
        return this.over;
    }

    @Override
    public void reset() {
        this.over = false;
        this.marks.clear();
        this.centres.clear();
    }

    @Override
    public boolean hit(Position position) {
        if (this.over) {
            return false;
        } else if (this.marks.contains(position)) {
            this.over = true;
            return false;
        } else if (this.isAligned(position)) {
            this.over = true;
            return false;
        } else {
            this.centres.add(position);
            this.marks.add(position);
            this.marks.add(new Position(position.x() + 1, position.y()));
            this.marks.add(new Position(position.x() + 2, position.y()));
            this.marks.add(new Position(position.x() - 1, position.y()));
            this.marks.add(new Position(position.x() - 2, position.y()));
            this.marks.add(new Position(position.x(), position.y() + 1));
            this.marks.add(new Position(position.x(), position.y() + 2));
            this.marks.add(new Position(position.x(), position.y() - 1));
            this.marks.add(new Position(position.x(), position.y() - 2));
            return true;
        }

    }


    private boolean isAligned(Position position) {
        for (Position centre : centres) {
            if (centre.x() == position.x() || centre.y() == position.y()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isMarked(Position position) {
        return this.marks.contains(position);
    }

}
