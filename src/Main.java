import java.io.IOException;
import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        List<Match> matches = Match.load("matches.csv");

        MatchAnalytics matchAnalytics = new MatchAnalytics(matches);

        // Q1: matches played per year
        matchAnalytics.matchesPerSeason().forEach((season, count) ->
                System.out.println(season + " -> " + count));
    }
}