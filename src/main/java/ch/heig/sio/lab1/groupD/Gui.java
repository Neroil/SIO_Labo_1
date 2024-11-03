package ch.heig.sio.lab1.groupD;

import ch.heig.sio.lab1.display.HeuristicComboItem;
import ch.heig.sio.lab1.display.TspSolverGui;
import ch.heig.sio.lab1.groupD.heuristics.ClosestFirstInsert;
import ch.heig.sio.lab1.groupD.heuristics.FarthestFirstInsert;
import ch.heig.sio.lab1.groupD.heuristics.RandomInsert;
import ch.heig.sio.lab1.sample.CanonicalTour;
//import com.formdev.flatlaf.FlatLightLaf;

public final class Gui {
  public static void main(String[] args) {
    HeuristicComboItem[] heuristics = {
        new HeuristicComboItem("Canonical tour", new CanonicalTour()),
            new HeuristicComboItem("Random insert", new RandomInsert()),
            new HeuristicComboItem("Closest First", new ClosestFirstInsert()),
            new HeuristicComboItem("Farthest First", new FarthestFirstInsert()),
    };

    // May not work on all platforms, comment out if necessary
    System.setProperty("sun.java2d.opengl", "true");

    //FlatLightLaf.setup();
    new TspSolverGui(1400, 800, "TSP solver", heuristics);
  }
}
