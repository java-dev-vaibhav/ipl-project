import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Delivery {
    private final int matchId, inning, over, ball;
    private final int wideRuns, byeRuns, legByeRuns, noBallRuns, penaltyRuns;
    private final int batsmanRuns, extraRuns, totalRuns;
    private final boolean superOver;
    private final String battingTeam, bowlingTeam, batsman, nonStriker, bowler,
            playerDismissed, dismissalKind, fielder;

    public Delivery(int matchId, int inning, String battingTeam, String bowlingTeam,
                    int over, int ball, String batsman, String nonStriker, String bowler,
                    boolean superOver, int wideRuns, int byeRuns, int legByeRuns,
                    int noBallRuns, int penaltyRuns, int batsmanRuns, int extraRuns,
                    int totalRuns, String playerDismissed, String dismissalKind, String fielder) {
        this.matchId = matchId;
        this.inning = inning;
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.over = over;
        this.ball = ball;
        this.batsman = batsman;
        this.nonStriker = nonStriker;
        this.bowler = bowler;
        this.superOver = superOver;
        this.wideRuns = wideRuns;
        this.byeRuns = byeRuns;
        this.legByeRuns = legByeRuns;
        this.noBallRuns = noBallRuns;
        this.penaltyRuns = penaltyRuns;
        this.batsmanRuns = batsmanRuns;
        this.extraRuns = extraRuns;
        this.totalRuns = totalRuns;
        this.playerDismissed = playerDismissed;
        this.dismissalKind = dismissalKind;
        this.fielder = fielder;
    }

    public static Delivery fromCsv(String line) {
        String[] c = line.split(",", -1);
        for (int i = 0; i < c.length; i++) {
            c[i] = c[i].trim();
        }
        return new Delivery(
                Integer.parseInt(c[0]), Integer.parseInt(c[1]), c[2], c[3],
                Integer.parseInt(c[4]), Integer.parseInt(c[5]), c[6], c[7], c[8],
                c[9].equals("1"),
                Integer.parseInt(c[10]), Integer.parseInt(c[11]), Integer.parseInt(c[12]),
                Integer.parseInt(c[13]), Integer.parseInt(c[14]), Integer.parseInt(c[15]),
                Integer.parseInt(c[16]), Integer.parseInt(c[17]),
                c[18], c[19], c[20]);
    }

    public static List<Delivery> load(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        List<Delivery> deliveries = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) { // skip header
            String line = lines.get(i);
            if (!line.trim().isEmpty()) {
                deliveries.add(fromCsv(line));
            }
        }
        return deliveries;
    }

    public boolean isWicket()         { return !playerDismissed.isEmpty(); }
    public boolean isLegalDelivery()  { return wideRuns == 0 && noBallRuns == 0; }

    public int getMatchId()            { return matchId; }
    public int getInning()             { return inning; }
    public String getBattingTeam()     { return battingTeam; }
    public String getBowlingTeam()     { return bowlingTeam; }
    public int getOver()               { return over; }
    public int getBall()               { return ball; }
    public String getBatsman()         { return batsman; }
    public String getNonStriker()      { return nonStriker; }
    public String getBowler()          { return bowler; }
    public boolean isSuperOver()       { return superOver; }
    public int getWideRuns()           { return wideRuns; }
    public int getByeRuns()            { return byeRuns; }
    public int getLegByeRuns()         { return legByeRuns; }
    public int getNoBallRuns()         { return noBallRuns; }
    public int getPenaltyRuns()        { return penaltyRuns; }
    public int getBatsmanRuns()        { return batsmanRuns; }
    public int getExtraRuns()          { return extraRuns; }
    public int getTotalRuns()          { return totalRuns; }
    public String getPlayerDismissed() { return playerDismissed; }
    public String getDismissalKind()   { return dismissalKind; }
    public String getFielder()         { return fielder; }

    @Override
    public String toString() {
        return "Delivery{matchId=" + matchId + ", inning=" + inning + ", over=" + over
                + ", ball=" + ball + ", batsman=" + batsman + ", bowler=" + bowler
                + ", totalRuns=" + totalRuns + ", playerDismissed=" + playerDismissed + "}";
    }
}