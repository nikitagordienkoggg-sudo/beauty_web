package com.example.booking.mediator.interfaces;

import com.example.booking.entity.ServiceEntity;

import java.util.List;

public interface IServiceCatalogService {

    List<ServiceEntity> searchServices(String name);

    ServiceEntity createService(ServiceEntity service);

    ServiceEntity updateService(Long id, ServiceEntity service);

    void deleteService(Long id);

    ServiceEntity getServiceById(Long id);
}