package ch.heig.sio.lab1.groupD.heuristics;

//MODIFIER, STOCKER LES INFORMATIONS POUR NE PAS REFAIRE DES CALCULS (MAJ) DEJA EXISTANT POUR EVITER LE O(n³) chercher O(n²)!!!!!!!!!!!!!!!!!!!! :^(

import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.groupD.Utilities.OptimizedLinkedList;
import ch.heig.sio.lab1.groupD.Utilities.Tuple;
import ch.heig.sio.lab1.tsp.TspData;

import java.util.ArrayList;
import java.util.Collections;

//Au début, on a un tableau de toutes les villes qui sont hors tournée, elles vont tous pointer vers la ville initiale.
//On parcourt pour trouver la ville la plus proche
//Ensuite, nous ajoutons la ville la plus proche et mettons à jour ce tableau des villes hors tournées. Elles vont soit pointer la ville 1 ou la ville 2
public class ClosestFirstInsert extends DistanceBasedInsert {

    @Override
    boolean cityDistanceSelection(int d1, int d2) {
        return d1 < d2;
    }

    @Override
    int getMinOrMax() {
        return Integer.MAX_VALUE;
    }
}
