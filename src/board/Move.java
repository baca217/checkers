package board;

public class Move
{
    private final Position start, destination;
    //constructor
    public Move(Position start, Position destination)
    {
        this.start = start;
        this.destination = destination;
    }
    //methods
    public boolean isVertical()
    {
        return getX1() == getX2();
    }
    public boolean isNorth()
    {
        return getY2() - getY1() > 0;
    }
    public boolean isSouth()
    {
        return getY2() - getY1() > 0;
    }
    public boolean isDiagonal()
    {
        return Math.abs(getY2() - getY1()) == Math.abs(getX2() - getX1());
    }
    public boolean isStraight()
    {
        return getX1() == getX2() ^ getY1() == getY2();
    }

    public int distance()
    {
        return Math.max(
                Math.abs(getY2() - getY1()),
                Math.abs(getX2() - getX1())
        );
    }
    //getters
    public Position getStart()
    {
        return start;
    }
    public Position getDestination()
    {
        return destination;
    }
    public int getX1()
    {
        return getStart().getX();
    }
    public int getY1()
    {
        return getStart().getY();
    }
    public int getX2()
    {
        return  getDestination().getX();
    }
    public int getY2()
    {
        return getDestination().getY();
    }
}
