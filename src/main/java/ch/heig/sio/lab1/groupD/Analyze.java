package ch.heig.sio.lab1.groupD;

import ch.heig.sio.lab1.display.HeuristicComboItem;
import ch.heig.sio.lab1.groupD.heuristics.ClosestFirstInsert;
import ch.heig.sio.lab1.groupD.heuristics.FarthestFirstInsert;
import ch.heig.sio.lab1.groupD.heuristics.RandomInsert;
import ch.heig.sio.lab1.tsp.TspData;

import java.text.DecimalFormat;
import java.util.*;

import static java.util.Collections.max;
import static java.util.Collections.min;

/**
 * Used to get different metrics on the heuristics.
 * @author Edwin Häffner
 * @author Arthur Junod
 */
public final class Analyze {

    private record Statistics(double max, double min, double median, double mean, double stdDev) {}

    public static void main(String[] args) {

        //Array of all the heuristics
        HeuristicComboItem[] heuristics = {
                new HeuristicComboItem("Random insert", new RandomInsert()),
                new HeuristicComboItem("Closest First", new ClosestFirstInsert()),
                new HeuristicComboItem("Farthest First", new FarthestFirstInsert()),
        };

        // Array of files
        String[] files = {"pcb442", "att532", "u574", "pcb1173", "nrw1379", "u1817"};
        long[] optimalDistances = {50778, 86729, 36905, 56892, 56638, 57201};

        System.out.println("Analyzing heuristics...");
        System.out.println("The performance is a percentage of the mean distance compared to the optimal distance. It should never be below 100% and the closer to 100% the better.");

        // Loop through all the files
        for (int fileIndex = 0; fileIndex < files.length; fileIndex++) {
            String file = files[fileIndex];

            // Open the file and analyze the heuristics
            try {
                TspData data = TspData.fromFile("data/" + file + ".dat");
                int numberOfCities = data.getNumberOfCities();

                System.out.println("\nProcessing dataset: " + file + ".dat (" + numberOfCities + " cities)");
                Map<String, Statistics> statsMap = new LinkedHashMap<>();

                // Loop through all the heuristics
                for (HeuristicComboItem heuristic : heuristics) {
                    ArrayList<Long> results = new ArrayList<>();
                    long meanValue = 0;

                    // Loop through all the cities
                    for (int i = 0; i < numberOfCities; ++i) {
                        long length = heuristic.computeTour(data, i).length();
                        results.add(length);
                        meanValue += length;

                        updateProgress(i + 1, numberOfCities, heuristic.toString());
                    }

                    double mean = (double) meanValue / data.getNumberOfCities();
                    double medianValue = median(results);
                    double stdDevValue = stdDev(results, mean);

                    statsMap.put(heuristic.toString(), new Statistics(
                            max(results),
                            min(results),
                            medianValue,
                            mean,
                            stdDevValue
                    ));
                }

                printStatistics(file, statsMap, optimalDistances[fileIndex]);

            } catch (Exception e) {
                System.err.println("There was an error in processing " + file + ".dat");
                System.err.println(e.getMessage());
                return;
            }
        }
    }

    public static double median(List<Long> values) {
        Collections.sort(values);
        int middle = values.size() / 2;
        if (values.size() % 2 == 0) {
            return (values.get(middle - 1) + values.get(middle)) / 2.0;
        } else {
            return values.get(middle);
        }
    }

    public static double stdDev(List<Long> values, double mean) {
        double sum = 0;
        for (Long value : values) {
            sum += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sum / (values.size() - 1));
    }

    /**
     * Print the statistics for the heuristics in a nice readable format.
     * Generated with ClaudeAI.
     *
     * @param filename The name of the file being analyzed
     * @param heuristicStats The statistics for each heuristic
     * @param optimalDistance The optimal distance for the dataset
     */
    private static void printStatistics(String filename, Map<String, Statistics> heuristicStats, long optimalDistance) {
        int metricWidth = 20;
        int valueWidth = 18;

        // Print header
        System.out.println("\nAnalysis for dataset: " + filename);
        System.out.println("Optimal tour length: " + String.format("%,d", optimalDistance));
        System.out.printf("%-" + metricWidth + "s", "Metric");
        for (String heuristic : heuristicStats.keySet()) {
            System.out.printf("%-" + valueWidth + "s", heuristic);
        }
        System.out.println();

        // Print separator
        System.out.println("-".repeat(metricWidth + (valueWidth * heuristicStats.size())));

        // Print statistics
        String[] metrics = {"Max", "Min", "Median", "Mean", "Standard Deviation", "Performance (%)"};
        DecimalFormat df = new DecimalFormat("#,##0.00");

        for (String metric : metrics) {
            System.out.printf("%-" + metricWidth + "s", metric);
            for (Statistics stats : heuristicStats.values()) {
                double value = switch (metric) {
                    case "Max" -> stats.max;
                    case "Min" -> stats.min;
                    case "Median" -> stats.median;
                    case "Mean" -> stats.mean;
                    case "Standard Deviation" -> stats.stdDev;
                    case "Performance (%)" -> (stats.mean / optimalDistance) * 100;
                    default -> 0.0;
                };
                System.out.printf("%-" + valueWidth + "s", df.format(value));
            }
            System.out.println();
        }
    }

    /**
     * Display a progress bar for the current heuristic tested.
     * Generated with ClaudeAI.
     * @param current           The current city processed, used to track progress
     * @param total             The total number of cities
     * @param currentHeuristic  The name of the current heuristic tested
     */
    private static void updateProgress(int current, int total, String currentHeuristic) {
        int progressBarWidth = 40;
        double percentage = (double) current / total * 100;
        int completedWidth = progressBarWidth * current / total;

        // Create the progress bar
        StringBuilder progressBar = new StringBuilder("[");
        for (int i = 0; i < progressBarWidth; i++) {
            if (i < completedWidth) {
                progressBar.append("=");
            } else if (i == completedWidth) {
                progressBar.append(">");
            } else {
                progressBar.append(" ");
            }
        }
        progressBar.append("]");

        // Print the progress bar and percentage
        System.out.print("\r" + currentHeuristic + " Progress: " + progressBar + " " +
                String.format("%.1f%%", percentage) + "    "); // Extra spaces to clear any previous longer text

        // Print newline if we're done processing
        if (current == total) {
            System.out.println();
        }
    }



}


