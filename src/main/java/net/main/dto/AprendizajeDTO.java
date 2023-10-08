package net.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AprendizajeDTO {
	
    private Long id;
    
    private String nombre;
    
    private Long areaPintada;
    
    private String arrayBinario;

}
