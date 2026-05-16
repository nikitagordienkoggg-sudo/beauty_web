package com.example.booking.foundation.repository.impl;

import com.example.booking.entity.ServiceEntity;
import com.example.booking.foundation.repository.IServiceEntityRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ServiceEntityRepositoryImpl implements IServiceEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ServiceEntity save(ServiceEntity service) {

        if (service.getId() == null) {
            entityManager.persist(service);
            return service;
        }

        return entityManager.merge(service);
    }

    @Override
    public Optional<ServiceEntity> findById(Long id) {

        return Optional.ofNullable(
                entityManager.find(ServiceEntity.class, id)
        );
    }

    @Override
    public List<ServiceEntity> findAll() {

        return entityManager.createQuery(
                "SELECT s FROM ServiceEntity s",
                ServiceEntity.class
        ).getResultList();
    }

    @Override
    public List<ServiceEntity> findByNameContaining(String name) {

        return entityManager.createQuery(
                        "SELECT s FROM ServiceEntity s " +
                        "WHERE LOWER(s.name) LIKE LOWER(:name)",
                        ServiceEntity.class
                )
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {

        ServiceEntity service = entityManager.find(ServiceEntity.class, id);

        if (service != null) {
            entityManager.remove(service);
        }
    }

    @Override
    public List<ServiceEntity> findBySalonId(Long salonId) {
        return entityManager.createQuery(
                        "SELECT s FROM ServiceEntity s WHERE s.salon.id = :salonId",
                        ServiceEntity.class
                )
                .setParameter("salonId", salonId)
                .getResultList();
    }

    
}