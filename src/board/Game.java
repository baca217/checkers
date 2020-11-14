package board;

public class Game
{
    private GameState currentGameState;
    //methods
    public void makeMove(Move move)
    {
        if( currentGameState.isMoveLegal(move))
        {
            GameState copy = currentGameState.copy();
            copy.makeMove(move);
            makeMove(copy);
        }
    }

    public void makeMove(GameState gameState)
    {
        gameState.setFuture(null);
        currentGameState.setFuture(gameState);
        gameState.setPrev(currentGameState);
        currentGameState = gameState;
    }

    public void undoMove()
    {
        if( currentGameState.getPrev() != null)
            currentGameState = currentGameState.getPrev();
    }

    public void redoMove()
    {
        if(currentGameState.getFuture() != null)
            currentGameState = currentGameState.getPrev();
    }

    public void undoAll()
    {
        while( currentGameState.getPrev() != null)
            currentGameState = currentGameState.getPrev();
    }

    public void redoAll()
    {
        while( currentGameState.getFuture() != null)
            currentGameState = currentGameState.getFuture();
    }

    public void newGame()
    {
        undoAll();
        currentGameState.setFuture(null);
    }
    //getters
    public void setCurrentGameState(GameState gameState){ currentGameState = gameState; }
    public GameState getCurrentGameState() { return currentGameState; }
}
