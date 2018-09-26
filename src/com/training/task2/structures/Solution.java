package com.training.task2.structures;

public class Solution {
    private Node optimalNode;
    private int optimalNumViolations;
    private int numPasses;

    public Solution(Node optimalNode, int optimalNumViolations, int numPasses) {
        this.optimalNode = optimalNode;
        this.optimalNumViolations = optimalNumViolations;
        this.numPasses = numPasses;
    }

    public Node getOptimalNode() {
        return optimalNode;
    }

    public int getOptimalNumViolations() {
        return optimalNumViolations;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "optimalNode=" + optimalNode +
                ", optimalNumViolations=" + optimalNumViolations +
                ", numPasses=" + numPasses +
                '}';
    }
}
