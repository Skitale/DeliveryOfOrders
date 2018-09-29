package com.training.task2;

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
            Solution baseRes = new BaseAlgorithm(m).solve();
            System.out.println("Base RES = " + baseRes);
            Solution res = new UAlgorithm(m).solve();
            System.out.println("RES = " + res);
        }
    }
}
