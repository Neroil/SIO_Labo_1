package ch.heig.sio.lab1.groupD.heuristics;

import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.groupD.Utilities.OptimizedLinkedList;
import ch.heig.sio.lab1.tsp.TspData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomInsert extends GenericConstructiveHeuristic{
    ArrayList<Integer> citiesToVisit;
    Random rand = new Random();
    int citiesToVisitIndex;

    @Override
    public void insertLogic(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        //Initialisation de l'ordre de visite
        generateShuffledCityToVisit(data, startCityIndex);


        //Ajoute assez de ville pour avoir un triangle
        for(int i = 1; i < 3; ++i){
            cycleCities.add(getUnusedCity());
        }

        //Prenons un triangle, il faut ensuite choisir quelle arête de ce triangle enlever pour avoir un poids minimum
        // lors de l'insertion de la ville aléatoire
        //Ne pas stocker d'arêtes
        //Heuristique gloutonne page 47 !


        while(citiesToVisitIndex < data.getNumberOfCities() - 1){ //nbCities - 1 since we remove the start city from the index we built earlier !
            insertCity(getUnusedCity(),data);
            observer.update(calculateEdges());
        }
    }

    /**
     * Returns
     *
     * @return a random city that hasn't been visited before
     */
    private int getUnusedCity() {
        return citiesToVisit.get(citiesToVisitIndex++);
    }

    private void generateShuffledCityToVisit(TspData data, int startCityIndex){
        rand.setSeed(0x134DA73);
        int nbOfCities = data.getNumberOfCities();
        citiesToVisitIndex = 0;
        citiesToVisit = new ArrayList<>(nbOfCities);

        for (int i = 0; i < nbOfCities; ++i) {
            citiesToVisit.add(i);
        }
        citiesToVisit.remove(startCityIndex); //Remove the initial city

        Collections.shuffle(citiesToVisit, rand);
    }
}
