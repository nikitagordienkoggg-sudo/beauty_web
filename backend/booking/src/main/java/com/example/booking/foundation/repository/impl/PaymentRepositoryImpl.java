package com.example.booking.foundation.repository.impl;

import com.example.booking.entity.Payment;
import com.example.booking.foundation.repository.IPaymentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PaymentRepositoryImpl implements IPaymentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Payment save(Payment payment) {

        if (payment.getId() == null) {
            entityManager.persist(payment);
            return payment;
        }

        return entityManager.merge(payment);
    }

    @Override
    public Optional<Payment> findById(Long id) {

        return Optional.ofNullable(
                entityManager.find(Payment.class, id)
        );
    }

    @Override
    public List<Payment> findAll() {

        return entityManager.createQuery(
                "SELECT p FROM Payment p",
                Payment.class
        ).getResultList();
    }

    @Override
    public Optional<Payment> findByBookingId(Long bookingId) {

        List<Payment> result = entityManager.createQuery(
                        "SELECT p FROM Payment p WHERE p.booking.id = :bookingId",
                        Payment.class
                )
                .setParameter("bookingId", bookingId)
                .getResultList();

        return result.stream().findFirst();
    }

    @Override
    public void deleteById(Long id) {

        Payment payment = entityManager.find(Payment.class, id);

        if (payment != null) {
            entityManager.remove(payment);
        }
    }
}