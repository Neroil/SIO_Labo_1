package ch.heig.sio.lab1.groupD.heuristics;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.groupD.Utilities.CityTuple;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

//Quelque soit la méthode d'insertion, on le met toujours au meilleur endroit

public class RandomInsert implements ObservableTspConstructiveHeuristic {
    Random rand = new Random(0x134DA73);
    ArrayList<Integer> citiesToVisit;
    int[] tabPassage;
    int citiesToVisitIndex;
    TspData data;
    int startCityIndex;

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        if(this.data == null) this.data = data;
        if(this.startCityIndex != startCityIndex) this.startCityIndex = startCityIndex;

        int nbCities = data.getNumberOfCities();

        //Initialisation du tableau de passage :
        this.tabPassage = new int[nbCities];

        //La ville de point de départ
        TspData.City firstCity = data.getCityCoord(startCityIndex);
        tabPassage[0] = startCityIndex;

        //Ajoute assez de ville pour avoir un triangle
        for(int i = 1; i < 3; ++i){
            tabPassage[i] = getUnusedCity().index;
        }

        //Prenons un triangle, il faut ensuite choisir quelle arête de ce triangle enlever pour avoir un poids minimum
        // lors de l'insertion de la ville aléatoire
        //Ne pas stocker d'arêtes
        //Heuristique gloutonne page 47 !




        return new TspTour(data, tabPassage, 0);
    }

    public Iterator<Edge> calculateEdges(){
        //TODO: For the observer update
        return null;
    }

    public void insertCity(TspData.City city){
        //Find the shortest distance between two already existing vertice
    }

    /**
     * Returns
     *
     * @return a random city that hasn't been visited before
     */
    private CityTuple getUnusedCity() {
        if (citiesToVisit == null) {
            int nbOfCities = data.getNumberOfCities();
            citiesToVisitIndex = 0;
            citiesToVisit = new ArrayList<>(nbOfCities);
            for (int i = 0; i < nbOfCities; ++i) {
                citiesToVisit.add(i);
            }
            citiesToVisit.remove(startCityIndex); //Remove the initial city

            Collections.shuffle(citiesToVisit, rand);
        }
        return new CityTuple(citiesToVisitIndex,data.getCityCoord(citiesToVisit.get(citiesToVisitIndex++)));
    }
}
