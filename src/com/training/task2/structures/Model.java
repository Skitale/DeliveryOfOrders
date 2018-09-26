package com.training.task2.structures;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private int n;
    private List<Integer> tD;
    private List<List<Integer>> t;
    private boolean validation = false;

    public Model(int n) {
        if(n <= 0) throw new UnsupportedOperationException();
        this.n = n;
        tD = new ArrayList<>(n);
        fillListNumber(tD, n, -1);
        t = new ArrayList<>(n + 1);
        for(int i = 0; i < n + 1; i++){
            t.add(i, new ArrayList<>(n + 1));
            fillListNumber(t.get(i), n + 1, -1);
        }
    }

    public void setTd(int i, int value) {
        i = i - 1;
        validateTdIndex(i);
        validateValueNotNegative(value);
        tD.set(i, value);
        setValidUnknown();
    }

    public void setT(int i, int j, int value) {
        validateTIndexes(i, j);
        validateValueNotNegative(value);
        t.get(i).set(j, value);
        setValidUnknown();
    }

    private void fillListNumber(List<Integer> list, int size, int num){
        for(int i = 0; i < size; i++){
            list.add(num);
        }
    }

    private void setValidSuccess(){
        validation = true;
    }

    private void setValidUnknown(){
        validation = false;
    }

    private void validateTdIndex(int i){
        if (i < 0 || i >= n) throw new ArrayIndexOutOfBoundsException();
    }

    private void validateTIndexes(int i, int j){
        if (i < 0 || i >= n + 1 || j < 0 || j >= n + 1) throw new ArrayIndexOutOfBoundsException();
    }

    private void validateValueNotNegative(int value){
        if(value < 0) throw new UnsupportedOperationException();
    }

    public int getN() {
        if (!isValid()) throw new UnsupportedOperationException();
        return n;
    }

    public int getT(int i, int j){
        if (!isValid()) throw new UnsupportedOperationException();
        validateTIndexes(i, j);
        return t.get(i).get(j);
    }

    public int getTd(int i){
        i = i - 1;
        if(!isValid()) throw new UnsupportedOperationException();
        validateTdIndex(i);
        return tD.get(i);
    }

    public boolean isValid(){
        if(validation) return true;
        for(Integer id : tD){
            if(id.equals(-1)){
                return false;
            }
        }

        for(List<Integer> col : t){
            for(Integer i : col){
                if(i.equals(-1)){
                    return false;
                }
            }
        }
        setValidSuccess();
        return true;
    }
}
