package metrics;

public class PerformanceTracker {
    private long comparisons   = 0;
    private long swaps         = 0;
    private long arrayAccesses = 0;
    private long allocations   = 0;

    private long startTimeNs   = 0;
    private long elapsedTimeNs = 0;

    public void incComparisons()           { comparisons++; }
    public void incSwaps()                 { swaps++; }
    public void incArrayAccesses(long n)   { arrayAccesses += n; }
    public void incAllocations()           { allocations++; }

    public void startTimer() {
        startTimeNs = System.nanoTime();
    }

    public void stopTimer() {
        elapsedTimeNs = System.nanoTime() - startTimeNs;
    }

    public long getComparisons()   { return comparisons; }
    public long getSwaps()         { return swaps; }
    public long getArrayAccesses(){ return arrayAccesses; }
    public long getAllocations()   { return allocations; }

    public long getElapsedTimeNs() { return elapsedTimeNs; }

    public void reset() {
        comparisons = swaps = arrayAccesses = allocations = 0;
        elapsedTimeNs = 0;
    }

    @Override
    public String toString() {
        return String.format(
                "timeNs=%d, comparisons=%d, swaps=%d, arrayAccesses=%d, allocations=%d",
                elapsedTimeNs, comparisons, swaps, arrayAccesses, allocations
        );
    }
}