package ch.heig.sio.lab1.groupD;


import ch.heig.sio.lab1.TestUtils;
import ch.heig.sio.lab1.groupD.heuristics.ClosestFirstInsert;
import ch.heig.sio.lab1.groupD.heuristics.FarthestFirstInsert;
import ch.heig.sio.lab1.groupD.heuristics.RandomInsert;
import ch.heig.sio.lab1.tsp.TspConstructiveHeuristic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for the heuristics
 * @author Edwin Häffner
 * @author Arthur Junod
 */
public class TestHeuristics {
    private static TspConstructiveHeuristic randomInsert;
    private static TspConstructiveHeuristic farthestInsert;
    private static TspConstructiveHeuristic closestInsert;

    @BeforeAll
    public static void setUpHeuristic(){
        randomInsert = new RandomInsert();
        farthestInsert = new FarthestFirstInsert();
        closestInsert = new ClosestFirstInsert();
    }

    @Test
    public void testOneCity() {
        TspConstructiveHeuristic[] tabHeuristics = {randomInsert, farthestInsert, closestInsert};
        TestUtils.testAll(tabHeuristics, TestUtils.ONE_CITY);
    }

    @Test
    public void testTwoCities() {
        TspConstructiveHeuristic[] tabHeuristics = {randomInsert, farthestInsert, closestInsert};
        TestUtils.testAll(tabHeuristics, TestUtils.TWO_CITIES);
    }

    @Test
    public void testThreeCities() {
        TspConstructiveHeuristic[] tabHeuristics = {randomInsert, farthestInsert, closestInsert};
        TestUtils.testAll(tabHeuristics, TestUtils.THREE_CITIES);
    }

    @Test
    public void testEx1Closest() {
        TestUtils.test(closestInsert, TestUtils.EXERCISE_1_NI);
    }

    @Test
    public void testEx1Farthest() {
        TestUtils.test(farthestInsert, TestUtils.EXERCISE_1_FI);
    }
}
