package com.example.booking.foundation.repository.impl;

import com.example.booking.entity.Salon;
import com.example.booking.foundation.repository.ISalonRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class SalonRepositoryImpl implements ISalonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Salon save(Salon salon) {

        if (salon.getId() == null) {
            entityManager.persist(salon);
            return salon;
        }

        return entityManager.merge(salon);
    }

    @Override
    public Optional<Salon> findById(Long id) {
        return Optional.ofNullable(
                entityManager.find(Salon.class, id)
        );
    }

    @Override
    public List<Salon> findAll() {
        return entityManager.createQuery(
                "SELECT s FROM Salon s",
                Salon.class
        ).getResultList();
    }

    @Override
    public void deleteById(Long id) {

        Salon salon = entityManager.find(Salon.class, id);

        if (salon != null) {
            entityManager.remove(salon);
        }
    }

    @Override
    public List<Salon> findByNameContaining(String name) {

        return entityManager.createQuery(
                        "SELECT s FROM Salon s WHERE LOWER(s.name) LIKE LOWER(:name)",
                        Salon.class
                )
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}