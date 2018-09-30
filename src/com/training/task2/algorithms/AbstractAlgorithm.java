package com.training.task2.algorithms;

import com.training.task2.structures.Model;
import com.training.task2.structures.Node;
import com.training.task2.structures.Solution;
import javafx.util.Pair;

import java.util.*;

public abstract class AbstractAlgorithm implements IAlgorithm {
    protected List<Node> nodeList;
    protected Model model;
    private Node minNode;
    private int iter = 0;

    public AbstractAlgorithm(Model model) {
        this.model = model;
        nodeList = new ArrayList<>();
        Node rootNode = new Node();
        rootNode.setUpperBound(Integer.MAX_VALUE);
        rootNode.setLowerBound(Integer.MAX_VALUE - 1);
        nodeList.add(rootNode);
        minNode = new Node();
        minNode.setUpperBound(Integer.MAX_VALUE);
    }

    @Override
    public Solution solve() {
        while (true) {
            Node root = branching();
            addUnionToList(nodeList, root);
            Pair<Integer, Node> currentUpperMin = foundMinUpperBound(nodeList, true);
            int min = currentUpperMin.getKey();
            removingNodes(nodeList, min);
            if (nodeList.isEmpty()) {
                return new Solution(minNode.getUpperBoundSolution(), minNode.getUpperBound(), iter);
            }
            if (nodeList.size() == 1) {
                Node oneNode = nodeList.get(0);
                int uBound = upperBound(oneNode);
                int lBound = lowerBound(oneNode);
                if (uBound == lBound) {
                    if(minNode.getUpperBound() != minNode.getLowerBound() || minNode.getLowerBound() >= uBound){
                       minNode = oneNode;
                    }
                    return new Solution(minNode.getUpperBoundSolution(), minNode.getUpperBound(), iter);
                }
            }
        }
    }

    private void addUnionToList(List<Node> nodes, Node root) {
        nodes.remove(root);
        List<Integer> negativeVertex = negativeVertexForNode(root);
        for (Integer num : negativeVertex) {
            nodes.add(new Node(root, num));
            iter++;
        }
    }

    private void removingNodes(List<Node> nodes, int upperBound) {
        List<Node> forDelete = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node curNode = nodes.get(i);
            if (lowerBound(curNode) >= upperBound) {
                forDelete.add(curNode);
            }
        }
        nodes.removeAll(forDelete);
    }

    protected List<Integer> negativeVertexForNode(Node node) {
        List<Integer> negativeVertex = new ArrayList<>();
        for (int i = 1; i <= model.getN(); i++) {
            if (!node.isExist(i)) {
                negativeVertex.add(i);
            }
        }
        return negativeVertex;
    }

    @Override
    abstract public int lowerBound(Node node);

    @Override
    abstract public int upperBound(Node node);

    @Override
    abstract public Node branching();

    protected int getSolution(Node node) {
        int numViolations = 0;
        Map<Integer, Integer> timesForStations = new HashMap<>();
        for (int i = 0; i < node.getLength(); i++) {
            int firstStation = node.getVItem(0);
            int time = model.getT(0, firstStation);
            for (int j = 1; j < i + 1; j++) {
                int sourceStationNumber = node.getVItem(j - 1);
                int distStationNumber = node.getVItem(j);
                time += model.getT(sourceStationNumber, distStationNumber);
            }
            timesForStations.put(node.getVItem(i), time);
        }

        for (Map.Entry<Integer, Integer> entry : timesForStations.entrySet()) {
            int numStation = entry.getKey();
            int iD = model.getTd(numStation);
            int time = entry.getValue();
            if (iD < time) {
                numViolations++;
            }
        }
        return numViolations;
    }

    protected Pair<Integer, Node> foundMinUpperBound(List<Node> nodes, boolean saveMin) {
        if (nodes.isEmpty()) throw new UnsupportedOperationException();
        int min = Integer.MAX_VALUE;
        Node sol = null;
        for (Node n : nodes) {
            int currentUBound = upperBound(n);
            if (currentUBound <= min) {
                min = currentUBound;
                sol = n;
            }
        }
        if(saveMin && minNode.getUpperBound() > min){
            minNode = sol;
        }
        return new Pair<>(min, sol);
    }


}
