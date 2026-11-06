import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DeliveriesAnalytics {
    private final List<Delivery> deliveries;
    private final Map<Integer, Match> matchById = new HashMap<>();

    public DeliveriesAnalytics(List<Delivery> deliveries, List<Match> matches) {
        this.deliveries = deliveries;
        for (Match m : matches) {
            matchById.put(m.getId(), m);
        }
    }

    // top n batsmen by runs in a season (batsman runs only, super overs excluded)
    public Map<String, Integer> topRunScorers(int season, int n) {
        Map<String, Integer> runs = new HashMap<>();
        for (Delivery d : deliveries) {
            if (!inSeason(d, season) || d.isSuperOver()) continue;
            runs.merge(d.getBatsman(), d.getBatsmanRuns(), Integer::sum);
        }
        return topN(runs, n, (a, b) -> Integer.compare(b.getValue(), a.getValue()));
    }

    // top n bowlers by lowest economy in a season (super overs excluded)
    public Map<String, Double> topEconomicalBowlers(int season, int n) {
        Map<String, Integer> runs = new HashMap<>();
        Map<String, Integer> balls = new HashMap<>();

        for (Delivery d : deliveries) {
            if (!inSeason(d, season) || d.isSuperOver()) continue;
            String bowler = d.getBowler();

            // byes and leg byes are not charged to the bowler
            runs.merge(bowler, d.getTotalRuns() - d.getByeRuns() - d.getLegByeRuns(), Integer::sum);

            // wides and no-balls don't count as balls bowled
            if (d.isLegalDelivery()) {
                balls.merge(bowler, 1, Integer::sum);
            }
        }

        Map<String, Double> economy = new HashMap<>();
        for (Map.Entry<String, Integer> e : runs.entrySet()) {
            int legalBalls = balls.getOrDefault(e.getKey(), 0);
            if (legalBalls > 0) {
                economy.put(e.getKey(), e.getValue() * 6.0 / legalBalls);
            }
        }
        return topN(economy, n, (a, b) -> Double.compare(a.getValue(), b.getValue()));
    }

    // extra runs conceded per bowling team in a season
    public Map<String, Integer> extraRunsConceded(int season) {
        Map<String, Integer> extras = new HashMap<>();
        for (Delivery d : deliveries) {
            if (!inSeason(d, season)) continue;
            extras.merge(d.getBowlingTeam(), d.getExtraRuns(), Integer::sum);
        }
        return extras;
    }

    private boolean inSeason(Delivery d, int season) {
        Match m = matchById.get(d.getMatchId());
        return m != null && m.getSeason() == season;
    }

    // sort entries with the given order and keep the first n
    private static <V> Map<String, V> topN(Map<String, V> map, int n,
                                           Comparator<Map.Entry<String, V>> order) {
        List<Map.Entry<String, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, order);
        Map<String, V> top = new LinkedHashMap<>();
        for (int i = 0; i < list.size() && i < n; i++) {
            top.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return top;
    }
}