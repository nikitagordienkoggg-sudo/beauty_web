package com.example.booking.foundation.repository.impl;

import com.example.booking.entity.TimeSlot;
import com.example.booking.foundation.repository.ITimeSlotRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TimeSlotRepositoryImpl implements ITimeSlotRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TimeSlot save(TimeSlot timeSlot) {

        if (timeSlot.getId() == null) {
            entityManager.persist(timeSlot);
            return timeSlot;
        }

        return entityManager.merge(timeSlot);
    }

    @Override
    public Optional<TimeSlot> findById(Long id) {
        return Optional.ofNullable(
                entityManager.find(TimeSlot.class, id)
        );
    }

    @Override
    public List<TimeSlot> findAll() {
        return entityManager.createQuery(
                "SELECT t FROM TimeSlot t",
                TimeSlot.class
        ).getResultList();
    }

    @Override
    public void deleteById(Long id) {

        TimeSlot slot = entityManager.find(TimeSlot.class, id);

        if (slot != null) {
            entityManager.remove(slot);
        }
    }

    @Override
    public List<TimeSlot> findByScheduleId(Long scheduleId) {

        return entityManager.createQuery(
                        "SELECT t FROM TimeSlot t WHERE t.schedule.id = :scheduleId",
                        TimeSlot.class
                )
                .setParameter("scheduleId", scheduleId)
                .getResultList();
    }

    @Override
    public List<TimeSlot> findByAvailable(boolean available) {

        return entityManager.createQuery(
                        "SELECT t FROM TimeSlot t WHERE t.available = :available",
                        TimeSlot.class
                )
                .setParameter("available", available)
                .getResultList();
    }
}