package checkers;

public class MoveResult
{
    //variables
    private MoveType type;
    private Piece piece;
    //constructors
    public MoveResult(MoveType type) {
        this(type, null);
    } //move with no kill
    public MoveResult(MoveType type, Piece piece) //move with kill
    {
        this.type = type;
        this.piece = piece;
    }
    //getters
    public MoveType getType() {
        return type;
    }
    public Piece getPiece() {
        return piece;
    }
}