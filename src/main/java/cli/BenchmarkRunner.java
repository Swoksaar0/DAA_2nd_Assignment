package cli;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;
import utils.CSVWriter;
import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) throws IOException {
        String out = args.length>0? args[0] : "benchmark.csv";
        try (CSVWriter csv = new CSVWriter(out)) {
            csv.writeHeader("n","timeMs","comparisons","accesses");
            PerformanceTracker tracker = new PerformanceTracker();
            BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(tracker);
            Random rng = new Random();

            int[] sizes = {100,1_000,10_000,100_000};
            for (int n : sizes) {
                int[] data = rng.ints(n,0,10).toArray();
                long t0 = System.nanoTime();
                bm.findMajority(data);
                long dt = (System.nanoTime()-t0)/1_000_000;
                csv.writeRow(n, dt, tracker.getComparisons(), tracker.getAccesses());
            }
            System.out.println("Results → " + out);
        }
    }
}