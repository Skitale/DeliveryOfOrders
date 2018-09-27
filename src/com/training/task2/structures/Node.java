package com.training.task2.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private List<Integer> vertex;
    private int lowerBound = -1;
    private int upperBound = -1;
    private boolean lbFlag = false;
    private boolean ubFlag = false;

    public Node() {
        vertex = new ArrayList<>();
    }

    public Node(Integer... array) {
        vertex = new ArrayList<>(Arrays.asList(array));
    }

    public Node(List<Integer> vertex) {
        this.vertex = new ArrayList<>(vertex);
    }

    public Node(List<Integer> vertex, Integer... array) {
        this(vertex);
        vertex.addAll(Arrays.asList(array));
    }

    public Node(Node vert, Integer... array) {
        vertex = new ArrayList<>(vert.vertex);
        vertex.addAll(Arrays.asList(array));
    }

    public int getLength() {
        return vertex.size();
    }

    public int getVItem(int i) {
        if (i < 0 || i >= vertex.size()) throw new UnsupportedOperationException();
        return vertex.get(i);
    }

    public void addVItem(int value) {
        ubFlag = false;
        lbFlag = false;
        vertex.add(value);
    }

    public boolean isExist(int value) {
        return vertex.contains(value);
    }

    public int getLowerBound() {
        if(!isExistLb()) throw new UnsupportedOperationException();
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        lbFlag = true;
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        if(!isExistUb()) throw new UnsupportedOperationException();
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        ubFlag = true;
        this.upperBound = upperBound;
    }

    public boolean isExistLb() {
        return lbFlag;
    }

    public boolean isExistUb() {
        return ubFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (lowerBound != node.lowerBound) return false;
        if (upperBound != node.upperBound) return false;
        return vertex.equals(node.vertex);
    }

    @Override
    public int hashCode() {
        int result = vertex.hashCode();
        result = 31 * result + lowerBound;
        result = 31 * result + upperBound;
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "vertex=" + vertex.toString() +
                '}';
    }
}
