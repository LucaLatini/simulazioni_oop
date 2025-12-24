package a02a.e2;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;

public class LogicImpl implements Logic {
    private boolean over = false;
    private List<Position> obstacles = new LinkedList<>();
    private final Random random = new Random();
    private List<Position> path;
    private int currentPathIndex = 0;
    private boolean nextMoveIsThree = true;

    public LogicImpl(int size) {
        while (this.obstacles.size() < 3) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            path = this.createPath(size);

            boolean onBorder = (x == 0 || x == size - 1 || y == 0 || y == size - 1);

            boolean isStart = (x == 0 && y == 0);

            if (onBorder && !isStart) {
                obstacles.add(new Position(x, y));
            }
        }
    }

    private List<Position> createPath(int size) {
        List<Position> p = new ArrayList<>();
        // Lato superiore (da sinistra a destra)
        for (int i = 0; i < size - 1; i++)
            p.add(new Position(i, 0));
        // Lato destro (dall'alto in basso)
        for (int i = 0; i < size - 1; i++)
            p.add(new Position(size - 1, i));
        // Lato inferiore (da destra a sinistra)
        for (int i = size - 1; i > 0; i--)
            p.add(new Position(i, size - 1));
        // Lato sinistro (dal basso in alto)
        for (int i = size - 1; i > 0; i--)
            p.add(new Position(0, i));
        return p;
    }

    @Override
    public Position hit() {
        if (this.isOver()) {
            return path.get(currentPathIndex);
        }
        int move = nextMoveIsThree ? 3 : 4;
        this.currentPathIndex = (move + this.currentPathIndex) % this.path.size();
        nextMoveIsThree = !nextMoveIsThree;
        Position currentPosition = this.path.get(currentPathIndex);
        if (this.obstacles.contains(currentPosition)) {
            this.obstacles.remove(currentPosition);
            if (this.obstacles.isEmpty()) {
                this.over = true;
            }
            this.currentPathIndex = 0;
            currentPosition = this.path.get(0);
        } else if (this.obstacles.isEmpty()) {
            this.over = true;
        }
        return currentPosition;

    }

    @Override
    public boolean isObstacle(Position position) {
        return obstacles.contains(position);
    }

    @Override
    public void reset() {
        this.obstacles.clear();
        this.over = false;
    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    public boolean isStart(Position position) {
        return (position.x() == 0 && position.y() == 0);
    }
}
