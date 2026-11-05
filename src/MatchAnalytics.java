import java.util.*;
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

    public Map<String, Long> winsPerTeam() {
        return matches.stream()
                .filter(Match::hasResult)
                .collect(Collectors.groupingBy(Match::getWinner, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }
}