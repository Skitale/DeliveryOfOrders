package com.training.task2.tests;

import com.training.task2.Main;
import com.training.task2.algorithms.impl.BaseAlgorithm;
import com.training.task2.algorithms.impl.UAlgorithm;
import com.training.task2.load.Parser;
import com.training.task2.structures.Model;
import com.training.task2.structures.Solution;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AlgorithmsTest {
    private List<Model> modelList;
    private Map<String, Integer> answers;
    private int limitationN = 20;

    @Before
    public void setUp() {
        modelList = Parser.parseFolderWithFiles(Main.PATH_TO_FOLDER);
        answers = new HashMap<>();
        answers.put("task_2_01_n3.txt", 0);
        answers.put("task_2_02_n3.txt", 2);
        answers.put("task_2_03_n10.txt", 4);
        answers.put("task_2_04_n10.txt", 3);
        answers.put("task_2_05_n10.txt", 4);
        answers.put("task_2_06_n15.txt", 2);
        answers.put("task_2_07_n15.txt", 2);
        answers.put("task_2_08_n50.txt", 3);
        answers.put("task_2_09_n50.txt", 1);
        answers.put("task_2_10_n50.txt", 2);
    }

    @Test
    public void testCorrectSolved() {
        for (Model model : modelList) {
            if (model.getN() > limitationN) continue;
            Solution baseSolution = new BaseAlgorithm(model).solve();
            Solution uSolution = new UAlgorithm(model).solve();
            if (answers.get(model.getNameModel()) == null)
                throw new UnsupportedOperationException("The list doesn't has the answer for this model. Please, enter it.");
            int answerForTask = answers.get(model.getNameModel());
            assertEquals(baseSolution.getOptimalNumViolations(), answerForTask);
            assertEquals(uSolution.getOptimalNumViolations(), answerForTask);
        }
        System.out.println("test passed");
    }
}
