import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to IPL Analytics Project!");
        System.out.println("Please enter the problem number to be solved....");
        System.out.print("1-> Number of matches played per year of all the years in IPL.\n" +
                "2-> Number of matches won of all teams over all the years of IPL.\n" +
                "3-> For the year 2016 get the extra runs conceded per team.\n" +
                "4-> For the year 2015 get the top economical bowlers.\n" +
                "5-> Get top batsmen by year");
        Scanner sc=new Scanner(System.in);
        int q=sc.nextInt();
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
            case 3:{
                List<Delivery> deliveries = Delivery.load("deliveries.csv");
                List<Match> matches = Match.load("matches.csv");
                DeliveriesAnalytics deliveriesAnalytics = new DeliveriesAnalytics(deliveries, matches);

                for (Map.Entry<String, Integer> e : deliveriesAnalytics.extraRunsConceded(2016).entrySet()) {
                    System.out.println(e.getKey() + " -> " + e.getValue());
                }
            }
            case 4:{
                System.out.println("Enter number of top economical bowlers to display...");
                int n=sc.nextInt();
                List<Delivery> deliveries = Delivery.load("deliveries.csv");
                List<Match> matches = Match.load("matches.csv");
                DeliveriesAnalytics deliveriesAnalytics = new DeliveriesAnalytics(deliveries, matches);
                for (Map.Entry<String, Double> e : deliveriesAnalytics.topEconomicalBowlers(2015, n).entrySet()) {
                    System.out.printf("%s -> %.2f%n", e.getKey(), e.getValue());
                }
                break;
            }
            case 5:{
                System.out.print("Enter year: ");
                int year = sc.nextInt();
                System.out.print("How many top batsmen? ");
                int n = sc.nextInt();
                List<Delivery> deliveries = Delivery.load("deliveries.csv");
                List<Match> matches = Match.load("matches.csv");
                DeliveriesAnalytics deliveriesAnalytics = new DeliveriesAnalytics(deliveries, matches);
                Map<String, Integer> result = deliveriesAnalytics.topRunScorers(year, n);
                if (result.isEmpty()) {
                    System.out.println("No data for " + year);
                }
                for (Map.Entry<String, Integer> e : result.entrySet()) {
                    System.out.println(e.getKey() + " -> " + e.getValue());
                }
                break;
            }
            default:{
                System.out.println("Please enter a valid problem number...");
                break;
            }
        }
        sc.close();


    }
}