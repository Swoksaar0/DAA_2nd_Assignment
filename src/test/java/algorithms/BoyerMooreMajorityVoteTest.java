package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreMajorityVoteTest {

    private Optional<Integer> bruteMajority(int[] a) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int v : a) {
            freq.merge(v, 1, Integer::sum);
        }
        int n = a.length;
        return freq.entrySet().stream()
                .filter(e -> e.getValue() > n/2)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Test
    public void testNullArray() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        assertThrows(IllegalArgumentException.class, () -> bm.findMajority(null));
    }

    @Test
    public void testEmptyArray() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        assertTrue(bm.findMajority(new int[0]).isEmpty());
    }

    @Test
    public void testSingleElement() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        assertEquals(Optional.of(7), bm.findMajority(new int[]{7}));
    }

    @Test
    public void testExactHalfNoMajorityEvenLength() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        int[] a = {1, 2, 1, 2};  // each appears exactly 2 times, 2 is not >4/2
        assertTrue(bm.findMajority(a).isEmpty());
    }

    @Test
    public void testMajorityAtBeginning() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        int[] a = {3, 3, 3, 1, 2, 3};
        assertEquals(Optional.of(3), bm.findMajority(a));
    }

    @Test
    public void testMajorityAtEnd() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        int[] a = {1, 2, 4, 2, 2, 2, 2};
        assertEquals(Optional.of(2), bm.findMajority(a));
    }

    @Test
    public void testNegativeNumbers() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        int[] a = {-1, -1, 2, -1, 3, -1};
        assertEquals(Optional.of(-1), bm.findMajority(a));
    }

    @Test
    public void testAllElementsSame() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        int n = 50;
        int[] a = new int[n];
        Arrays.fill(a, 42);
        assertEquals(Optional.of(42), bm.findMajority(a));
    }

    @Test
    public void testAllUnique() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        int[] a = {5, 3, 7, 1, 9};
        assertTrue(bm.findMajority(a).isEmpty());
    }

    @RepeatedTest(5)
    public void testRandomArrayWithGuaranteedMajority() {
        PerformanceTracker t = new PerformanceTracker();
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(t);
        int n = 1000;
        int maj = new Random().nextInt(10_000);
        int[] a = new int[n];
        for (int i = 0; i < n/2 + 10; i++) a[i] = maj;
        Random rnd = new Random(123);
        for (int i = n/2 + 10; i < n; i++) a[i] = rnd.nextInt(1000);
        Collections.shuffle(Arrays.asList(a.clone()));
        Optional<Integer> res = bm.findMajority(a);
        assertTrue(res.isPresent());
        assertEquals(maj, res.get());
    }

    @RepeatedTest(5)
    public void testRandomArrayWithoutMajority() {
        PerformanceTracker t = new PerformanceTracker();
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(t);
        int n = 1001;
        Random rnd = new Random(456);
        int[] a = rnd.ints(n, 0, 1000).toArray();
        for (int i = 0; i < n/2*2; i += 2) {
            a[i] = i; a[i+1] = i;
        }
        if (n % 2 != 0) a[n-1] = n;  // last odd
        Optional<Integer> res = bm.findMajority(a);
        assertTrue(res.isEmpty());
    }

    @Test
    public void testBruteForceComparisonOnSmallSamples() {
        PerformanceTracker t = new PerformanceTracker();
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(t);
        int[][] samples = {
                {2,2,1,2,3},
                {4,5,4,5,4,5,4},
                {7,8,9,8,8},
                {1,1,2,3,4,5,1}
        };
        for (int[] s : samples) {
            Optional<Integer> expected = bruteMajority(s);
            Optional<Integer> actual = bm.findMajority(s);
            assertEquals(expected, actual,
                    "Mismatch on sample " + Arrays.toString(s));
        }
    }
}