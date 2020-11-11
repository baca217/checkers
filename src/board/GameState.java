package board;

import org.ietf.jgss.GSSName;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GameState {
    private GameState prev, future;
    private Move lastMove;
    private Color playerTurn = Color.LIGHT;
    private Piece[][] squares;
    //constructors
    public GameState()
    {
        this(0, 0);
    }
    public GameState(int boardWidth, int boardHeight)
    {
        squares = new Piece[boardWidth][boardHeight];
    }
    //methods
    public boolean isMoveLegal(Move move)
    {
        for(int coordinate : new int[]{move.getX1(), move.getY1(), move.getX2(), move.getY2()})
            if(coordinate < 0 || coordinate >= getBoardWidth())
                return false;
        Piece piece = getPiece(move.getStart()) ;
        return piece != null //piece exists
                && piece.getColor() == getPlayerTurn() //piece color is the same as the current turn
                && piece.isMoveLegal(this, move); //check if move is legal
    }
    public  void makeMove(Move move)
    {
        getPiece(move.getStart()).makeMove(this, move);
        setLastMove(move);
    }
    public abstract void postMoveUINotifications(UserInterface ui);
    public GameState copy()
    {
        GameState copy = null;
        try
        {
            copy = this.getClass().getDeclaredConstructor().newInstance();
            copy.prev = prev;
            copy.future = future;
            copy.lastMove = lastMove;
            copy.playerTurn = playerTurn;
            for( int x = 0; x < squares.length; x++)
            {
                for(int y = 0; y < squares[x].length; y++)
                {
                    copy.squares[x][y] = squares[x][y];
                }
            }
        } catch (Exception e)
        {
            System.out.println("ERROR: " + this.getClass() + " must have a no-arg constructor for copy() to work.");
            System.exit(1);
        }
        return copy;
    }
    public ArrayList<Position> getPlayersPositions(Color playerColor)
    {
        ArrayList<Position> positions = new ArrayList<>();

        for (Position position : getAllPossiblePositions())
        {
            Piece piece = getPiece(position);
            if (piece  != null && piece.getColor() == playerColor)
                position.add(position);
        }
    }
    public ArrayList<Position> getAllPossiblePositions()
    {
        ArrayList<Position> positions = new ArrayList<>(64);

        for (int x = 0; x < getBoardWidth(); x++)
        {
            for(int y = 0; y < getBoardHeight(); y++)
            {
                positions.add(new Position(x, y));
            }
        }
        return positions;
    }
    public boolean equals(GameState other)
    {
        return playerTurn == other.playerTurn
                && Arrays.deepEquals(squares, other.squares);
    }
    public boolean isTerminal()
    {
        boolean whiteFound = false;
        boolean blackFound =false;
        for(Position position : getAllPossiblePositions())
        {
            Piece piece = getPiece(position);
            if( piece != null)
            {
                if( piece.getColor() == Color.LIGHT)
                    whiteFound = true;
                else
                    blackFound = true;
            }
        }
        return !whiteFound || !blackFound;
    }
    public double evaluate(Color maximizingPlayer)
    {
        int score = 0;
        for(Position position : getAllPossiblePositions())
        {
            Piece piece = getPiece(position);
            if(piece != null)
                if( piece.getColor() == maximizingPlayer)
                    score += piece.getValue();
                else
                    score -= piece.getValue();
        }
        return score;
    }
    public ArrayList<GameState> getSuccessors()
    {
        ArrayList<GameState> successors = new ArrayList<>();

        for( Position position : getPlayersPositions(getPlayerTurn()))
        {
            Piece piece = getPiece(position);
            if( piece != null && piece.getColor() == getPlayerTurn())
            {
                successors.addAll(piece.getSuccesors(this, position));
            }
        }
    }
    //getters and setters
    public GameState getPrev() { return prev; }
    public void setPrev(GameState prev) { this.prev = prev; }
    public GameState getFuture() { return future; }
    public void setFuture(GameState future) { this.future = future; }
    public Move getLastMove() { return lastMove; }
    public void setLastMove(Move lastMove) { this.lastMove = lastMove; }
    public Color getPlayerTurn() { return playerTurn; }
    public void setPlayerTurn(Color playerTurn) { this.playerTurn = playerTurn; }
    public void endPlayerTurn() { playerTurn = playerTurn.opponent(); }
    public Piece getPiece(Position position) { return squares[position.getX()][position.getY()]; }
    public void setPiece(Position position, Piece piece)
    {

    }
    public int getBoardWidth() { return squares.length; }
    public int getBoardLength() { return squares[0].length; }
}
