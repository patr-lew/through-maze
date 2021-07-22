package org.example;

import org.example.pathalgorithm.FirstLeft;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

public class Main {
    static String filePath = "src/resources/examples/perfect2k.png";
    static File file = new File(filePath);

    public static void main(String[] args) throws Exception {

        BufferedImage mazeImg = readAndSwitchToColor(file);

        System.out.println("\n--- MAPPING MAZE ---");
        long start = System.currentTimeMillis();
        Maze maze = new Maze(mazeImg);
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


        System.out.println("\n--- PAINTING PATH ---");
        start = System.currentTimeMillis();
        colorNodes(mazeImg, path);
        stop = System.currentTimeMillis();
        System.out.println("finished.\n" +
                "\tElapsed time: " + (stop - start) + "ms");

//        colorNodes(mazeImg, maze.allNodes);
    }

    private static BufferedImage readAndSwitchToColor(File file) throws IOException {
        BufferedImage img = ImageIO.read(file);
        System.out.println("loading maze from " + filePath +
                "\nMaze size: " + img.getWidth() + " x " + img.getHeight() +
                "\nTotal pixels = " + (img.getWidth() * img.getHeight()));


        BufferedImage mazeImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = mazeImg.createGraphics();
        g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        g.dispose();

        return mazeImg;
    }


    private static void colorNodes(BufferedImage mazeImg, LinkedList<MazeNode> path) {
        if (path.size() == 0) {
            return;
        }

        Color color = new Color(255, 0, 0);

        double colorStep = 255.0 / path.size();
        double changeColorFlag = 0;

        MazeNode previous = path.get(0);
        for (int i = 1; i < path.size(); i++) {
            MazeNode current = path.get(i);
            int xOffset = current.getPositionX() - previous.getPositionX();
            int yOffset = current.getPositionY() - previous.getPositionY();

            changeColorFlag += colorStep;

            if (changeColorFlag >= 1) {
                color = changeColorLessRedMoreBlue(color, changeColorFlag);
                changeColorFlag = 0;
            }

            //color current node
            mazeImg.setRGB(previous.getPositionX(), previous.getPositionY(), color.getRGB());

            if (xOffset != 0) {
                while (xOffset != 0) {
//                    System.out.printf("being colored: %d : %d\n", previous.getPositionX() + xOffset, previous.getPositionY());
                    mazeImg.setRGB(previous.getPositionX() + xOffset, previous.getPositionY(), color.getRGB());

                    xOffset = (xOffset > 0) ? xOffset - 1 : xOffset + 1;
                }
            }

            if (yOffset != 0) {
                while (yOffset != 0) {
//                    System.out.printf("being colored: %d : %d\n", previous.getPositionX(), previous.getPositionY() + yOffset);
                    mazeImg.setRGB(previous.getPositionX(), previous.getPositionY() + yOffset, color.getRGB());

                    yOffset = (yOffset > 0) ? yOffset - 1 : yOffset + 1;
                }
            }

            previous = current;
        }

        try {
            ImageIO.write(mazeImg, "png", file);
        } catch (IOException e) {
            System.out.println("didn't work" + e.getMessage());
        }

    }

    private static Color changeColorLessRedMoreBlue(Color color, double changeColorFlag) {
        int red = color.getRed();
        red -= changeColorFlag;
        if (red < 0) red = 0;

        int blue = color.getBlue();
        blue += changeColorFlag;
        if (blue > 255) blue = 255;

        color = new Color (red, 0, blue);
        return color;
    }

    // color all created nodes in the maze
    private static void colorNodes(BufferedImage mazeImg, Set<MazeNode> allNodes) {
        if (allNodes.size() == 0) {
            return;
        }

        for (MazeNode node : allNodes) {
            Color color = new Color(255, 0, 0);
            mazeImg.setRGB(node.getPositionX(), node.getPositionY(), color.getRGB());
        }

        try {
            ImageIO.write(mazeImg, "png", file);
        } catch (IOException e) {
            System.out.println("didn't work: " + e.getMessage());
        }

    }

}

