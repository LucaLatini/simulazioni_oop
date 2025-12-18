package a01b.e2;

public interface Logic {

    boolean hit(Position position);
    
    boolean isOver();

    void reset();

    boolean isSelected(Position position);

}
