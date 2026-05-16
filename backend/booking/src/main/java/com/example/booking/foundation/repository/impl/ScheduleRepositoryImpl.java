package com.example.booking.foundation.repository.impl;

import com.example.booking.entity.Schedule;
import com.example.booking.foundation.repository.IScheduleRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ScheduleRepositoryImpl implements IScheduleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Schedule save(Schedule schedule) {

        if (schedule.getId() == null) {
            entityManager.persist(schedule);
            return schedule;
        }

        return entityManager.merge(schedule);
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return Optional.ofNullable(
                entityManager.find(Schedule.class, id)
        );
    }

    @Override
    public List<Schedule> findAll() {
        return entityManager.createQuery(
                "SELECT s FROM Schedule s",
                Schedule.class
        ).getResultList();
    }

    @Override
    public void deleteById(Long id) {

        Schedule schedule = entityManager.find(Schedule.class, id);

        if (schedule != null) {
            entityManager.remove(schedule);
        }
    }

    @Override
    public List<Schedule> findByMasterId(Long masterId) {

        return entityManager.createQuery(
                        "SELECT s FROM Schedule s WHERE s.master.id = :masterId",
                        Schedule.class
                )
                .setParameter("masterId", masterId)
                .getResultList();
    }

    @Override
    public List<Schedule> findByDate(LocalDate date) {

        return entityManager.createQuery(
                        "SELECT s FROM Schedule s WHERE s.date = :date",
                        Schedule.class
                )
                .setParameter("date", date)
                .getResultList();
    }
}