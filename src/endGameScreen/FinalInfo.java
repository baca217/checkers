package endGameScreen;

public class FinalInfo {
    private String ranking;
    private int winsOverLoser;

    public  FinalInfo(){}

    public FinalInfo(String ranking, int winsOverLoser) {
        this();
        this.ranking = ranking;
        this.winsOverLoser = winsOverLoser;
    }

    public String getRanking() {
        return ranking;
    }

    public int getWinsOverLoser() {
        return winsOverLoser;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public void setWinsOverLoser(int winsOverLoser) {
        this.winsOverLoser = winsOverLoser;
    }
}
