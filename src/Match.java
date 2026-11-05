import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Match {
    private final int id;
    private final int season;
    private final String city;
    private final String date;
    private final String team1;
    private final String team2;
    private final String tossWinner;
    private final String tossDecision;
    private final String result;
    private final boolean dlApplied;
    private final String winner;
    private final int winByRuns;
    private final int winByWickets;
    private final String playerOfMatch;
    private final String venue;
    private final String umpire1;
    private final String umpire2;
    private final String umpire3;

    public Match(int id, int season, String city, String date, String team1, String team2,
                 String tossWinner, String tossDecision, String result, boolean dlApplied,
                 String winner, int winByRuns, int winByWickets, String playerOfMatch,
                 String venue, String umpire1, String umpire2, String umpire3) {
        this.id = id;
        this.season = season;
        this.city = nvl(city);
        this.date = nvl(date);
        this.team1 = nvl(team1);
        this.team2 = nvl(team2);
        this.tossWinner = nvl(tossWinner);
        this.tossDecision = nvl(tossDecision);
        this.result = nvl(result);
        this.dlApplied = dlApplied;
        this.winner = nvl(winner);
        this.winByRuns = winByRuns;
        this.winByWickets = winByWickets;
        this.playerOfMatch = nvl(playerOfMatch);
        this.venue = nvl(venue);
        this.umpire1 = nvl(umpire1);
        this.umpire2 = nvl(umpire2);
        this.umpire3 = nvl(umpire3);
    }

    private static String nvl(String s) {
        return s == null ? "" : s.trim();
    }

    // Column order: id,season,city,date,team1,team2,toss_winner,toss_decision,result,
    // dl_applied,winner,win_by_runs,win_by_wickets,player_of_match,venue,
    // umpire1,umpire2,umpire3
    public static Match fromCsv(String line) {
        String[] c = line.split(",", -1); // -1 keeps trailing empty fields
        return new Match(
                Integer.parseInt(c[0].trim()), Integer.parseInt(c[1].trim()),
                c[2], c[3], c[4], c[5], c[6], c[7], c[8],
                c[9].trim().equals("1"),
                c[10],
                Integer.parseInt(c[11].trim()), Integer.parseInt(c[12].trim()),
                c[13], c[14], c[15], c[16],
                c.length > 17 ? c[17] : ""); // umpire3 is missing/empty in many rows
    }

    private static String[] splitCsv(String line) {
        // split on commas that are followed by an even number of quotes (i.e. not inside "...")
        String[] c = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for (int i = 0; i < c.length; i++) {
            String s = c[i].trim();
            if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
                s = s.substring(1, s.length() - 1).replace("\"\"", "\"");
            }
            c[i] = s;
        }
        return c;
    }

    public static List<Match> load(String path) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.skip(1) // header
                    .filter(l -> !l.trim().isEmpty())
                    .map(Match::fromCsv)
                    .collect(Collectors.toList());
        }
    }

    public static Map<Integer, Match> loadById(String path) throws IOException {
        return load(path).stream()
                .collect(Collectors.toMap(Match::getId, Function.identity()));
    }

    // ---- Helpers ----
    public boolean hasResult() {
        return !winner.isEmpty();
    }

    public boolean wonBatting() {
        return winByRuns > 0;
    }

    public boolean wonChasing() {
        return winByWickets > 0;
    }

    public boolean tossWinnerWonMatch() {
        return hasResult() && tossWinner.equals(winner);
    }

    // ---- Getters ----
    public int getId() { return id; }
    public int getSeason() { return season; }
    public String getCity() { return city; }
    public String getDate() { return date; }
    public String getTeam1() { return team1; }
    public String getTeam2() { return team2; }
    public String getTossWinner() { return tossWinner; }
    public String getTossDecision() { return tossDecision; }
    public String getResult() { return result; }
    public boolean isDlApplied() { return dlApplied; }
    public String getWinner() { return winner; }
    public int getWinByRuns() { return winByRuns; }
    public int getWinByWickets() { return winByWickets; }
    public String getPlayerOfMatch() { return playerOfMatch; }
    public String getVenue() { return venue; }
    public String getUmpire1() { return umpire1; }
    public String getUmpire2() { return umpire2; }
    public String getUmpire3() { return umpire3; }

    // id is unique per match, so equality is based on it alone
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;
        return id == ((Match) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Match{id=" + id + ", season=" + season + ", city='" + city + "'"
                + ", date='" + date + "', team1='" + team1 + "', team2='" + team2 + "'"
                + ", tossWinner='" + tossWinner + "', tossDecision='" + tossDecision + "'"
                + ", result='" + result + "', winner='" + winner + "'"
                + ", winByRuns=" + winByRuns + ", winByWickets=" + winByWickets
                + ", playerOfMatch='" + playerOfMatch + "', venue='" + venue + "'}";
    }
}