package net.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConvertBlackWhite {


    public static void main(String[] args) {
        System.out.println(convert("images_r/proof_ri.png"));
    }

    public static String convert(String imagePath){
        try {

            // upload image
            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);

            // size image
            int width = image.getWidth();
            int height = image.getHeight();

            // iterating the image in all its pixels

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    Color colorPixel = new Color(image.getRGB(x,y));
                    int averageGrey = (colorPixel.getRed()+colorPixel.getGreen()+colorPixel.getBlue())/3;
                    Color colorBlackWhite = new Color(averageGrey, averageGrey,averageGrey);
                    image.setRGB(x,y,colorBlackWhite.getRGB());
                }
            }

            // extraxting image name
            String rename  = imagePath;
            int indexBar = rename.lastIndexOf("/");
            String nameExtension = rename.substring(indexBar + 1);
            int indexPoint = nameExtension.lastIndexOf(".");
            String name = nameExtension.substring(0, indexPoint);

            // file
            String box = "./images_bw/";
            // save resize image
            File exitFile = new File(box+name+"_bw.png");
            ImageIO.write(image, "png", exitFile);

            return exitFile.toString();


        }catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }
}
