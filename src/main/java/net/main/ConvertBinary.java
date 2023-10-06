package net.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConvertBinary {

    public static void main(String[] args) {
        System.out.println(convert("images_bw/proof_ri_bw.png"));
    }

    public static String convert(String imagePath){
        try {
            // upload image
            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);

            // size image
            int width = image.getWidth();
            int height = image.getHeight();

            // string to save the binary
            StringBuilder binary = new StringBuilder();

            // iterating the image in all its pixels
            for (int y = 0; y < width; y++) {
                for (int x = 0; x < height; x++) {
                    // getting color
                    Color colorPixel = new Color(image.getRGB(x, y));

                    // checks if the pixel is black.
                    if (colorPixel.getRed() == 0) {
                        binary.append("1");
                    }
                    // pixel white.
                    binary.append("0");

                }
            }
            return binary.toString();

        }catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }
}
