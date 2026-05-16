package com.example.booking.control.controller;

import com.example.booking.entity.ServiceEntity;
import com.example.booking.mediator.interfaces.IServiceCatalogService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final IServiceCatalogService service;

    public ServiceController(IServiceCatalogService service) {
        this.service = service;
    }

    @GetMapping
    public List<ServiceEntity> search(@RequestParam(required = false) String name) {
        return service.searchServices(name);
    }

    @PostMapping
    public ServiceEntity create(@RequestBody ServiceEntity s) {
        return service.createService(s);
    }

    @PutMapping("/{id}")
    public ServiceEntity update(@PathVariable Long id,
                                @RequestBody ServiceEntity s) {
        return service.updateService(id, s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteService(id);
    }
}