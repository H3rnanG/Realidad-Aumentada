package net.main.dao;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Entity;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.main.modelo.Aprendizaje;
import net.main.repositorio.AprendizajeRepositorio;
import net.main.servicio.AprendizajeServicio;

@Service
public class AprendizajeImpl implements AprendizajeServicio {

	private final int width = 50;
	private final int height = 50;
//	private int[][] pixelMatrix;
//	private int[] arrayBinario;
	private final String directorio = "./letras/";

	@Autowired
	private AprendizajeRepositorio aprendizajeRepositorio;

	@Override
	public void guardarLetra(int[] arrayBinario, String nombre) {
		Aprendizaje aprendizaje = new Aprendizaje();
		try {
			
            byte[] arrayBinarioBytes = convertirArrayBinarioABytes(arrayBinario);

			
            aprendizaje.setNombre(nombre);
            aprendizaje.setAreaPintada(CalcularAreaPintada(arrayBinario));

            String pathArchivo = guardarArrayBinarioEnArchivo(arrayBinario, nombre);
            
            aprendizaje.setArrayBinario(arrayBinarioBytes);

            aprendizajeRepositorio.save(aprendizaje);
        } catch (Exception e) {
            // Maneja la excepción apropiadamente
            e.printStackTrace();
        }

	}
	
	@Override
	public Aprendizaje encontrarLetraSimilar(int[] arrayBinarioImagen, List<Aprendizaje> letrasEnRango) {
		Aprendizaje letraSalida = null;
        if (letrasEnRango.isEmpty()) {
            return null;
        }

        String letraSimilar = null;
        int menorDiferencia = Integer.MAX_VALUE;

        for (Aprendizaje letra : letrasEnRango) {
            int[] arrayBinarioBase = convertirBytesAArrayBinario(letra.getArrayBinario());

            int diferencia = calcularDiferencia(arrayBinarioImagen, arrayBinarioBase);

            if (diferencia < menorDiferencia) {
                menorDiferencia = diferencia;
                letraSalida = letra; // Esta es la letra más similar encontrada hasta ahora.
            }
        }

        return letraSalida;
	}
	
	private int calcularDiferencia(int[] arrayBinario1, int[] arrayBinario2) {
        if (arrayBinario1.length != arrayBinario2.length) {
            throw new IllegalArgumentException("Los arrays deben tener la misma longitud.");
        }

        int diferencia = 0;
        for (int i = 0; i < arrayBinario1.length; i++) {
            if (arrayBinario1[i] != arrayBinario2[i]) {
                diferencia++;
            }
        }

        return diferencia;
    }

    private int[] convertirBytesAArrayBinario(byte[] bytes) {
        int[] arrayBinario = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            arrayBinario[i] = bytes[i];
        }
        return arrayBinario;
    }
	
	
	
	private byte[] convertirArrayBinarioABytes(int[] arrayBinario) {
        byte[] bytes = new byte[arrayBinario.length];
        for (int i = 0; i < arrayBinario.length; i++) {
            bytes[i] = (byte) arrayBinario[i];
        }
        return bytes;
    }
	
	private List<Aprendizaje> obtenerLetrasEnRango(Long limiteInferior, Long limiteSuperior){
		
		return aprendizajeRepositorio.findByAreaPintadaBetween(limiteInferior, limiteSuperior);		
	}
	@Override
	public List<Aprendizaje> TraerImagenes(int[] matrix){
		
		double porcentajeVarianza = 0.10;
		long areaPintadaImagenEntrante = CalcularAreaPintada(matrix);
		long limiteInferior = areaPintadaImagenEntrante - (long) (areaPintadaImagenEntrante * porcentajeVarianza);
		long limiteSuperior = areaPintadaImagenEntrante + (long) (areaPintadaImagenEntrante * porcentajeVarianza);

	    List<Aprendizaje> letrasEnRango = obtenerLetrasEnRango(limiteInferior, limiteSuperior);
	    
	    return letrasEnRango;
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

	private Long CalcularAreaPintada(int[] matrix) {
		long area = 0;

		for (int pixel : matrix) {
			if (pixel == 1) {
				area++;
			}
		}

		return area;
	}

	private String guardarArrayBinarioEnArchivo(int[] arrayBinario, String nombre) {
        try {
            // Convierte el array binario a texto (por ejemplo) para guardarlo en un archivo
            String contenido = Arrays.stream(arrayBinario)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(",")); // Puedes elegir un separador apropiado

            // Define el nombre del archivo
            String nombreArchivo = nombre + ".txt";

            // Define la ruta del archivo
            String pathArchivo = directorio + nombreArchivo;

            // Guarda el contenido en el archivo
            Files.write(Paths.get(pathArchivo), contenido.getBytes());

            // Devuelve el path del archivo
            return pathArchivo;

        } catch (IOException e) {
            // Maneja la excepción apropiadamente
            e.printStackTrace();
            return null;
        }
    }

	@Override
	public int[][] binarizarImagen(BufferedImage image) throws IOException {

		int width = image.getWidth();
		int height = image.getHeight();
		
		int[][] pixelMatrix = new int[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = image.getRGB(x, y);
				// Si el pixel no es negro (0), considéralo blanco (1)
				if (pixel == -16777216) {
					pixel = 1;
				} else {
					pixel = 0;
				}
				pixelMatrix[y][x] = pixel;
			}
		}

		return pixelMatrix;
	}

	@Override
	public int[] arrayBinario(int[][] pixelMatrixInt) {
		int[] arrayBinario = new int[width * height];

		int index = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				arrayBinario[index++] = pixelMatrixInt[y][x];
			}
		}

		return arrayBinario;
	}

}
