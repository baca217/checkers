package board;

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
    public abstract void makeMove(GameState, Move move);
    @Override public String toString()
    {
        return getColor() == Color.LIGHT ? lightSymbol : darkSymbol;
    }
    //getters
    public int getValue() { return value; }
    public Color getColor() { return color; }
}
