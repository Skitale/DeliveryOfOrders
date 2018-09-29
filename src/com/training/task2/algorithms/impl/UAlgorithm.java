package com.training.task2.algorithms.impl;

import com.training.task2.structures.Model;
import com.training.task2.structures.Node;
import javafx.util.Pair;

import java.util.*;

public class UAlgorithm extends BaseAlgorithm {
    public UAlgorithm(Model model) {
        super(model);
        enableTdComparing = true;
    }

    @Override
    public int lowerBound(Node node) {
        int lb = 0;
        if (node.isExistLb()) {
            return node.getLowerBound();
        }
        List<Integer> negVertex = negativeVertexForNode(node);
        List<Integer> result = new ArrayList<>();
        List<Node> oneDeepNodes = new ArrayList<>();
        for (Integer i : negVertex) {
            oneDeepNodes.add(new Node(node, i));
        }

        for (Node n : oneDeepNodes) {
            int sum = 0;
            for (Integer i : negativeVertexForNode(n)) {
                sum += getSolution(new Node(n, i));
            }
            result.add(sum);
        }

        if(result.isEmpty()) {
            result.add(getSolution(node));
        }

        lb = Collections.min(result);
        node.setLowerBound(lb);
        return lb;
    }

    @Override
    public int upperBound(Node node) {
        return super.upperBound(node);
    }

    @Override
    public Node branching() {
        if (nodeList.isEmpty()) throw new UnsupportedOperationException();
        Node minLB = super.branching();
        double L = (double)minLB.getLowerBound() * 1.3d;
        Set<Node> perspective = new HashSet<>();


        for(Node node : nodeList){
            if(node.getLowerBound() <= L){
                perspective.add(node);
            }
        }


        int min_diff = Integer.MAX_VALUE;
        Node minNode = minLB;
        for(Node node : perspective){
            int diff = node.getUpperBound() - node.getLowerBound();
            if(diff < min_diff){
                min_diff = diff;
                minNode = node;
            }
        }
        return minNode;
    }
}
