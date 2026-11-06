import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Match {
    private final int id, season, winByRuns, winByWickets;
    private final boolean dlApplied;
    private final String city, date, team1, team2, tossWinner, tossDecision, result,
            winner, playerOfMatch, venue, umpire1, umpire2, umpire3;

    public Match(int id, int season, String city, String date, String team1, String team2,
                 String tossWinner, String tossDecision, String result, boolean dlApplied,
                 String winner, int winByRuns, int winByWickets, String playerOfMatch,
                 String venue, String umpire1, String umpire2, String umpire3) {
        this.id = id;
        this.season = season;
        this.city = city;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
        this.tossWinner = tossWinner;
        this.tossDecision = tossDecision;
        this.result = result;
        this.dlApplied = dlApplied;
        this.winner = winner;
        this.winByRuns = winByRuns;
        this.winByWickets = winByWickets;
        this.playerOfMatch = playerOfMatch;
        this.venue = venue;
        this.umpire1 = umpire1;
        this.umpire2 = umpire2;
        this.umpire3 = umpire3;
    }

    public static Match fromCsv(String line) {
        // split on commas outside quotes, then drop the quotes
        String[] c = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for (int i = 0; i < c.length; i++) {
            c[i] = c[i].replace("\"", "").trim();
        }
        return new Match(
                Integer.parseInt(c[0]), Integer.parseInt(c[1]),
                c[2], c[3], c[4], c[5], c[6], c[7], c[8],
                c[9].equals("1"), c[10],
                Integer.parseInt(c[11]), Integer.parseInt(c[12]),
                c[13], c[14], c[15], c[16],
                c.length > 17 ? c[17] : "");
    }

    public static List<Match> load(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        List<Match> matches = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) { // skip header
            String line = lines.get(i);
            if (!line.trim().isEmpty()) {
                matches.add(fromCsv(line));
            }
        }
        return matches;
    }

    public static Map<Integer, Match> loadById(String path) throws IOException {
        Map<Integer, Match> map = new HashMap<>();
        for (Match m : load(path)) {
            map.put(m.id, m);
        }
        return map;
    }

    public boolean hasResult()          { return !winner.isEmpty(); }
    public boolean wonBatting()         { return winByRuns > 0; }
    public boolean wonChasing()         { return winByWickets > 0; }
    public boolean tossWinnerWonMatch() { return hasResult() && tossWinner.equals(winner); }

    public int getId()               { return id; }
    public int getSeason()           { return season; }
    public String getCity()          { return city; }
    public String getDate()          { return date; }
    public String getTeam1()         { return team1; }
    public String getTeam2()         { return team2; }
    public String getTossWinner()    { return tossWinner; }
    public String getTossDecision()  { return tossDecision; }
    public String getResult()        { return result; }
    public boolean isDlApplied()     { return dlApplied; }
    public String getWinner()        { return winner; }
    public int getWinByRuns()        { return winByRuns; }
    public int getWinByWickets()     { return winByWickets; }
    public String getPlayerOfMatch() { return playerOfMatch; }
    public String getVenue()         { return venue; }
    public String getUmpire1()       { return umpire1; }
    public String getUmpire2()       { return umpire2; }
    public String getUmpire3()       { return umpire3; }

    @Override
    public boolean equals(Object o) {
        return o instanceof Match && id == ((Match) o).id;
    }

    @Override
    public int hashCode() { return id; }

    @Override
    public String toString() {
        return "Match{id=" + id + ", season=" + season + ", " + team1 + " vs " + team2
                + ", winner=" + winner + ", venue=" + venue + "}";
    }
}