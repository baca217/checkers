package endGameScreen;

public class GameResult {
    private String winner;
    private String loser;

    public GameResult(String winner, String loser) {
        this.winner = winner;
        this.loser = loser;
    }

    public String getWinner() {
        return winner;
    }

    public String getLoser() {
        return loser;
    }

}
