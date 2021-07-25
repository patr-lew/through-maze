package org.example.pathalgorithm;

import org.example.maze.Maze;
import org.example.maze.MazeNode;

import java.util.LinkedList;

public abstract class PathFinder {
    protected Maze maze;

    public PathFinder(Maze maze) {
        this.maze = maze;
    }

    public abstract LinkedList<MazeNode> solve();
}
