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
public abstract class DistanceBasedInsert extends GenericConstructiveHeuristic {
    OptimizedLinkedList<CityDistanceTuple> outsideCycleCitiesDistance;

    //TODO : FAIRE SA PROPRE LINKED LIST
    @Override
    public void insertLogic(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        outsideCycleCitiesDistance = new OptimizedLinkedList<>();

        int closestDistance = getMinOrMax();
        OptimizedLinkedList.Node<CityDistanceTuple> selectedCityNode = null;

        //Populate with distance to startCityIndex and get the closest
        for (int i = 0; i < data.getNumberOfCities(); ++i){
            if(i == startCityIndex) continue;

            int distance = data.getDistance(i,startCityIndex);
            OptimizedLinkedList.Node<CityDistanceTuple> currNode = outsideCycleCitiesDistance.add(new CityDistanceTuple(i,distance));

            if (cityDistanceSelection(distance, closestDistance)){
                closestDistance = distance;
                selectedCityNode = currNode;
            }
        }

        if(selectedCityNode == null){
            System.out.println("No closest city, make sure you have at least 2 cities");
            return;
        }

        //Boucle pour ajouter le reste
        do {
            //Add the closestCity
            insertCity(selectedCityNode.getValue().getIndex(),data);
            observer.update(calculateEdges());

            //Remove the city
            outsideCycleCitiesDistance.remove(selectedCityNode);
            //Update the distances and get the new city
            selectedCityNode = updateDistanceAndGetCity(selectedCityNode.getValue(),data);


        } while (!outsideCycleCitiesDistance.isEmpty() && selectedCityNode != null);
    }


    private OptimizedLinkedList.Node<CityDistanceTuple> updateDistanceAndGetCity(CityDistanceTuple cityToCompareTo, TspData data){
        OptimizedLinkedList.Node<CityDistanceTuple> currNode = outsideCycleCitiesDistance.getFirst();
        if(currNode == null) return null;

        //Update the distances
        do {
            int currDistance = currNode.getValue().getDistance();
            int maybeNextDistance = data.getDistance(cityToCompareTo.getIndex(),
                    currNode.getValue().getIndex());

            if(maybeNextDistance < currDistance){
                currNode.getValue().setDistance(maybeNextDistance);
            }

            currNode = currNode.getNext();
        } while (currNode != null);

        //Find the new closest city
        OptimizedLinkedList.Node<CityDistanceTuple> newClosestCityNode = outsideCycleCitiesDistance.getFirst();
        currNode = outsideCycleCitiesDistance.getFirst();
        do {

            if(cityDistanceSelection(currNode.getValue().getDistance(),newClosestCityNode.getValue().getDistance())){
                newClosestCityNode = currNode;
            }

            currNode = currNode.getNext();
        } while (currNode != null);

        return newClosestCityNode;
    }

    abstract boolean cityDistanceSelection(int d1, int d2);
    abstract int getMinOrMax();
}
