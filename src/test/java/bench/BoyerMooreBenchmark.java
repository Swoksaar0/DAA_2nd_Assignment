package bench;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class BoyerMooreBenchmark {

    @Param({ "100", "1000", "10000", "100000" })
    private int size;

    private int[] data;
    private BoyerMooreMajorityVote bm;
    private PerformanceTracker tracker;
    private Random rng = new Random(42);

    @Setup(Level.Trial)
    public void setupTrial() {
        tracker = new PerformanceTracker();
        bm = new BoyerMooreMajorityVote(tracker);
    }

    @Setup(Level.Invocation)
    public void setupInvocation() {
        data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = rng.nextInt(10) - 5;
        }
        tracker.reset();
    }

    @Benchmark
    public void measure(Blackhole bh) {
        bh.consume(bm.findMajority(data));
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}