package org.example.maze;

import java.util.TreeSet;

public class MazeNode {

    private int positionX;
    private int positionY;
    private MazeNode up;
    private MazeNode down;
    private MazeNode left;
    private MazeNode right;
    private int distanceUp;
    private int distanceDown;
    private int distanceLeft;
    private int distanceRight;
    private int distance;


    public MazeNode(int positionY, int positionX) {
        this.positionY = positionY;
        this.positionX = positionX;
    }

    // setters


    public void setUp(MazeNode up) {
        this.distanceUp = setDistance(up);
        this.up = up;
    }

    public void setDown(MazeNode down) {
        this.distanceDown = setDistance(down);
        this.down = down;
    }

    public void setLeft(MazeNode left) {
        this.distanceLeft = setDistance(left);
        this.left = left;
    }

    public void setRight(MazeNode right) {
        this.distanceRight = setDistance(right);
        this.right = right;
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

    private int setDistance(MazeNode neighbour) {
        return Math.abs(this.positionX - neighbour.positionX) + Math.abs(this.positionY - neighbour.positionY);
    }
}
