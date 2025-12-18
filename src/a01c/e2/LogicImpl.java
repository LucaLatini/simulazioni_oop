package a01c.e2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicImpl implements Logic {

    private List<Position> marks = new ArrayList<>();
    private boolean over = false;

    @Override
    public boolean hit(Position position) {
        if (this.over) {
            this.reset();
            return false;
        }
        if (this.isVertex(position)) {
            marks.add(position);
            if (this.marks.size() == 4) {
                this.over = true;
            }
            return true;
        } else {
            this.reset();
            return false;
        }

    }

    @Override
    public void reset() {
        this.marks.clear();
        this.over = false;
    }

    @Override
    public boolean isSelected(Position position) {
        if (!this.over || this.marks.size() != 4) {
            return false;
        }
        if (this.marks.contains(position)) {
            return false;
        }
        Position topLeft = this.marks.get(0);
        Position topRight = this.marks.get(1);
        Position bottomRight = this.marks.get(2);
        Position bottomLeft = this.marks.get(3);
        if ((position.x() > topLeft.x() && position.x() < topRight.x())
                &&
                (position.y() > topLeft.y() && position.y() < bottomLeft.y())

        ) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean isVertex(Position position) {
        if (this.marks.contains(position)) {
            return false;
        }
        switch (this.marks.size()) {
            case 0:
                return true;
            case 1:
                Position topLeft = this.marks.get(0);
                if (position.y() == topLeft.y() && position.x() > topLeft.x()) {
                    return true;
                }else{
                    return false;
                }
            case 2:
                Position topRight = this.marks.get(1);
                if (position.x() == topRight.x() && position.y() > topRight.y()) {
                    return true;
                }else{
                    return false;
                }
            case 3:
                Position topLeft2 = this.marks.get(0);
                Position bottomRight = this.marks.get(2);
                return position.y() == bottomRight.y() && position.x() == topLeft2.x();
            default:
                return false;
        }
    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    @Override
    public int Size() {
        return this.marks.size();
    }

}
