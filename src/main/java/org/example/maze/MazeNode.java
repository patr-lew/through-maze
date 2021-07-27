package org.example.maze;

import java.util.HashMap;
import java.util.LinkedList;

public class MazeNode {

    private int positionX;
    private int positionY;
    private MazeNode up;
    private MazeNode down;
    private MazeNode left;
    private MazeNode right;
    private HashMap<MazeNode, Integer> adjacentNodes = new HashMap(4);
    private int distance = Integer.MAX_VALUE;
    private LinkedList<MazeNode> shortestPath = new LinkedList<>();


    public MazeNode(int positionY, int positionX) {
        this.positionY = positionY;
        this.positionX = positionX;
    }

    // setters
    public void setUp(MazeNode up) {
        this.up = up;
        adjacentNodes.put(up, setDistance(up));
    }

    public void setDown(MazeNode down) {
        this.down = down;
        adjacentNodes.put(down, setDistance(down));
    }

    public void setLeft(MazeNode left) {
        this.left = left;
        adjacentNodes.put(left, setDistance(left));
    }

    public void setRight(MazeNode right) {
        this.right = right;
        adjacentNodes.put(right, setDistance(right));
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setShortestPath(LinkedList<MazeNode> shortestPath) {
        this.shortestPath = shortestPath;
    }

    // getters

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public MazeNode getUp() {
        return up;
    }

    public MazeNode getDown() {
        return down;
    }

    public MazeNode getLeft() {
        return left;
    }

    public MazeNode getRight() {
        return right;
    }

    public int getDistance() {
        return distance;
    }

    public HashMap<MazeNode, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public LinkedList<MazeNode> getShortestPath() {
        return this.shortestPath;
    }

    @Override
    public String toString() {
        return "MazeNode{" +
                "X=" + positionX +
                ", Y=" + positionY +
                '}';
    }

    private int setDistance(MazeNode neighbour) {
        return Math.abs(this.positionX - neighbour.positionX) + Math.abs(this.positionY - neighbour.positionY);
    }
}
