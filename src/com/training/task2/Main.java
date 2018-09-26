package com.training.task2;

import com.training.task2.algorithms.BaseAlgorithm;
import com.training.task2.load.Parser;
import com.training.task2.structures.Model;
import com.training.task2.structures.Node;
import com.training.task2.structures.Solution;

import java.io.File;
import java.util.List;

public class Main {
    public static final String PATH_TO_FOLDER = "./resources/";
    public static void main(String[] args) {
        List<Model> models = Parser.parseFolderWithFiles(PATH_TO_FOLDER);



       /* Model m = new Model(2);
        m.setTd(2, 3);
        m.setTd(1, 2);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                m.setT(i, j, 1 + (int) (Math.random() * 10));
            }
        }*/

        BaseAlgorithm ba = new BaseAlgorithm(models.get(1));
        Solution res = ba.solve();
        System.out.println("RES = " + res);
    }
}
