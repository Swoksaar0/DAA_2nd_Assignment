package bench;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.Random;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BoyerMooreBenchmark {
    @Param({"100","1000","10000","100000"})
    private int n;

    private int[] data;
    private BoyerMooreMajorityVote bm;

    @Setup(Level.Trial)
    public void setup() {
        PerformanceTracker t = new PerformanceTracker();
        bm = new BoyerMooreMajorityVote(t);
        data = new Random(123).ints(n,0,10).toArray();
    }

    @Benchmark
    public void measure() {
        bm.findMajority(data);
    }
}