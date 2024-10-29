package ch.heig.sio.lab1.groupD.heuristics;

import ch.heig.sio.lab1.groupD.Utilities.Tuple;

public class CityDistanceTuple extends Tuple<Integer,Integer> {

    public CityDistanceTuple(int index, int distance) {
        super(index, distance);
    }

    public int getIndex(){
        return super.getFirst();
    }

    public int getDistance(){
        return super.getSecond();
    }

    public void setDistance(int distance){
        super.setSecond(distance);
    }

}
