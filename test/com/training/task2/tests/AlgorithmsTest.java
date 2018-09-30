package com.training.task2.tests;

import com.training.task2.Main;
import com.training.task2.algorithms.impl.BaseAlgorithm;
import com.training.task2.algorithms.impl.UAlgorithm;
import com.training.task2.load.Parser;
import com.training.task2.structures.Model;
import com.training.task2.structures.Solution;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class AlgorithmsTest {
    private List<Model> modelList;
    private int limitationN = 20;

    @Before
    public void setUp() {
        modelList = Parser.parseFolderWithFiles(Main.PATH_TO_FOLDER);
    }

    @Test
    public void testCorrectSolved() {
        for (Model model : modelList) {
            if (model.getN() > limitationN) continue;
            Solution baseSolution = new BaseAlgorithm(model).solve();
            Solution uSolution = new UAlgorithm(model).solve();
            assertEquals(baseSolution.getOptimalNumViolations(), uSolution.getOptimalNumViolations());
        }
        System.out.println("test passed");
    }
}
