package a04.e2;

import java.util.LinkedList;
import java.util.List;

public class LogicImpl implements Logic {
    private boolean over = false;
    private List<List<Position>> circles = new LinkedList<>();
    private int currentCircleIndex = 0;
    private int selectedPosition = 0;

    public LogicImpl(int size) {
        List<Position> centers = List.of(new Position(1, 1),
                new Position(size / 2, size / 2),
                new Position(size - 2, size - 2));
        for (Position center : centers) {
            List<Position> circle = new LinkedList<>();

            circle.add( new Position(center.x()-1 , center.y()-1));
            circle.add(new Position(center.x() -1 , center.y()));
            circle.add( new Position(center.x() -1 , center.y()+1));
            circle.add(new Position( center.x() , center.y()+1));
            circle.add(new Position(center.x()+1 , center.y()+1));
            circle.add(new Position(center.x()+1 , center.y()));
            circle.add(new Position(center.x()+1 , center.y()-1));
            circle.add(new Position(center.x() , center.y()-1));


            this.circles.add(circle);
        }


    }
    @Override
    public void hit(Position position) {
        if(position.equals(this.circles.get(currentCircleIndex).get(selectedPosition))){
            currentCircleIndex = (currentCircleIndex+1) % this.circles.size();
            selectedPosition = 0;

        }else{
            selectedPosition = (selectedPosition+1) % this.circles.get(0).size();
        }
    }

    @Override
    public Position markedPosition() {

        return this.circles.get(currentCircleIndex).get(selectedPosition);
    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }

}
