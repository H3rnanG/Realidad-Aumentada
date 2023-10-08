package net.main.controller;

import java.awt.Color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.main.servicio.AprendizajeServicio;
import net.main.servicio.LetraServicio;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("api/")
public class LetraController {

	@Autowired
	private LetraServicio LetraServicio;
	@Autowired
	private AprendizajeServicio aprendizajeServicio;

	/*@PostMapping("analizar")
	public ResponseEntity<byte[]> analizarLetra(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		try {
			BufferedImage originalImage = ImageIO.read(file.getInputStream());
			int newWidth = 50;
			int newHeight = 50;

			// Redimensionar la imagen a 50x50 píxeles
			Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
			BufferedImage bufferedResizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			bufferedResizedImage.getGraphics().drawImage(resizedImage, 0, 0, null);

			// Convertir la imagen redimensionada a blanco y negro
			for (int y = 0; y < newHeight; y++) {
				for (int x = 0; x < newWidth; x++) {
					int pixel = bufferedResizedImage.getRGB(x, y);
					int red = (pixel >> 16) & 0xFF;
					int green = (pixel >> 8) & 0xFF;
					int blue = pixel & 0xFF;

					if (!(red == 0 && green == 0 && blue == 0) && !(red == 255 && green == 255 && blue == 255)) {
						bufferedResizedImage.setRGB(x, y, Color.WHITE.getRGB());
					}
				}
			}

			// Crear un flujo de salida para la imagen en formato PNG
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedResizedImage, "png", baos);
			byte[] resizedImageBytes = baos.toByteArray();

			String name = file.getOriginalFilename();
			// file
			String box = "./images/";

			File exitFile = new File(box + name + "_ri.png");
			ImageIO.write(bufferedResizedImage, "png", exitFile);

			// Devuelve la imagen redimensionada y en blanco y negro como respuesta binaria
			return ResponseEntity.ok().body(resizedImageBytes);

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}*/

	@PostMapping("analizar")
	public ResponseEntity<byte[]> ProcesarImagen(@RequestParam("file") MultipartFile file) {

		try {
			BufferedImage processedImage = aprendizajeServicio.procesarImagen(file);

			// Verifica si la imagen procesada no es nula
			if (processedImage != null) {
				// Crea un flujo de salida para la imagen en formato PNG
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(processedImage, "png", baos);
				byte[] imageBytes = baos.toByteArray();

				// Devuelve la imagen en formato PNG como arreglo de bytes
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.IMAGE_PNG);
				return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	@PostMapping("guardar")
	public void GuardarImagen(@RequestParam("file")MultipartFile file, @RequestParam("nombre") String nombre) {
		
	}
}
