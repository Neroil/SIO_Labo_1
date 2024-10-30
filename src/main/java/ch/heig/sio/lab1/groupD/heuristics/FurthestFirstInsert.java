package ch.heig.sio.lab1.groupD.heuristics;

public class FurthestFirstInsert extends DistanceBasedInsert{
    @Override
    boolean cityDistanceSelection(int d1, int d2) {
        return d1 > d2;
    }

    @Override
    int getMinOrMax() {
        return Integer.MIN_VALUE;
    }
}
