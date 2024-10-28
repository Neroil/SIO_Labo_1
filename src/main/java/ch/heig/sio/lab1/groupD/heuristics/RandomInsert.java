package ch.heig.sio.lab1.groupD.heuristics;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.groupD.Utilities.CityTuple;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.*;

//Quelque soit la méthode d'insertion, on le met toujours au meilleur endroit
//Faire une linked list a la main peut être bien

public class RandomInsert implements ObservableTspConstructiveHeuristic {
    Random rand = new Random();
    ArrayList<Integer> citiesToVisit;
    int[] tabPassage;
    //Meilleures solution possible (à éviter, faut réfléchir)
    ArrayList<Integer> operationTab;
    int citiesToVisitIndex;
    int startCityIndex;

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        if(this.startCityIndex != startCityIndex) this.startCityIndex = startCityIndex;

        int nbCities = data.getNumberOfCities();

        //Initialisation du tableau des opérations
        operationTab = new ArrayList<>(nbCities);
        //Initialisation du tableau de passage :
        this.tabPassage = new int[nbCities];
        //Initialisation de l'ordre de visite
        generateShuffledCityToVisit(data);

        //La ville de point de départ
        operationTab.add(startCityIndex);

        //Ajoute assez de ville pour avoir un triangle
        for(int i = 1; i < 3; ++i){
            operationTab.add(getUnusedCity());
        }

        //Prenons un triangle, il faut ensuite choisir quelle arête de ce triangle enlever pour avoir un poids minimum
        // lors de l'insertion de la ville aléatoire
        //Ne pas stocker d'arêtes
        //Heuristique gloutonne page 47 !


        while(citiesToVisitIndex < nbCities - 1){ //nbCities - 1 since we remove the start city from the index we built earlier !
            if(citiesToVisitIndex == nbCities - 2){
                //Do nothing
                System.out.println("BANCO");
            }
            insertCity(getUnusedCity(),data);
            observer.update(calculateEdges());
        }

        //Population du tabPassage
        for(int i = 0; i < nbCities; ++i)
            tabPassage[i] = operationTab.get(i);

        return new TspTour(data, tabPassage, nbCities);
    }

    public Iterator<Edge> calculateEdges(){
        List<Edge> res = new ArrayList<>();
        //TODO: For the observer update
        int size = operationTab.size();
        for(int i = 0; i < size; i++) {

            int next = (i + 1) % size;

            res.add(new Edge(operationTab.get(i),operationTab.get(next)));
        }
        return res.iterator();
    }

    public void insertCity(int index, TspData data){
        //Find the shortest distance between two already existing vertices
        int bestDistance = Integer.MAX_VALUE;
        int indexInsert = -1;

        for(int i = 0; i < operationTab.size() - 1; ++i) {
            int indexPlus = i + 1 % operationTab.size();
            int calculatedDist = data.getDistance(operationTab.get(i), index) + data.getDistance(index, operationTab.get(indexPlus)) - data.getDistance(operationTab.get(i), operationTab.get(indexPlus));
            if (calculatedDist < bestDistance) {
                bestDistance = calculatedDist;
                indexInsert = indexPlus;
            }
        }
        //The best distance has been found, insert the city now
        if(bestDistance != Integer.MAX_VALUE && indexInsert != -1){
            operationTab.add(indexInsert,index);
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

    private void generateShuffledCityToVisit(TspData data){
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
