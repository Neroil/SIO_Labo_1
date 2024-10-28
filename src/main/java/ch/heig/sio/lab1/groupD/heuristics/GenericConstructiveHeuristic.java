package ch.heig.sio.lab1.groupD.heuristics;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.groupD.Utilities.OptimizedLinkedList;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class GenericConstructiveHeuristic implements ObservableTspConstructiveHeuristic {

    //Meilleures solution possible (à éviter, faut réfléchir)
    OptimizedLinkedList<Integer> cycleCities;

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {

        int nbCities = data.getNumberOfCities();

        //Initialisation du tableau des opérations
        cycleCities = new OptimizedLinkedList<>();

        //Initialisation du tableau à retourner :
        int[] finalCycle = new int[nbCities];

        //La ville de point de départ
        cycleCities.add(startCityIndex); //Taille de la linked list de 1

        insertLogic(data, startCityIndex, observer);

        //Population du tabPassage

        //Get le premier Node
        OptimizedLinkedList.Node<Integer> currNode = cycleCities.getFirst();
        for(int i = 0; i < nbCities; ++i){
            finalCycle[i] = currNode.getValue();
            currNode = currNode.getNext();
        }

        return new TspTour(data, finalCycle, nbCities);
    }

    public abstract void insertLogic(TspData data, int startCityIndex, TspHeuristicObserver observer);

    public Iterator<Edge> calculateEdges(){
        List<Edge> res = new ArrayList<>();

        int size = cycleCities.size();
        for(int i = 0; i < size; i++) {

            int next = (i + 1) % size;

            res.add(new Edge(cycleCities.get(i), cycleCities.get(next)));
        }
        return res.iterator();
    }

    public void insertCity(int index, TspData data){
        //Find the shortest distance between two already existing vertices
        int bestDistance = Integer.MAX_VALUE;
        int indexInsert = -1;

        for(int i = 0; i < cycleCities.size() - 1; ++i) {
            int indexPlus = i + 1 % cycleCities.size();
            int calculatedDist = data.getDistance(cycleCities.get(i), index) + data.getDistance(index, cycleCities.get(indexPlus)) - data.getDistance(cycleCities.get(i), cycleCities.get(indexPlus));
            if (calculatedDist < bestDistance) {
                bestDistance = calculatedDist;
                indexInsert = indexPlus;
            }
        }
        //The best distance has been found, insert the city now
        if(bestDistance != Integer.MAX_VALUE && indexInsert != -1){
            cycleCities.add(indexInsert,index);
        }
    }

}
