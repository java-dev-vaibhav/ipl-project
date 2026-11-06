import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MatchAnalytics {
    private final List<Match> matches;

    public MatchAnalytics(List<Match> matches) {
        this.matches = matches;
    }

    // Q1: matches played per season (sorted by season)
    public Map<Integer, Long> matchesPerSeason() {
        Map<Integer, Long> counts = new TreeMap<>();
        for (Match m : matches) {
            counts.put(m.getSeason(), counts.getOrDefault(m.getSeason(), 0L) + 1);
        }
        return counts;
    }

    // Q2: wins per team (sorted by wins, highest first)
    public Map<String, Long> winsPerTeam() {
        Map<String, Long> wins = new HashMap<>();
        for (Match m : matches) {
            if (m.hasResult()) {
                wins.put(m.getWinner(), wins.getOrDefault(m.getWinner(), 0L) + 1);
            }
        }

        List<Map.Entry<String, Long>> entries = new ArrayList<>(wins.entrySet());
        Collections.sort(entries, (a, b) -> Long.compare(b.getValue(), a.getValue()));

        Map<String, Long> sorted = new LinkedHashMap<>();
        for (Map.Entry<String, Long> e : entries) {
            sorted.put(e.getKey(), e.getValue());
        }
        return sorted;
    }
}