package algorithms;

import metrics.PerformanceTracker;
import java.util.Optional;

public class BoyerMooreMajorityVote {
    private final PerformanceTracker tracker;

    public BoyerMooreMajorityVote(PerformanceTracker tracker) {
        this.tracker = tracker;
    }

    public Optional<Integer> findMajority(int[] a) {
        tracker.reset();
        if (a == null || a.length == 0) {
            return Optional.empty();
        }

        // Phase 1: find candidate
        int candidate = a[0], count = 1;
        tracker.incAccesses(1);
        for (int i = 1; i < a.length; i++) {
            tracker.incAccesses(1);
            if (count == 0) {
                candidate = a[i];
                count = 1;
                tracker.incAccesses(1);
            } else {
                tracker.incComparisons();
                if (a[i] == candidate) {
                    count++;
                } else {
                    count--;
                }
            }
        }

        // Phase 2: verify candidate with early exit
        int c = 0;
        int threshold = a.length / 2;
        for (int v : a) {
            tracker.incAccesses(1);
            tracker.incComparisons();
            if (v == candidate) {
                c++;
                if (c > threshold) {
                    return Optional.of(candidate);
                }
            }
        }
        return Optional.empty();
    }
}