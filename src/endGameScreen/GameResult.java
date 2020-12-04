package endGameScreen;

public class GameResult {
    private String winner;
    private String loser;
    private boolean draw;
    public GameResult(String winner, String loser, boolean draw) {
        this.winner = winner;
        this.loser = loser;
        this.draw = draw;
    }

    public String getWinner() {
        return winner;
    }

    public String getLoser() {
        return loser;
    }

    public boolean isDraw() {
        return draw;
    }
}
