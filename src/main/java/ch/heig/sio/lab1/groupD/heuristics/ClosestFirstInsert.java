package ch.heig.sio.lab1.groupD.heuristics;

//MODIFIER, STOCKER LES INFORMATIONS POUR NE PAS REFAIRE DES CALCULS (MAJ) DEJA EXISTANT POUR EVITER LE O(n³) chercher O(n²)!!!!!!!!!!!!!!!!!!!! :^(

import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;

import java.util.ArrayList;
import java.util.Collections;

//Au début, on a un tableau de toutes les villes qui sont hors tournée, elles vont tous pointer vers la ville initiale.
//On parcourt pour trouver la ville la plus proche
//Ensuite, nous ajoutons la ville la plus proche et mettons à jour ce tableau des villes hors tournées. Elles vont soit pointer la ville 1 ou la ville 2
public class ClosestFirstInsert extends GenericConstructiveHeuristic {
    ArrayList<Integer> outsideCycleCitiesDistance;

    //TODO : FAIRE SA PROPRE LINKED LIST
    @Override
    public void insertLogic(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        outsideCycleCitiesDistance = new ArrayList<>(data.getNumberOfCities());

        int closestDistance = Integer.MAX_VALUE;
        int closestCityIndex = Integer.MAX_VALUE;
        //Populate with distance to startCityIndex and get the closest
        for (int i = 0; i < data.getNumberOfCities(); ++i){
            if(i == startCityIndex) continue;
            int distance = data.getDistance(i,startCityIndex);
            outsideCycleCitiesDistance.set(i, distance);
            if (distance < closestDistance){
                closestDistance = distance;
                closestCityIndex = i;
            }
        }

        //Add the second city to the final array
        if(closestCityIndex == Integer.MAX_VALUE){
            System.out.println("Wtf ??");
            return;
        }
        cycleCities.add(closestCityIndex);

        //Update the distances
        for (int i = 0; i < outsideCycleCitiesDistance.size(); ++i){
            int currDistance = outsideCycleCitiesDistance.get(i);
            int maybeNextValue = data.getDistance(closestCityIndex, i);

            if(maybeNextValue < currDistance){
                outsideCycleCitiesDistance.set(i, maybeNextValue);
            }
        }

        //Boucle pour ajouter le reste
    }



}
