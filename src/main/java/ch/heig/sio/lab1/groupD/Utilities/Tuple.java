package ch.heig.sio.lab1.groupD.Utilities;

public class Tuple<T,U>{
    private T firstValue;
    private U secondValue;

    public Tuple(T v1, U v2){
        firstValue = v1;
        secondValue = v2;
    }

    public T getFirst() {
        return firstValue;
    }

    public U getSecond(){
        return secondValue;
    }

    public void setFirst(T val){
        firstValue = val;
    }

    public void setSecond(U val){
        secondValue = val;
    }
}
