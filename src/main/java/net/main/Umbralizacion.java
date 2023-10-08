package net.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Umbralizacion {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// Cargar la imagen
        BufferedImage image = ImageIO.read(new File("images/letra.png.jpg_ri.png"));
        int width = image.getWidth();
        int height = image.getHeight();

        // Tamaño del bloque y valor de C (ajustables)
        int blockSize = 10;
        int C = 0;

        // Crear una imagen para el resultado
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Iterar a través de la imagen por bloques
        for (int y = 0; y < height; y += blockSize) {
            for (int x = 0; x < width; x += blockSize) {
                // Calcular umbral local para el bloque actual
                int sumGray = 0;
                int pixelCount = 0;
                for (int i = y; i < y + blockSize && i < height; i++) {
                    for (int j = x; j < x + blockSize && j < width; j++) {
                        Color pixelColor = new Color(image.getRGB(j, i));
                        int gray = (int) (0.299 * pixelColor.getRed() + 0.587 * pixelColor.getGreen() + 0.114 * pixelColor.getBlue());
                        sumGray += gray;
                        pixelCount++;
                    }
                }
                int localThreshold = sumGray / pixelCount + C;

                // Aplicar umbralización local al bloque actual
                for (int i = y; i < y + blockSize && i < height; i++) {
                    for (int j = x; j < x + blockSize && j < width; j++) {
                        Color pixelColor = new Color(image.getRGB(j, i));
                        int gray = (int) (0.299 * pixelColor.getRed() + 0.587 * pixelColor.getGreen() + 0.114 * pixelColor.getBlue());
                        if (gray > localThreshold) {
                            resultImage.setRGB(j, i, Color.WHITE.getRGB());
                        } else {
                            resultImage.setRGB(j, i, Color.BLACK.getRGB());
                        }
                    }
                }
            }
        }

        // Guardar la imagen binarizada
        ImageIO.write(resultImage, "png", new File("images_r/imagen_binarizada.png"));
    }

}
