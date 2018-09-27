package com.training.task2;

import com.training.task2.algorithms.AbstractAlgorithm;
import com.training.task2.algorithms.impl.BaseAlgorithm;
import com.training.task2.algorithms.impl.UAlgorithm;
import com.training.task2.load.Parser;
import com.training.task2.structures.Model;
import com.training.task2.structures.Solution;

import java.util.List;

public class Main {
    public static final String PATH_TO_FOLDER = "./resources/";

    public static void main(String[] args) {
        List<Model> models = Parser.parseFolderWithFiles(PATH_TO_FOLDER);
        for (Model m : models) {
            System.out.println("----- Model " + m.getNameModel() + " -----");
            AbstractAlgorithm ba = new UAlgorithm(m);
            Solution res = ba.solve();
            Solution baseRes = new BaseAlgorithm(m).solve();
            System.out.println("Base RES = " + baseRes);
            System.out.println("RES = " + res);
        }
    }
}
