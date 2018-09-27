package com.training.task2.structures;

import java.util.List;

public class Solution {
    private List<Integer> optimalPath;
    private int optimalNumViolations;
    private int numPasses;

    public Solution(List<Integer> optimalPath, int optimalNumViolations, int numPasses) {
        this.optimalPath = optimalPath;
        this.optimalNumViolations = optimalNumViolations;
        this.numPasses = numPasses;
    }

    public List<Integer> getOptimalPath() {
        return optimalPath;
    }

    public int getOptimalNumViolations() {
        return optimalNumViolations;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "optimalPath=" + optimalPath +
                ", optimalNumViolations=" + optimalNumViolations +
                ", numUsedVertex=" + numPasses +
                '}';
    }
}
