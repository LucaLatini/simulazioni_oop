package a06.e1;

public class LogicImpl implements Logic {
    private boolean over = false;
    private Position marks;
    private int size;
    private boolean goDown = true;
    private Position firstPosition;

    public LogicImpl(int size , Position pos ){
        this.size = size;
        firstPosition = pos;
        marks = this.setMark(pos);
    }


    @Override
    public boolean isOver() {
        return this.over;
    }

    @Override
    public void reset() {
        goDown = true;
        marks = this.setMark(firstPosition);
    }

    private Position setMark(Position position) {
        return new  Position(position.x() , 0);
    }


    @Override
    public void hit(Position position) {
        if(marks.equals(position)){
            if(goDown){
                if(marks.y() +1 > this.size-1 ){
                    Position newPosition = new Position(marks.x(), this.size-1);
                    marks = newPosition;
                    goDown = false;
                }else{
                    Position newPosition = new Position(marks.x(), marks.y()+1);
                    marks = newPosition;
                }
            }else{
                if(this.marks.y() == this.size-1){
                    Position newPosition = new Position(marks.x()+1, firstPosition.y());
                    marks = newPosition;
                }else if(this.marks.x() +1 > this.size-1){
                    Position newPosition = new Position(this.size-1,this.marks.y());
                    marks = newPosition;
                    this.reset();
                }else{
                    Position newPosition = new Position(marks.x()+1, marks.y());
                    marks = newPosition;
                }
            }
        }else{
            if(goDown){
                if(marks.y() +2 > this.size-1 ){
                    Position newPosition = new Position(marks.x(), this.size-1);
                    marks = newPosition;
                    goDown = false;
                }else{
                    Position newPosition = new Position(marks.x(), marks.y()+2);
                    marks = newPosition;
                }
            }else{
                if(this.marks.y() == this.size-1){
                    Position newPosition = new Position(marks.x()+1, firstPosition.y());
                    marks = newPosition;
                }else if(this.marks.x() +1 > this.size-1){
                    Position newPosition = new Position(this.size-1,this.marks.y());
                    marks = newPosition;
                    this.reset();
                }else{
                    Position newPosition = new Position(marks.x()+2, marks.y());
                    marks = newPosition;
                }
            }
        }
    }


    @Override
    public boolean isSelected(Position pos) {
        return marks.equals(pos);
    }

}
