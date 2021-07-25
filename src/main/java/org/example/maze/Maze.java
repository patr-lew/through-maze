package org.example.maze;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Maze {
    public Set<MazeNode> allNodes = new HashSet<>();
    private final BufferedImage maze;
    private final boolean[][] mazeMap;
    private MazeNode start;
    private MazeNode end;
    private int count;

    public Maze(BufferedImage maze) {
        this.maze = maze;
        this.mazeMap = createMap(maze);
    }

    public MazeNode getStart() {
        return start;
    }

    public MazeNode getEnd() {
        return end;
    }

    public int getCount() {
        return count;
    }

    private boolean[][] createMap(BufferedImage maze) {
        int width = maze.getWidth();
        int height = maze.getHeight();
        boolean[][] mazeMap = new boolean[height][];

        for (int x = 0; x < height; x++) {
            mazeMap[x] = new boolean[width];
            for (int y = 0; y < width; y++) {
                int rgb = maze.getRGB(y, x);
                mazeMap[x][y] = rgb > -500;
            }
        }

        return mazeMap;
    }

    public void createNodes() {
        int width = maze.getWidth();
        int height = maze.getHeight();

        // Top row buffer
        MazeNode[] topNodes = new MazeNode[width];
        int count = 0;

        // Start row
        for (int x = 0; x < width; x++) {
            if (mazeMap[0][x]) {
                this.start = new MazeNode(0, x);
                start.setDistance(0);
                allNodes.add(start);
                //System.out.printf("\tStart node at %d, %d\n", 0, x);
                topNodes[x] = start;
                count++;
                break;
            }
        }

        for (int y = 1; y < height; y++) {
            //System.out.println("\trow " + y);  // Uncomment in order to keep track of row progress

            // Initialize previous, current and next values
            boolean previous = false;
            boolean current = false;
            boolean next = mazeMap[y][0];

            MazeNode leftNode = null;

            for (int x = 0; x < width; x++) {
                previous = current;
                current = next;
                next = mazeMap[y][Math.min((x + 1), width - 1)];

                MazeNode n = null;

                if (!current) { // Wall - no action
                    continue;
                }

                if (previous) {
                    if (next) {
                        // PATH PATH PATH - create only if PATH above/below
                        if (mazeMap[y - 1][x] || mazeMap[Math.min(y + 1, height - 1)][x]) {
                            //System.out.printf("\tNew node at %d, %d\n", y, x);
                            n = new MazeNode(y, x);
                            allNodes.add(n);
                            leftNode.setRight(n);
                            n.setLeft(leftNode);
                            leftNode = n;
                        }
                    } else {
                        // PATH PATH WALL - path at the end of corridor
                        n = new MazeNode(y, x);
                        allNodes.add(n);
                        //System.out.printf("\tNew node at %d, %d\n", y, x);
                        leftNode.setRight(n);
                        n.setLeft(leftNode);
                        leftNode = null;
                    }
                } else {
                    if (next) {
                        // WALL PATH PATH - path at the start of corridor
                        n = new MazeNode(y, x);
                        allNodes.add(n);
                        //System.out.printf("\tNew node at %d, %d\n", y, x);
                        leftNode = n;
                    } else {
                        // WALL PATH WALL - node only if dead end
                        if (!mazeMap[y - 1][x] || !mazeMap[Math.min(y + 1, height - 1)][x]) {
                            n = new MazeNode(y, x);
                            allNodes.add(n);
                            //System.out.printf("\tNew node at %d, %d\n", y, x);
                        }
                    }
                }

                // if node isn't none, we can assume we can connect N-S somewhere
                if (n != null) {
                    // clear above, connect to waiting top node
                    if (mazeMap[y - 1][x]) {
                        MazeNode top = topNodes[x];
                        if (top != null) {
                            top.setDown(n);
                            n.setUp(top);
                        }
                    }

                    // if clear below, put this new node in the top row for the next connection
                    if (mazeMap[Math.min(y + 1, height - 1)][x]) {
                        topNodes[x] = n;
                    } else {
                        topNodes[x] = null;
                    }

                    count++;
                }

            }
        }

        // End row
        for (int x = 0; x < width; x++) {
            if (mazeMap[height - 1][x]) {
                this.end = new MazeNode(height - 1, x);
                allNodes.add(end);
                //System.out.printf("\tNew node at %d, %d\n", height - 1, x);
                MazeNode t = topNodes[x];
                t.setDown(end);
                end.setUp(t);
                count++;
                break;
            }
        }
        this.count = count;
    }
}

