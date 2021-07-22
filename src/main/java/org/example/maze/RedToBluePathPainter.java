package org.example.maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class RedToBluePathPainter implements PathPainter {
    private File output;
    private BufferedImage mazeImg;
    private LinkedList<MazeNode> path;

    public RedToBluePathPainter(File output, BufferedImage mazeImg, LinkedList<MazeNode> path) {
        this.output = output;
        this.mazeImg = mazeImg;
        this.path = path;
    }

    @Override
    public void paintPath() {
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
            ImageIO.write(mazeImg, "png", output);
        } catch (IOException e) {
            System.out.println("didn't work" + e.getMessage());
        }

    }

    private Color changeColorLessRedMoreBlue(Color color, double changeColorFlag) {
        int red = color.getRed();
        red -= changeColorFlag;
        if (red < 0) red = 0;

        int blue = color.getBlue();
        blue += changeColorFlag;
        if (blue > 255) blue = 255;

        color = new Color (red, 0, blue);
        return color;
    }
}
