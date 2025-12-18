package a01a.e2;

public interface Logic {

    boolean hit(Position position);

    boolean isOver();

    boolean isSelected(Position position);

    boolean isVertex(Position position);

    void reset();

}
