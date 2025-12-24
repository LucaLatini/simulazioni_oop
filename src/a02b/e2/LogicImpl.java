package a02b.e2;

import java.util.HashSet;
import java.util.Set;

public class LogicImpl implements Logic {
    boolean over = false;
    Set<Position> marks = new HashSet<>();

    @Override
    public boolean isDiagonal(Position position) {
        return (position.x() == position.y());
    }

    @Override
    public void hit(Position position) {
        if (this.isOver()) {
            this.reset();
        } else {
            if (this.marks.contains(position)) {
                this.marks.remove(position);
            } else if (this.marks.size() < 5) {
                this.marks.add(position);
                if(this.marks.size()==5){
                    this.over=true;
                }
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
    public Integer columnMarks(Position position) {
        return (int) this.marks.stream().filter(p -> p.x() == position.x()).count();
    }

    @Override
    public Integer rowMarks(Position position) {
        return (int) this.marks.stream().filter(p -> p.y() == position.y()).count();
    }

    @Override
    public boolean isMarked(Position position) {
        return this.marks.contains(position);
    }

}
