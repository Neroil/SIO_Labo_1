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
public class ClosestFirstInsert extends GenericConstructiveHeuristic {
    OptimizedLinkedList<CityDistanceTuple> outsideCycleCitiesDistance;

    //TODO : FAIRE SA PROPRE LINKED LIST
    @Override
    public void insertLogic(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        outsideCycleCitiesDistance = new OptimizedLinkedList<>();

        int closestDistance = Integer.MAX_VALUE;
        OptimizedLinkedList.Node<CityDistanceTuple> closestCityNode = null;
        //Populate with distance to startCityIndex and get the closest
        for (int i = 0; i < data.getNumberOfCities(); ++i){
            if(i == startCityIndex) continue;
            int distance = data.getDistance(i,startCityIndex);
            OptimizedLinkedList.Node<CityDistanceTuple> currNode = outsideCycleCitiesDistance.add(new CityDistanceTuple(i,distance));
            if (distance < closestDistance){
                closestDistance = distance;
                closestCityNode = currNode;
            }
        }

        //Add the second city to the final array
        if(closestCityNode == null){
            System.out.println("Wtf ??");
            return;
        }
        cycleCities.add(closestCityNode.getValue().getIndex());

        //Remove the node from the list
        outsideCycleCitiesDistance.remove(closestCityNode);

        //Update the distances
//        for (int i = 0; i < outsideCycleCitiesDistance.size(); ++i){
//            int currDistance = outsideCycleCitiesDistance.get(i);
//            int maybeNextValue = data.getDistance(closestCityIndex, i);
//
//            if(maybeNextValue < currDistance){
//                outsideCycleCitiesDistance.set(i, maybeNextValue);
//            }
//        }
        OptimizedLinkedList.Node<CityDistanceTuple> currNode = outsideCycleCitiesDistance.getFirst();

        do {
            int currDistance = currNode.getValue().getDistance();
            int maybeNextValue = data.getDistance(closestCityNode.getValue().getIndex(),
                                                    currNode.getValue().getIndex());

            if(maybeNextValue < currDistance){
                currNode.getValue().setDistance(maybeNextValue);
            }

            currNode = currNode.getNext();
        } while (currNode.getNext() != null);

        //Boucle pour ajouter le reste
//        OptimizedLinkedList.Node<Integer> currNode = new Opt;
//        do {
//
//        } while ();
    }



}
