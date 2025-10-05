package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreMajorityVoteTest {

    @Test
    public void testNullArray() {
        PerformanceTracker t = new PerformanceTracker();
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(t);
        assertThrows(IllegalArgumentException.class, () -> bm.findMajority(null));
    }

    @Test
    public void testEmpty() {
        PerformanceTracker t = new PerformanceTracker();
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(t);
        assertTrue(bm.findMajority(new int[0]).isEmpty());
    }

    @Test
    public void testSingle() {
        PerformanceTracker t = new PerformanceTracker();
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(t);
        assertEquals(Optional.of(42), bm.findMajority(new int[]{42}));
    }

    @Test
    public void testNoMajority() {
        PerformanceTracker t = new PerformanceTracker();
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(t);
        assertTrue(bm.findMajority(new int[]{1,2,3,2,1}).isEmpty());
    }

    @Test
    public void testHasMajority() {
        PerformanceTracker t = new PerformanceTracker();
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(t);
        assertEquals(Optional.of(2), bm.findMajority(new int[]{1,2,2,3,2,4,2}));
    }

    @Test
    public void testAllSame() {
        PerformanceTracker t = new PerformanceTracker();
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(t);
        int[] a = new int[100];
        for (int i = 0; i < a.length; i++) a[i] = 7;
        assertEquals(Optional.of(7), bm.findMajority(a));
    }
}