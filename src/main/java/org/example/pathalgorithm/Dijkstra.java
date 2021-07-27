package org.example.pathalgorithm;

import org.example.maze.Maze;
import org.example.maze.MazeNode;
import org.w3c.dom.Node;

import java.util.*;

public class Dijkstra extends PathFinder {
    private MazeNode start = maze.getStart();
    private MazeNode end = maze.getEnd();
    private Set<MazeNode> unsettledNodes = new HashSet<>();
    private Set<MazeNode> settledNodes = new LinkedHashSet<>();

    public Dijkstra(Maze maze) {
        super(maze);
    }

    @Override
    public LinkedList<MazeNode> solve() {

        unsettledNodes.add(start);

        while (unsettledNodes.size() != 0) {
            MazeNode currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);

            for (Map.Entry<MazeNode, Integer> adjacentPair :
                    currentNode.getAdjacentNodes().entrySet()) {
                MazeNode adjacentNode = adjacentPair.getKey();
                int distance = adjacentPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, distance, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }

        LinkedList<MazeNode> shortestPath = end.getShortestPath();
        shortestPath.add(end);
        return shortestPath;
    }

    private MazeNode getLowestDistanceNode(Set<MazeNode> unsettledNodes) {
        MazeNode lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (MazeNode node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(MazeNode evaluationNode, int distance, MazeNode sourceNode) {
        int sourceDistance = sourceNode.getDistance();
        if (sourceDistance + distance < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + distance);
            LinkedList<MazeNode> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}
