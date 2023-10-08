package net.main.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.main.modelo.Aprendizaje;

@Repository
public interface AprendizajeRepositorio extends JpaRepository<Aprendizaje, Long> {

}
