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
        List<Node> oneDeepNodes = new ArrayList<>();
        for (Integer i : negVertex) {
            oneDeepNodes.add(new Node(node, i));
        }

        List<Integer> result = new ArrayList<>();
        for (Node n : oneDeepNodes) {
            for (Integer i : negativeVertexForNode(n)) {
                result.add(getSolution(new Node(n, i)));
            }
        }

        if(result.isEmpty()) {
            result.add(getSolution(node));
        }

        lb = Collections.max(result);
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
        Pair<Integer, Node> minUpperBound = foundMinUpperBound(nodeList, false);
        double L = (double)minUpperBound.getKey() * 1.5d;
        Set<Node> perspective = new HashSet<>();


        for(Node node : nodeList){
            if(node.getUpperBound() <= L){
                perspective.add(node);
            }
        }


        int min_diff = Integer.MAX_VALUE;
        Node minNode = minUpperBound.getValue();
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
