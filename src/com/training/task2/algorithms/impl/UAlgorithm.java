package com.training.task2.algorithms.impl;

import com.training.task2.structures.Model;
import com.training.task2.structures.Node;
import javafx.util.Pair;

import java.util.*;

public class UAlgorithm extends BaseAlgorithm {
    public UAlgorithm(Model model) {
        super(model);
        enableTdComparing = false;
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
        Pair<Integer, Node> minH = foundMinUpperBound(nodeList);
        int maxL = -1;
        Node nMaxL = minH.getValue();
        for (int i = 0; i < nodeList.size(); i++) {
            if (upperBound(nodeList.get(i)) == minH.getKey()) {
                if (maxL < lowerBound(nodeList.get(i)) && lowerBound(nodeList.get(i)) != upperBound(nodeList.get(i))) {
                    maxL = lowerBound(nodeList.get(i));
                    nMaxL = nodeList.get(i);
                }
            }
        }
        return nMaxL;
    }
}
