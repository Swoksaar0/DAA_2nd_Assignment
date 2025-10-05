package algorithms;

import metrics.PerformanceTracker;

import java.util.Optional;

public class BoyerMooreMajorityVote {
    private final PerformanceTracker tracker;

    public BoyerMooreMajorityVote(PerformanceTracker tracker) {
        if (tracker == null) {
            throw new IllegalArgumentException("PerformanceTracker must not be null");
        }
        this.tracker = tracker;
    }

    public Optional<Integer> findMajority(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("Input array must not be null");
        }
        if (a.length == 0) {
            return Optional.empty();
        }
        if (a.length == 1) {
            return Optional.of(a[0]);
        }

        tracker.reset();
        tracker.startTimer();

        Optional<Integer> candOpt = findCandidate(a);
        if (candOpt.isEmpty()) {
            tracker.stopTimer();
            return Optional.empty();
        }

        boolean ok = verify(a, candOpt.get());
        tracker.stopTimer();
        return ok ? candOpt : Optional.empty();
    }

    private Optional<Integer> findCandidate(int[] a) {
        int n = a.length;
        tracker.incArrayAccesses(1);      // чтение length
        tracker.incArrayAccesses(1);      // чтение a[0]
        int candidate = a[0];
        int count = 1;

        for (int i = 1; i < n; i++) {
            tracker.incArrayAccesses(1);  // чтение a[i]
            if (count == 0) {
                candidate = a[i];
                tracker.incAllocations(); // логическая смена кандидата
                count = 1;
            } else {
                tracker.incComparisons();
                if (a[i] == candidate) {
                    count++;
                } else {
                    count--;
                }
            }
        }
        return Optional.of(candidate);
    }

    private boolean verify(int[] a, int candidate) {
        int cnt = 0;
        for (int v : a) {
            tracker.incArrayAccesses(1);
            tracker.incComparisons();
            if (v == candidate) {
                cnt++;
            }
        }
        return cnt > a.length / 2;
    }
}