package org.example.pathalgorithm;

import org.example.maze.Maze;
import org.example.maze.MazeNode;

import java.util.LinkedList;

public class FirstLeft implements PathFinder {
    private Maze maze;

    public FirstLeft(Maze maze) {
        this.maze = maze;
    }

    @Override
    public LinkedList<MazeNode> solve() {
        LinkedList<MazeNode> path = new LinkedList<>();
        MazeNode start = maze.getStart();
        MazeNode end = maze.getEnd();
        int heading = 2; // south
        int turn = 1; // left, -1 = right
        boolean deadEnd = false;

        MazeNode current = start;
        while (current != end && !deadEnd) {
            path.add(current);
            MazeNode[] neighbours = getNeighbours(current);

            if (neighbours[Math.floorMod((heading - turn), 4)] != null) {
                heading = Math.floorMod((heading - turn), 4);
                current = neighbours[heading];
                continue;
            }

            if (neighbours[heading] != null) {
                current = neighbours[heading];
                continue;
            }

            if (neighbours[(heading + turn) % 4] != null) {
                heading = (heading + turn) % 4;
                current = neighbours[heading];
                continue;
            }

            if (neighbours[(heading + 2) % 4] != null) {
                heading = (heading + 2) % 4;
                current = neighbours[heading];
                continue;
            }
            deadEnd = true;
        }

        if (!deadEnd) {
            path.add(end);
        }

        return path;
    }

    private static MazeNode[] getNeighbours(MazeNode node) {
        // N E S W - helpful reminder
        // 0 1 2 3
        return new MazeNode[]{node.getUp(), node.getRight(), node.getDown(), node.getLeft()};
    }
}


