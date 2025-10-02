package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreMajorityVoteTest {

    private BoyerMooreMajorityVote create() {
        return new BoyerMooreMajorityVote(new PerformanceTracker());
    }

    @Test
    public void testEmpty() {
        assertTrue(create().findMajority(new int[0]).isEmpty());
    }

    @Test
    public void testNull() {
        assertTrue(create().findMajority(null).isEmpty());
    }

    @Test
    public void testSingle() {
        assertEquals(Optional.of(7), create().findMajority(new int[]{7}));
    }

    @Test
    public void testNoMajority() {
        assertTrue(create().findMajority(new int[]{1,2,3,2,1}).isEmpty());
    }

    @Test
    public void testHasMajority() {
        assertEquals(Optional.of(2),
                create().findMajority(new int[]{1,2,2,3,2,4,2}));
    }

    @Test
    public void testAllSame() {
        int[] a = new int[50];
        for (int i = 0; i < a.length; i++) a[i] = 5;
        assertEquals(Optional.of(5), create().findMajority(a));
    }
}