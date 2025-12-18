package a01c.e2;

public interface Logic {

    boolean hit(Position position);

    void reset();

    boolean isSelected(Position position);

    boolean isVertex(Position position);

    boolean isOver();

    int Size();
}
