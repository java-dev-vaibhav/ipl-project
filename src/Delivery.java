import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Delivery {
    int matchId;
    int inning;
    String battingTeam;
    String bowlingTeam;
    int over;
    int ball;
    String batsman;
    String nonStriker;
    String bowler;
    boolean superOver;
    int wideRuns;
    int byeRuns;
    int legByeRuns;
    int noBallRuns;
    int penaltyRuns;
    int batsmanRuns;
    int extraRuns;
    int totalRuns;
    String playerDismissed;
    String dismissalKind;
    String fielder;

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
        this.playerDismissed = playerDismissed == null ? "" : playerDismissed;
        this.dismissalKind = dismissalKind == null ? "" : dismissalKind;
        this.fielder = fielder == null ? "" : fielder;
    }
    public static Delivery fromCsv(String line) {
        String[] c = line.split(",", -1);
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
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.skip(1) // header
                    .filter(l -> !l.trim().isEmpty())
                    .map(Delivery::fromCsv)
                    .collect(Collectors.toList());
        }
    }
    public boolean isWicket() {
        return !playerDismissed.trim().isEmpty();
    }

    public boolean isLegalDelivery() {
        return wideRuns == 0 && noBallRuns == 0;
    }

    public int getMatchId() { return matchId; }
    public int getInning() { return inning; }
    public String getBattingTeam() { return battingTeam; }
    public String getBowlingTeam() { return bowlingTeam; }
    public int getOver() { return over; }
    public int getBall() { return ball; }
    public String getBatsman() { return batsman; }
    public String getNonStriker() { return nonStriker; }
    public String getBowler() { return bowler; }
    public boolean isSuperOver() { return superOver; }
    public int getWideRuns() { return wideRuns; }
    public int getByeRuns() { return byeRuns; }
    public int getLegByeRuns() { return legByeRuns; }
    public int getNoBallRuns() { return noBallRuns; }
    public int getPenaltyRuns() { return penaltyRuns; }
    public int getBatsmanRuns() { return batsmanRuns; }
    public int getExtraRuns() { return extraRuns; }
    public int getTotalRuns() { return totalRuns; }
    public String getPlayerDismissed() { return playerDismissed; }
    public String getDismissalKind() { return dismissalKind; }
    public String getFielder() { return fielder; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Delivery)) return false;
        Delivery d = (Delivery) o;
        return matchId == d.matchId && inning == d.inning && over == d.over && ball == d.ball
                && superOver == d.superOver && wideRuns == d.wideRuns && byeRuns == d.byeRuns
                && legByeRuns == d.legByeRuns && noBallRuns == d.noBallRuns
                && penaltyRuns == d.penaltyRuns && batsmanRuns == d.batsmanRuns
                && extraRuns == d.extraRuns && totalRuns == d.totalRuns
                && Objects.equals(battingTeam, d.battingTeam)
                && Objects.equals(bowlingTeam, d.bowlingTeam)
                && Objects.equals(batsman, d.batsman)
                && Objects.equals(nonStriker, d.nonStriker)
                && Objects.equals(bowler, d.bowler)
                && Objects.equals(playerDismissed, d.playerDismissed)
                && Objects.equals(dismissalKind, d.dismissalKind)
                && Objects.equals(fielder, d.fielder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, inning, battingTeam, bowlingTeam, over, ball, batsman,
                nonStriker, bowler, superOver, wideRuns, byeRuns, legByeRuns, noBallRuns,
                penaltyRuns, batsmanRuns, extraRuns, totalRuns, playerDismissed,
                dismissalKind, fielder);
    }

    @Override
    public String toString() {
        return "Delivery{matchId=" + matchId + ", inning=" + inning
                + ", battingTeam='" + battingTeam + "', bowlingTeam='" + bowlingTeam + "'"
                + ", over=" + over + ", ball=" + ball
                + ", batsman='" + batsman + "', nonStriker='" + nonStriker + "', bowler='" + bowler + "'"
                + ", superOver=" + superOver
                + ", batsmanRuns=" + batsmanRuns + ", extraRuns=" + extraRuns + ", totalRuns=" + totalRuns
                + ", playerDismissed='" + playerDismissed + "', dismissalKind='" + dismissalKind
                + "', fielder='" + fielder + "'}";
    }
}
