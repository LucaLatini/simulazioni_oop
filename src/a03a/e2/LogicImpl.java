package a03a.e2;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LogicImpl implements Logic {

    private boolean over = false;
    private List<Position> obstacles = new LinkedList<>();
    private Random random = new Random();
    private List<Position> marks = new LinkedList<>();
    private boolean fiveMarks = false;
    private int index = 0;
    private int size;

    public LogicImpl(int size) {
        this.size = size;
        generateObstacles();

    }

    @Override
    public void hit(Position position) {
        if (this.fiveMarks) {
            this.descent();
        } else if (this.marks.contains(position)) {
        } else if (this.marks.size() < 5) {
            this.marks.add(position);
            if (this.marks.size() == 5) {
                this.fiveMarks = true;
            }
        }

    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    @Override
    public void reset() {
        this.fiveMarks = false;
        this.over = false;
        this.obstacles.clear();
        this.generateObstacles();
        this.marks.clear();
        this.index = 0;

    }

    @Override
    public boolean isObstacle(Position position) {
        return this.obstacles.contains(position);
    }

    @Override
    public void descent() {
        boolean moved = false;
        for (int i = 0; i < marks.size(); i++) {
            int curr = (index + i) % marks.size();
            Position p = marks.get(curr);
            Position next = new Position(p.x(), p.y() + 1);

            if (next.y() < this.size && !this.isObstacle(next) && !isContained(next)) {
                marks.set(curr, next);
                index = (curr + 1) % marks.size();
                moved = true;
                break;
            }
        }
        if (!moved) {
            this.over = true;
        }

    }

    @Override
    public boolean isContained(Position pos) {
        return this.marks.contains(pos);
    }

    private void generateObstacles() {
        while (this.obstacles.size() < 3) {
            int x = random.nextInt(this.size);
            int y = random.nextInt(this.size);
            Position p = new Position(x, y);

            this.obstacles.add(p);
        }
    }
}
