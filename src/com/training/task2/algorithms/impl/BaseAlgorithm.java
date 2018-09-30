package com.training.task2.algorithms.impl;

import com.training.task2.algorithms.AbstractAlgorithm;
import com.training.task2.structures.Model;
import com.training.task2.structures.Node;

import java.util.*;

public class BaseAlgorithm extends AbstractAlgorithm {
    protected boolean enableTdComparing = true;

    public BaseAlgorithm(Model model) {
        super(model);
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
        for (Node n : oneDeepNodes) {
            lb += getSolution(n);
        }
        node.setLowerBound(lb);
        return lb;
    }

    @Override
    public int upperBound(Node node) {
        int ub = 0;
        if (node.isExistUb()) {
            return node.getUpperBound();
        }
        int k = node.getLength();
        if (k == model.getN()) {
            node.setUpperBoundSolution(node);
            ub = getSolution(node);
            node.setUpperBound(ub);
            return ub;
        } else if(k == 0){
            return Integer.MAX_VALUE;
        }
        int j = 0;
        Node tmpNode = new Node(node);
        while (j != model.getN()) {
            int firstStation = tmpNode.getVItem(0);
            int time = model.getT(0, firstStation);
            for (int i = 1; i < tmpNode.getLength(); i++) {
                int sourceStationNumber = tmpNode.getVItem(i - 1);
                int distStationNumber = tmpNode.getVItem(i);
                time += model.getT(sourceStationNumber, distStationNumber);
            }
            List<Integer> negVertex = negativeVertexForNode(tmpNode);
            Map<Integer, Integer> mapResult = new HashMap<>();
            Map<Integer, Integer> mapResultTimes = new HashMap<>();
            for (Integer curStation : negVertex) {
                int res = -1;
                int lastStation = tmpNode.getVItem(tmpNode.getLength() - 1);
                int r = time + model.getT(lastStation, curStation);
                int td = model.getTd(curStation);
                if (r <= td) {
                    res = td - r;
                    mapResultTimes.put(curStation, model.getT(lastStation, curStation));
                } else {
                    res = Integer.MAX_VALUE;
                    mapResultTimes.put(curStation, Integer.MAX_VALUE);
                }
                mapResult.put(curStation, res);
            }
            int num = 0;
            for (Integer item : mapResult.values()) {
                if (item.equals(Integer.MAX_VALUE)) {
                    num++;
                }
            }

            if (num == negVertex.size()) {
                ub = num + getSolution(tmpNode);
                Node tNode = new Node(tmpNode, negativeVertexForNode(tmpNode));
                assert ub == getSolution(tNode);
                node.setUpperBoundSolution(tNode);
                node.setUpperBound(ub);
                return ub;
            }
            int numMinStation;
            if(enableTdComparing) {
                numMinStation = getNumMinStation(mapResult);
            } else {
                numMinStation = getNumMinStation(mapResultTimes);
            }
            tmpNode.addVItem(numMinStation);
            j = tmpNode.getLength();
        }
        ub = getSolution(tmpNode);
        node.setUpperBoundSolution(tmpNode);
        node.setUpperBound(ub);
        return ub;
    }

    protected int getNumMinStation(Map<Integer, Integer> integerMap) {
        int min = Integer.MAX_VALUE;
        int numMinStation = -1;
        for (Map.Entry<Integer, Integer> entry : integerMap.entrySet()) {
            if (entry.getValue() < min) {
                min = entry.getValue();
                numMinStation = entry.getKey();
            }
        }
        return numMinStation;
    }

    @Override
    public Node branching() {
        if (nodeList.isEmpty()) throw new UnsupportedOperationException();
        Node res = nodeList.get(0);
        int resN = lowerBound(res);
        for (Node n : nodeList) {
            if (lowerBound(n) <= resN) {
                res = n;
                resN = lowerBound(res);
            }
        }
        /*for(Node n: nodeList) {
            if (n.getLength() < res.getLength()) {
                res = n;
            }
        }*/
        return res;
    }
}
