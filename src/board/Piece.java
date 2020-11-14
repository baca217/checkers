package board;

import java.util.ArrayList;

public abstract class Piece
{
    private final Color color;
    protected String lightSymbol;
    protected String darkSymbol;
    protected int value;
    //constructor
    protected Piece(Color color, String lightSymbol, String darkSymbol, int value)
    {
        this.color = color;
        this.lightSymbol = lightSymbol;
        this.darkSymbol = darkSymbol;
        this.value = value;
    }
    //methods
    public abstract boolean isMoveLegal(GameState gameState, Move move);
    public abstract void makeMove(GameState gameState, Move move);
    @Override public String toString()
    {
        return getColor() == Color.LIGHT ? lightSymbol : darkSymbol;
    }
    public ArrayList<GameState> getSuccessors(GameState gameState, Position myPosition)
    {
        ArrayList<GameState> successors = new ArrayList<>();

        for( Position position : gameState.getAllPossiblePositions())
        {
            Move move = new Move(myPosition,position);
            if( gameState.isMoveLegal(move))
            {
                GameState copy = gameState.copy();
                copy.setPrev(gameState);
                copy.makeMove(move);
                successors.add(copy);
            }
        }
        return successors;
    }
    //getters
    public int getValue() { return value; }
    public Color getColor() { return color; }
}
