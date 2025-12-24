package a02b.e2;

public interface Logic {

    void hit(Position position);

    boolean isOver();

    void reset();

    boolean isDiagonal(Position position);

    Integer columnMarks(Position position);

    Integer rowMarks(Position position);

    boolean isMarked(Position position);
}
