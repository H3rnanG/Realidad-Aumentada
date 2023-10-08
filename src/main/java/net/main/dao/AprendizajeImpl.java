package net.main.dao;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.main.modelo.Aprendizaje;
import net.main.repositorio.AprendizajeRepositorio;
import net.main.servicio.AprendizajeServicio;

@Service
public class AprendizajeImpl implements AprendizajeServicio {

	private Color array[][] = new Color[50][50];
	private final int width = 50;
	private final int height = 50;

	@Autowired
	private AprendizajeRepositorio aprendizajeRepositorio;

	@Override
	public void guardarLetra(Aprendizaje letraAprendizaje) {
		// TODO Auto-generated method stub
		aprendizajeRepositorio.save(letraAprendizaje);
	}

	@Override
	public BufferedImage procesarImagen(MultipartFile file) {
		try {

			// upload image
			BufferedImage image = ImageIO.read(file.getInputStream());

			// Convert to black and white
			BufferedImage blacAndWhite = convertToBlackAndWhite(image);

			// resize image to 50x50
			int newWidth = 50;
			int newHeight = 50;
			BufferedImage resizedImage = resizeImage(blacAndWhite, newWidth, newHeight);

			// extraxting image name

			String name = file.getOriginalFilename();

			// file
			String box = "./images_r/";
			// save resize image
			File exitFile = new File(box + name + "_ri.png");
			ImageIO.write(resizedImage, "png", exitFile);
			return resizedImage;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		

	}

	private BufferedImage convertToBlackAndWhite(BufferedImage originalImage) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color pixelColor = new Color(originalImage.getRGB(x, y));
				int gray = (int) (0.299 * pixelColor.getRed() + 0.587 * pixelColor.getGreen()
						+ 0.114 * pixelColor.getBlue());
				int newPixel = new Color(gray, gray, gray).getRGB();
				grayscaleImage.setRGB(x, y, newPixel);
			}
		}

		return grayscaleImage;
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
		Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		BufferedImage bufferedResizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		bufferedResizedImage.getGraphics().drawImage(resizedImage, 0, 0, null);
		return bufferedResizedImage;
	}
	
	

}
