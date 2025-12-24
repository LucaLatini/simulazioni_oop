package a03a.e2;

public interface Logic {

    boolean isOver();

    void reset();

    boolean isObstacle(Position position);

    void hit(Position position);

    void descent();

    boolean isContained(Position pos);

}
