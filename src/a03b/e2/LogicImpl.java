package a03b.e2;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LogicImpl implements Logic {
    private Map<Position, String> marks = new LinkedHashMap<>();
    private boolean over = false;
    private boolean fiveMarks = false;
    private int index = 0;

    @Override
    public void hit(Position position) {
        if (this.fiveMarks) {
            this.moveMarks();
        } else if (this.marks.containsKey(position)) {
            if (this.marks.get(position) == "up") {
                this.marks.put(position, "down");
            } else {
                this.marks.put(position, "up");
            }
        } else if (this.marks.size() < 5) {
            this.marks.put(position, "up");
            if (this.marks.size() == 5) {
                this.fiveMarks = true;
            }

        }
    }

    @Override
    public void reset() {
        this.over = false;
        this.fiveMarks = false;
        this.marks.clear();
        this.index = 0;
    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    private void moveMarks() {
        boolean moved = false;
        Position next;
        List<Position> pos = new LinkedList<>(marks.keySet());
        for (int i = 0; i < pos.size(); i++) {
            int curr = (index + i) % pos.size();
            Position p = pos.get(curr);
            if (this.marks.get(p).equals("up")) {
                next = new Position(p.x(), p.y() - 1);
            } else {
                next = new Position(p.x(), p.y() + 1);
            }
            if (!this.marks.containsKey(next) && next.y() >= 0 && next.y() < 10) {
                String value = this.marks.get(p);
                if ((this.marks.remove(p)) != null) {
                    this.marks.put(next, value);
                }
                index = (curr + 1) % pos.size();
                moved = true;
                break;
            }
        }
        if (!moved) {
            this.over = true;
        }
    }

    @Override
    public boolean isUp(Position position) {
        return (this.marks.get(position) == "up");
    }

    @Override
    public boolean isDown(Position position) {
        return (this.marks.get(position) == "down");
    }
}
