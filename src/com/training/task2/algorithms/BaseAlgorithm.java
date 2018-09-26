package com.training.task2.algorithms;

import com.training.task2.structures.Model;
import com.training.task2.structures.Node;
import com.training.task2.structures.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseAlgorithm implements IAlgorithm {
    private List<Integer> notDelivered;
    private List<Node> nodeList;
    private Model model;
    private int iter = 0;

    public BaseAlgorithm(Model model) {
        this.model = model;
        notDelivered = new ArrayList<>();
        for(int i = 0; i < model.getN(); i++){
            notDelivered.add(i + 1);
        }

        nodeList = new ArrayList<>();
        nodeList.add(new Node());
    }

    @Override
    public Solution solve() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(Thread.currentThread().isInterrupted()){
                        break;
                    }
                    System.out.println(iter);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        while (true) {
            iter++;
            Node root = branching();
            addUnionToList(nodeList, root);
            int currentUpperMax = foundMaxUpperBound(nodeList);
            removingNodes(nodeList, currentUpperMax);
            if (nodeList.isEmpty()) {
                return new Solution(root, currentUpperMax, iter);
            }
            if(nodeList.size() == 1){
                Node oneNode = nodeList.get(0);
                int uBound = upperBound(oneNode);
                int lBound = lowerBound(oneNode);
                if(uBound == lBound){
                    return new Solution(oneNode, uBound, iter);
                }
            }
        }
    }

    private void addUnionToList(List<Node> nodes, Node root){
        nodes.remove(root);
        List<Integer> negativeVertex = negativeVertexForNode(root);
        for(Integer num : negativeVertex){
            nodes.add(new Node(root, num));
        }
    }

    private void removingNodes(List<Node> nodes, int upperBound){
        for(int i = 0; i < nodes.size(); i++){
            Node curNode = nodes.get(i);
            if(lowerBound(curNode) >= upperBound){
                nodes.remove(curNode);
            }
        }
    }
    private List<Integer> negativeVertexForNode(Node node){
        List<Integer> negativeVertex = new ArrayList<>();
        for(int i = 1; i <= model.getN(); i++){
            if(!node.isExist(i)){
                negativeVertex.add(i);
            }
        }
        return negativeVertex;
    }
    @Override
    public int lowerBound(Node node) {
        int lb;
        if(node.isExistLb()) {
            lb = node.getLowerBound();
        } else {
            lb = getSolution(node);
            node.setLowerBound(lb);
        }
        return lb;
    }

    @Override
    public int upperBound(Node node) {
        int ub;
        if(node.isExistUb()){
            return node.getUpperBound();
        }
        int k = node.getLength();
        if(k == model.getN()){
            return getSolution(node);
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
            for (Integer curStation : negVertex) {
                int res = -1;
                int lastStation = tmpNode.getVItem(tmpNode.getLength() - 1);
                int r = time + model.getT(lastStation, curStation);
                int td = model.getTd(curStation);
                if (r <= td) {
                    res = td - r;
                } else {
                    res = Integer.MAX_VALUE - 1;
                }
                mapResult.put(curStation, res);
            }

            int min = Integer.MAX_VALUE;
            int numMinStation = -1;
            for (Map.Entry<Integer, Integer> entry : mapResult.entrySet()) {
                if (entry.getValue() < min) {
                    min = entry.getValue();
                    numMinStation = entry.getKey();
                }
            }
            tmpNode.addVItem(numMinStation);
            j = tmpNode.getLength();
        }
        ub = getSolution(tmpNode);
        node.setUpperBound(ub);
        return ub;
    }

    private int getSolution(Node node){
        int numViolations = 0;
        Map<Integer, Integer> timesForStations = new HashMap<>();
        for(int i = 0; i < node.getLength(); i++){
            int firstStation = node.getVItem(0);
            int time = model.getT(0, firstStation);
            for (int j = 1; j < i + 1; j++) {
                int sourceStationNumber = node.getVItem(j - 1);
                int distStationNumber = node.getVItem(j);
                time += model.getT(sourceStationNumber, distStationNumber);
            }
            timesForStations.put(node.getVItem(i), time);
        }

        for(Map.Entry<Integer, Integer> entry: timesForStations.entrySet()){
            int numStation = entry.getKey();
            int iD = model.getTd(numStation);
            int time = entry.getValue();
            if(iD < time){
                numViolations++;
            }
        }
        return numViolations;
    }

    public int foundMaxUpperBound(List<Node> nodes){
        if(nodes.isEmpty()) throw new UnsupportedOperationException();
        int max = -1;
        for(Node n : nodes){
            int currentUBound = upperBound(n);
            if(currentUBound > max) {
                max = currentUBound;
            }
        }
        return max;
    }
    public Node branching(){
        if(nodeList.isEmpty()) throw new UnsupportedOperationException();
        Node res = nodeList.get(0);
        int resN = lowerBound(res);
        for(Node n : nodeList){
            if(lowerBound(n) < resN){
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
