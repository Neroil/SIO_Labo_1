package ch.heig.sio.lab1.groupD.Utilities;

import ch.heig.sio.lab1.tsp.TspData;

public class CityTuple {
    public final int index;
    public final TspData.City city;

    public CityTuple(int a, TspData.City b) {
        this.index = a;
        this.city = b;
    }
}