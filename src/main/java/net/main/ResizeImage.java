package net.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResizeImage {

    public ResizeImage(){}

    public static void main(String[] args) {

        System.out.println(resize("images/proof.png"));
    }

    public static String resize(String imagePath){
        try {

            // upload image
            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);

            // resize image to 50x50
            int newWidth = 50;
            int newHeight = 50;
            Image resizeImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            // creating the image with the new size
            BufferedImage finalImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            finalImage.getGraphics().drawImage(resizeImage, 0, 0, null);

            // extraxting image name
            String rename  = imagePath;
            int indexBar = rename.lastIndexOf("/");
            String nameExtension = rename.substring(indexBar + 1);
            int indexPoint = nameExtension.lastIndexOf(".");
            String name = nameExtension.substring(0, indexPoint);

            // file
            String box = "./images_r/";
            // save resize image
            File exitFile = new File(box+name+"_ri.png");
            ImageIO.write(finalImage, "png", exitFile);

            return exitFile.toString();




        }catch (IOException e){
            e.printStackTrace();
            return "";
        }


    }
}
