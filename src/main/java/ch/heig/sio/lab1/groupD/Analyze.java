package ch.heig.sio.lab1.groupD;

import ch.heig.sio.lab1.display.HeuristicComboItem;
import ch.heig.sio.lab1.groupD.heuristics.ClosestFirstInsert;
import ch.heig.sio.lab1.groupD.heuristics.FurthestFirstInsert;
import ch.heig.sio.lab1.groupD.heuristics.RandomInsert;
import ch.heig.sio.lab1.sample.CanonicalTour;
import ch.heig.sio.lab1.tsp.TspData;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.max;
import static java.util.Collections.min;

public final class Analyze {

  public static void main(String[] args) {
    // TODO
    //  - Renommer le package ;
    //  - Implémenter les différentes heuristiques en écrivant le code dans ce package, et uniquement celui-ci
    //    (sous-packages et packages de tests ok) ;
    //  - Factoriser le code commun entre les différentes heuristiques ;
    //  - Documentation soignée comprenant :
    //    - la javadoc, avec auteurs et description des implémentations ;
    //    - des commentaires sur les différentes parties de vos algorithmes.

    // Longueurs optimales :
    // pcb442 : 50778
    // att532 : 86729
    // u574 : 36905
    // pcb1173   : 56892
    // nrw1379  : 56638
    // u1817 : 57201

    //FAIRE TEST

    // Exemple de lecture d'un jeu de données :
    // TspData data = TspData.fromFile("data/att532.dat");

    // Min max, moyenne, médiane et écart-type des longueurs de tournées pour chaque heuristique
    // Absolu et relatif par rapport à la longueur optimale

    //Tableau de toutes les heuristiques :
    HeuristicComboItem[] heuristics = {
            new HeuristicComboItem("Random insert", new RandomInsert()),
            new HeuristicComboItem("Closest First", new ClosestFirstInsert()),
            new HeuristicComboItem("Furthest First", new FurthestFirstInsert()),
    };

    // Tableau de tous les fichiers :
     String[] files = {"pcb442", "att532", "u574", "pcb1173", "nrw1379", "u1817"};

     for (String file : files){
       try {

         TspData data = TspData.fromFile("data/" + file + ".dat");
         System.out.println("Processing file " + file + ".dat");

         int size = data.getNumberOfCities();
         System.out.println("Size of dataset : " + size);
         ArrayList<Long> results = new ArrayList<>(size);
         long meanValue = 0;

         for (HeuristicComboItem heuristic : heuristics){

           //Clear the results
           results.clear();
           meanValue = 0;


           for(int i = 0; i < size; ++i){
             long length = heuristic.computeTour(data,i).length();
             results.add(length);
             meanValue += length;

           }

           long mean = meanValue / (long)size;

           System.out.println("Processing heuristic : " + heuristic + " for file " + file + ".dat");
              System.out.println("Min : " + min(results));
              System.out.println("Max : " + max(results));
              System.out.println("Moyenne : " + mean );
              System.out.println("Médiane : " + median(results));
              System.out.println("Ecart-type : " + stdDev(results, mean));
         }

        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier " + file + ".dat");
            System.err.println(e.getMessage());
            return;
       }

     }

  }

  public static double median(List<Long> values) {
    Collections.sort(values);
    int middle = values.size() / 2;
    if (values.size() % 2 == 0) {
      return (values.get(middle - 1) + values.get(middle)) / 2.0;
    } else {
      return values.get(middle);
    }
  }

  public static double stdDev(List<Long> values, long mean) {
    double sum = 0;
    for (Long value : values) {
      sum += Math.pow(value - mean, 2);
    }
    return Math.sqrt(sum / values.size());
  }


}
