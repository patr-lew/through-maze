package org.example.maze;

public class MazeNode {

    private int positionX;
    private int positionY;
    public MazeNode up;
    public MazeNode down;
    public MazeNode left;
    public MazeNode right;
    private int weight;
    private int distance;


    public MazeNode(int positionY, int positionX) {
        this.positionY = positionY;
        this.positionX = positionX;
    }

    // setters
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    // getters
    public int getWeight() {
        return weight;
    }

    public int getDistance() {
        return distance;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
