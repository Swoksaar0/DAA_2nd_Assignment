package metrics;

public class PerformanceTracker {
    private long comparisons = 0;
    private long accesses = 0;
    private long swaps = 0;      // not used here, but scaffolded

    public void incComparisons() { comparisons++; }
    public void incAccesses(long n) { accesses += n; }
    public long getComparisons() { return comparisons; }
    public long getAccesses() { return accesses; }

    public void reset() {
        comparisons = accesses = swaps = 0;
    }

    @Override
    public String toString() {
        return String.format("comparisons=%d, accesses=%d", comparisons, accesses);
    }
}