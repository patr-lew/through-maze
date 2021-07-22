package org.example;

import org.example.maze.Maze;
import org.example.maze.MazeNode;
import org.example.maze.PathPainter;
import org.example.maze.RedToBluePathPainter;
import org.example.pathalgorithm.FirstLeft;
import org.example.pathalgorithm.PathFinder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

public class Main {
    static String filePath = "src/resources/examples/perfect2k.png";
    static File file = new File(filePath);

    public static void main(String[] args) throws Exception {

        BufferedImage mazeImg = ImageIO.read(file);
        mazeImg = switchFromBwToColor(mazeImg);
        System.out.println("loading maze from " + filePath +
                "\nMaze size: " + mazeImg.getWidth() + " x " + mazeImg.getHeight() +
                "\nTotal pixels = " + (mazeImg.getWidth() * mazeImg.getHeight()));


        System.out.println("\n--- MAPPING MAZE ---");
        long start = System.currentTimeMillis();
        Maze maze = new Maze(mazeImg);
        maze.createNodes();
        long stop = System.currentTimeMillis();
        System.out.println("finished.\n" +
                "\tNodes created: " + maze.getCount() +
                "\n\tElapsed time: " + (stop - start) + "ms");

        PathFinder finder = new FirstLeft(maze);

        System.out.println("\n--- CREATING PATH ---");
        start = System.currentTimeMillis();
        LinkedList<MazeNode> path = finder.solve(); // goLeft(maze);
        stop = System.currentTimeMillis();
        System.out.println("finished.\n" +
                "\tPath length: " + path.size() +
                "\n\tElapsed time: " + (stop - start) + "ms");

        PathPainter painter = new RedToBluePathPainter(file, mazeImg, path);

        System.out.println("\n--- PAINTING PATH ---");
        start = System.currentTimeMillis();
        painter.paintPath();
        stop = System.currentTimeMillis();
        System.out.println("finished.\n" +
                "\tElapsed time: " + (stop - start) + "ms");

    }

    private static BufferedImage switchFromBwToColor(BufferedImage img) {
        BufferedImage mazeImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = mazeImg.createGraphics();
        g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        g.dispose();

        return mazeImg;
    }


}

