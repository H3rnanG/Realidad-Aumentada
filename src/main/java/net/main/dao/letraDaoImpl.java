package net.main.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.main.servicio.LetraServicio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class letraDaoImpl implements LetraServicio{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void analizar() {
        
    }
    
}
