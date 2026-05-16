package com.example.booking.foundation.repository.impl;

import com.example.booking.entity.Master;
import com.example.booking.foundation.repository.IMasterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class MasterRepositoryImpl implements IMasterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Master save(Master master) {

        if (master.getId() == null) {
            entityManager.persist(master);
            return master;
        }

        return entityManager.merge(master);
    }

    @Override
    public Optional<Master> findById(Long id) {
        return Optional.ofNullable(
                entityManager.find(Master.class, id)
        );
    }

    @Override
    public List<Master> findAll() {
        return entityManager.createQuery(
                "SELECT m FROM Master m",
                Master.class
        ).getResultList();
    }

    @Override
    public void deleteById(Long id) {

        Master master = entityManager.find(Master.class, id);

        if (master != null) {
            entityManager.remove(master);
        }
    }

    @Override
    public List<Master> findBySalonId(Long salonId) {

        return entityManager.createQuery(
                        "SELECT m FROM Master m WHERE m.salon.id = :salonId",
                        Master.class
                )
                .setParameter("salonId", salonId)
                .getResultList();
    }
}