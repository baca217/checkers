package board;

public class Game
{
    private GameState currentGameState;
    //methods
    public void makeMove(Move move)
    {
        if( currentGameState.isMoveLegal(move)) //check if the move is legal
        {
            GameState copy = currentGameState.copy();
            copy.makeMove(move); //make move in copy gamestate
            makeMove(copy); //set copy gamestate to current gamestate
        }
    }

    public void makeMove(GameState gameState)
    {
        gameState.setFuture(null); //no future move
        currentGameState.setFuture(gameState);
        gameState.setPrev(currentGameState);
        currentGameState = gameState; //set copied gamestate to current gamestate
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
    //getters and setters
    public void setCurrentGameState(GameState gameState){ currentGameState = gameState; }
    public GameState getCurrentGameState() { return currentGameState; }
}
