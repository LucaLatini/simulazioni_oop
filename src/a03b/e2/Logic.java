package a03b.e2;

public interface Logic {

    void reset();

    boolean isOver();

    void hit(Position position);

    boolean isUp(Position position);

    boolean isDown(Position position);
}
