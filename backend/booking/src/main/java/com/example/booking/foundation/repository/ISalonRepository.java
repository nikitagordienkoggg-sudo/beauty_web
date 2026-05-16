package com.example.booking.foundation.repository;

import com.example.booking.entity.Salon;

import java.util.List;
import java.util.Optional;

public interface ISalonRepository {

    Salon save(Salon salon);

    Optional<Salon> findById(Long id);

    List<Salon> findAll();

    void deleteById(Long id);

    List<Salon> findByNameContaining(String name);
}