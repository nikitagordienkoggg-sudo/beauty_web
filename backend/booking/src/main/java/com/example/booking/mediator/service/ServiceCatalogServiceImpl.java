package com.example.booking.mediator.service;

import com.example.booking.entity.ServiceEntity;
import com.example.booking.foundation.repository.IServiceEntityRepository;
import com.example.booking.mediator.interfaces.IServiceCatalogService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceCatalogServiceImpl implements IServiceCatalogService {

    private final IServiceEntityRepository serviceRepository;

    public ServiceCatalogServiceImpl(IServiceEntityRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    // ========================= UC-01 SEARCH =========================

    @Override
    public List<ServiceEntity> searchServices(String name) {

        if (name == null || name.isBlank()) {
            return serviceRepository.findAll();
        }

        return serviceRepository.findByNameContaining(name);
    }

    // ========================= UC-06 CREATE =========================

    @Override
    public ServiceEntity createService(ServiceEntity service) {

        if (service.getPrice() <= 0) {
            throw new RuntimeException("Price must be greater than 0");
        }

        return serviceRepository.save(service);
    }

    // ========================= UC-06 UPDATE =========================

    @Override
    public ServiceEntity updateService(Long id, ServiceEntity updated) {

        ServiceEntity existing = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        existing.updateDetails(
                updated.getName(),
                updated.getDescription(),
                updated.getDuration(),
                updated.getPrice()
        );

        return serviceRepository.save(existing);
    }

    // ========================= UC-06 DELETE =========================

    @Override
    public void deleteService(Long id) {

        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        serviceRepository.deleteById(id);
    }

    // ========================= UC-01 GET BY ID =========================

    @Override
    public ServiceEntity getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }
}