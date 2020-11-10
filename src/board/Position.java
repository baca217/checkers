package board;

public class Position {
    private int x, y;
    //constructors
    public Position(char file, int rank)
    {
        x = fileToInt(file);
        y=rankToInt(rank);
    }
    public Position(int arrayX, int arrayY)
    {
        x = arrayX;
        y = arrayY;
    }
    //helpers
    private static int fileToInt(char file)
    {
        if(!Character.isAlphabetic(file))
        {
            return -1;
        }
        if (file >= 97)
            return file - 97; //capital letter
        else
            return file - 65; //lowercase letter
    }

    private static int rankToInt(int rank)
    {
        return rank - 1;
    }

    //getters and setters
    public int getX(){ return this.x; }
    public int getY(){ return this.y; }
    public void setX(int x){ this.x = x; }
    public void setY(int y){ this.y = y; }
}
