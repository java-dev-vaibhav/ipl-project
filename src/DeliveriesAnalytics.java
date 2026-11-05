import java.util.*;

public class DeliveriesAnalytics {
    private final List<Delivery> deliveries;
    private final Map<Integer, Match> matchById;
    public Map<String, Integer> topRunScorers(int season, int n) {
        Map<String, Integer> runs = new HashMap<>();

        for (Delivery d : deliveries) {
            Match m = matchById.get(d.getMatchId());
            if (m == null || m.getSeason() != season || d.isSuperOver()) {
                continue;
            }
            String batsman = d.getBatsman();
            // batsman_runs only: extras (wides, byes, leg byes) are not credited to the batsman
            runs.put(batsman, runs.getOrDefault(batsman, 0) + d.getBatsmanRuns());
        }

        // sort descending by runs (highest = best)
        List<Map.Entry<String, Integer>> list = new ArrayList<>(runs.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return Integer.compare(b.getValue(), a.getValue());
            }
        });

        Map<String, Integer> top = new LinkedHashMap<>();
        for (int i = 0; i < list.size() && i < n; i++) {
            top.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return top;
    }

    public Map<String, Double> topEconomicalBowlers(int season, int n) {
        Map<String, Integer> runs = new HashMap<>();
        Map<String, Integer> balls = new HashMap<>();

        for (Delivery d : deliveries) {
            Match m = matchById.get(d.getMatchId());
            if (m == null || m.getSeason() != season || d.isSuperOver()) {
                continue;
            }
            String bowler = d.getBowler();

            // runs charged to the bowler: exclude byes and leg byes
            int conceded = d.getTotalRuns() - d.getByeRuns() - d.getLegByeRuns();
            runs.put(bowler, runs.getOrDefault(bowler, 0) + conceded);

            // only legal deliveries count towards overs (no wides, no no-balls)
            if (d.isLegalDelivery()) {
                balls.put(bowler, balls.getOrDefault(bowler, 0) + 1);
            }
        }

        // economy = runs / (balls / 6)
        Map<String, Double> economy = new HashMap<>();
        for (String bowler : runs.keySet()) {
            int legalBalls = balls.getOrDefault(bowler, 0);
            if (legalBalls == 0) {
                continue; // avoid divide by zero
            }
            economy.put(bowler, runs.get(bowler) * 6.0 / legalBalls);
        }

        // sort ascending by economy (lowest = best)
        List<Map.Entry<String, Double>> list = new ArrayList<>(economy.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                return Double.compare(a.getValue(), b.getValue());
            }
        });

        // keep the top n in order
        Map<String, Double> top = new LinkedHashMap<>();
        for (int i = 0; i < list.size() && i < n; i++) {
            top.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return top;
    }

    public DeliveriesAnalytics(List<Delivery> deliveries, List<Match> matches) {
        this.deliveries = deliveries;
        this.matchById = new HashMap<>();
        for (Match m : matches) {
            matchById.put(m.getId(), m);
        }
    }

    // Extra runs conceded per bowling team in a given season
    public Map<String, Integer> extraRunsConceded(int season) {
        Map<String, Integer> extras = new HashMap<>();

        for (Delivery d : deliveries) {
            Match m = matchById.get(d.getMatchId());
            if (m == null || m.getSeason() != season) {
                continue; // not a 2016 match (or unknown match id)
            }
            String team = d.getBowlingTeam();
            extras.put(team, extras.getOrDefault(team, 0) + d.getExtraRuns());
        }

        return extras;
    }
}