package net.controller;

import java.awt.Color;
import net.dao.letraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.http.MediaType;

@RestController
public class LetraController {

    @Autowired
    private letraDao letraDao;
    
    @RequestMapping(value = "api/analizar", method = RequestMethod.POST)
    public ResponseEntity<byte[]> analizarLetra(@RequestParam("file") MultipartFile file){
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

            // Devuelve la imagen redimensionada y en blanco y negro como respuesta binaria
            return ResponseEntity.ok().body(resizedImageBytes);
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
    