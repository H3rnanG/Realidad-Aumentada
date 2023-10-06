package net.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class letraDaoImpl implements letraDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void analizar() {
        
    }
    
}
