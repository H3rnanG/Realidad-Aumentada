package net.main.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Entity;

import net.main.modelo.Aprendizaje;

@Repository
public interface AprendizajeRepositorio extends JpaRepository<Aprendizaje, Long> {
	
	public List<Aprendizaje> findByAreaPintadaBetween(long limiteInferior, long limiteSuperior);

}
