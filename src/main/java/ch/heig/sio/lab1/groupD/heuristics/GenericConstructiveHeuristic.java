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

    public Iterator<Edge> calculateEdges() {
        List<Edge> res = new ArrayList<>();
        OptimizedLinkedList.Node<Integer> currentNode = cycleCities.getFirst();

        if (currentNode == null) {
            return res.iterator();
        }

        OptimizedLinkedList.Node<Integer> firstNode = currentNode;
        do {
            OptimizedLinkedList.Node<Integer> nextNode = currentNode.getNext();
            if (nextNode == null) {
                nextNode = firstNode; // Loop back to the start
            }
            res.add(new Edge(currentNode.getValue(), nextNode.getValue()));
            currentNode = nextNode;
        } while (currentNode != firstNode);

        return res.iterator();
    }

    public void insertCity(int index, TspData data) {
        // Find the shortest distance between two already existing vertices
        int bestDistance = Integer.MAX_VALUE;
        OptimizedLinkedList.Node<Integer> bestNode = null;

        OptimizedLinkedList.Node<Integer> currentNode = cycleCities.getFirst();
        if (currentNode == null) {
            return;
        }

        OptimizedLinkedList.Node<Integer> firstNode = currentNode;
        do {
            OptimizedLinkedList.Node<Integer> nextNode = currentNode.getNext();
            if (nextNode == null) {
                nextNode = firstNode; // Loop back to the start
            }

            int calculatedDist = data.getDistance(currentNode.getValue(), index) +
                    data.getDistance(index, nextNode.getValue()) -
                    data.getDistance(currentNode.getValue(), nextNode.getValue());

            if (calculatedDist < bestDistance) {
                bestDistance = calculatedDist;
                bestNode = currentNode;
            }

            currentNode = nextNode;
        } while (currentNode != firstNode);

        // The best distance has been found, insert the city now
        if (bestNode != null) {
            cycleCities.insertAfter(bestNode, index);
        }
    }

}
