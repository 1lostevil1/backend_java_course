package edu.project4;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Drawer extends JFrame {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BufferedImage image;

    public Drawer(int width, int height) {
        super("Pixel Coloring");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    private void drawPixelInPoint(int x, int y, int color) {
        image.setRGB(x, y, color);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    public void saveToFile() {
        File outputFile = new File(UUID.randomUUID() + ".png");
        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public void drawFlame(Pixel[][] image) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                drawPixelInPoint(x, y, image[x][y].getColor().getRGB());
                repaint();
            }
        }
    }
}
