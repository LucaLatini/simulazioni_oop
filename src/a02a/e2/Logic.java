package a02a.e2;

public interface Logic {

    Position hit();

    boolean isOver();

    boolean isObstacle(Position position);

    void reset();

    boolean isStart(Position position);

}
