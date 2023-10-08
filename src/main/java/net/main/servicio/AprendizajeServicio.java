package net.main.servicio;


import java.awt.image.BufferedImage;

import org.springframework.web.multipart.MultipartFile;

import net.main.modelo.Aprendizaje;
import net.main.modelo.Letra;

public interface AprendizajeServicio {
	
	
	public void guardarLetra(Aprendizaje letra);
	
	public BufferedImage procesarImagen(MultipartFile file);
	
}
