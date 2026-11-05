import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public final class MatchAnalytics {
    private final List<Match> matches;

    public MatchAnalytics(List<Match> matches) {
        this.matches = matches;
    }

    // Q1: matches played per season
    public Map<Integer, Long> matchesPerSeason() {
        return matches.stream()
                .collect(Collectors.groupingBy(Match::getSeason, TreeMap::new, Collectors.counting()));
    }
}