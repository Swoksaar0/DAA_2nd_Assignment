package cli;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) {
        String outputFile = args.length > 0 ? args[0] : "benchmark.csv";
        int[] sizes = {100, 1_000, 10_000, 100_000};
        Random rng = new Random(42);
        PerformanceTracker tracker = new PerformanceTracker();
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(tracker);

        try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
            pw.println("n,timeNs,comparisons,swaps,arrayAccesses,allocations");

            for (int n : sizes) {
                tracker.reset();
                tracker.incAllocations();
                int[] data = new int[n];
                for (int i = 0; i < n; i++) {
                    data[i] = rng.nextInt(10);
                }

                Optional<Integer> maj = bm.findMajority(data);

                pw.printf("%d,%d,%d,%d,%d,%d%n",
                        n,
                        tracker.getElapsedTimeNs(),
                        tracker.getComparisons(),
                        tracker.getSwaps(),
                        tracker.getArrayAccesses(),
                        tracker.getAllocations()
                );
            }
            System.out.println("CSV written to " + outputFile);
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}