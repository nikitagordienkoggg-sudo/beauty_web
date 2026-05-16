package com.example.booking.foundation.repository.impl;

import com.example.booking.entity.Booking;
import com.example.booking.foundation.repository.IBookingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookingRepositoryImpl implements IBookingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Booking save(Booking booking) {

        if (booking.getId() == null) {
            entityManager.persist(booking);
            return booking;
        }

        return entityManager.merge(booking);
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return Optional.ofNullable(
                entityManager.find(Booking.class, id)
        );
    }

    @Override
    public List<Booking> findAll() {
        return entityManager.createQuery(
                "SELECT b FROM Booking b",
                Booking.class
        ).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Booking booking = entityManager.find(Booking.class, id);
        if (booking != null) {
            entityManager.remove(booking);
        }
    }

    @Override
    public List<Booking> findByClientId(Long clientId) {
        return entityManager.createQuery(
                        "SELECT b FROM Booking b WHERE b.client.id = :clientId",
                        Booking.class
                )
                .setParameter("clientId", clientId)
                .getResultList();
    }

    @Override
    public List<Booking> findByStatus(String status) {
        return entityManager.createQuery(
                        "SELECT b FROM Booking b WHERE b.status = :status",
                        Booking.class
                )
                .setParameter("status", status)
                .getResultList();
    }
}