package com.training.task2.algorithms;

import com.training.task2.structures.Node;
import com.training.task2.structures.Solution;

interface IAlgorithm {
    Solution solve();

    int lowerBound(Node node);

    int upperBound(Node node);

    Node branching();
}
