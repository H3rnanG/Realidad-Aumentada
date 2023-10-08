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

import net.main.modelo.Aprendizaje;
import net.main.modelo.Letra;
import net.main.servicio.AprendizajeServicio;
import net.main.servicio.LetraServicio;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("api/")
public class LetraController {

	@Autowired
	private LetraServicio LetraServicio;
	@Autowired
	private AprendizajeServicio aprendizajeServicio;

	@PostMapping("analizar") 
	public Aprendizaje analizarImagen(@RequestParam("file") MultipartFile fileInput){
		
		BufferedImage processedImage = aprendizajeServicio.procesarImagen(fileInput);
		int[][] pixelMatrix = null;
		try {
			pixelMatrix = aprendizajeServicio.binarizarImagen(processedImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int[] arrayBinario = aprendizajeServicio.arrayBinario(pixelMatrix);
		
		Aprendizaje letra = aprendizajeServicio.encontrarLetraSimilar(arrayBinario, aprendizajeServicio.TraerImagenes(arrayBinario));
		
		
		return letra;
	}
	
	//tst
	@PostMapping("ver") 
	public List<Aprendizaje> verImagenes(@RequestParam("file") MultipartFile fileInput){
		
		BufferedImage processedImage = aprendizajeServicio.procesarImagen(fileInput);
		int[][] pixelMatrix = null;
		try {
			pixelMatrix = aprendizajeServicio.binarizarImagen(processedImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] arrayBinario = aprendizajeServicio.arrayBinario(pixelMatrix);
		
		return aprendizajeServicio.TraerImagenes(arrayBinario);
	}
	

	@PostMapping("proceso")
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
	public void GuardarImagen(@RequestParam("file") MultipartFile file, @RequestParam("nombre") String nombre)
			throws IOException {
		BufferedImage processedImage = aprendizajeServicio.procesarImagen(file);
		int[][] pixelMatrix = aprendizajeServicio.binarizarImagen(processedImage);

		int[] arrayBinario = aprendizajeServicio.arrayBinario(pixelMatrix);

		aprendizajeServicio.guardarLetra(arrayBinario, nombre);

	}
}
