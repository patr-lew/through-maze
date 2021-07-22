package org.example.pathalgorithm;

import org.example.maze.MazeNode;

import java.util.LinkedList;

public interface PathFinder {
    LinkedList<MazeNode> solve();
}
