package net.main.servicio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.main.modelo.Aprendizaje;

public interface AprendizajeServicio {

	public void guardarLetra(int[] arrayBinario, String nombre);

	public BufferedImage procesarImagen(MultipartFile file);

	public int[][] binarizarImagen(BufferedImage image) throws IOException;

	public int[] arrayBinario(int[][] pixelMatrix);
	
	public List<Aprendizaje> TraerImagenes(int[] matrix);
	
	public Aprendizaje encontrarLetraSimilar(int[] arrayBinarioImagen, List<Aprendizaje> letrasEnRango);
}
