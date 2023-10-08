package net.main.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.main.servicio.LetraServicio;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class letraDaoImpl implements LetraServicio{

    @PersistenceContext
    private EntityManager entityManager;

    public void analizar() {
        
    }
    
}
