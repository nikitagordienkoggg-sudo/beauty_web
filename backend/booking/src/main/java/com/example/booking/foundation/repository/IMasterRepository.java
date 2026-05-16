package com.example.booking.foundation.repository;

import com.example.booking.entity.Master;

import java.util.List;
import java.util.Optional;

public interface IMasterRepository {

    Master save(Master master);

    Optional<Master> findById(Long id);

    List<Master> findAll();

    void deleteById(Long id);

    List<Master> findBySalonId(Long salonId);
}