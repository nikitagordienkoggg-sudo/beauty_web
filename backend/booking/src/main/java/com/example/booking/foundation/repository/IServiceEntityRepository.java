package com.example.booking.foundation.repository;

import java.util.List;
import java.util.Optional;

import com.example.booking.entity.ServiceEntity;

public interface IServiceEntityRepository {

    ServiceEntity save(ServiceEntity service);

    Optional<ServiceEntity> findById(Long id);

    List<ServiceEntity> findAll();

    List<ServiceEntity> findByNameContaining(String name);

    void deleteById(Long id);

    List<ServiceEntity> findBySalonId(Long salonId);
}