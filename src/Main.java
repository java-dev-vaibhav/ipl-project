import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to IPL Analytics Project!");
        System.out.println("Please enter the problem number to be solved....");
        Scanner sc=new Scanner(System.in);
        int q=sc.nextInt();
        sc.close();
        switch(q){
            case 1:
            {
                List<Match> matches = Match.load("matches.csv");

                MatchAnalytics matchAnalytics = new MatchAnalytics(matches);

                // Q1: matches played per year
                matchAnalytics.matchesPerSeason().forEach((season, count) ->
                        System.out.println(season + " -> " + count));
                break;
            }
            case 2:{
                List<Match> matches = Match.load("matches.csv");
                MatchAnalytics matchAnalytics = new MatchAnalytics(matches);

                matchAnalytics.winsPerTeam().forEach((team, wins) ->
                        System.out.println(team + " -> " + wins));
                    break;
                }
            default:{
                System.out.println("Please enter a valid problem number...");
                break;
            }
        }


    }
}