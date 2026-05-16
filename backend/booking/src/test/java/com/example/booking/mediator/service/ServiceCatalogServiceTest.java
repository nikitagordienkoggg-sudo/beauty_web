package com.example.booking.mediator.service;

import com.example.booking.entity.ServiceEntity;
import com.example.booking.foundation.repository.IServiceEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceCatalogServiceTest {

    @Mock
    private IServiceEntityRepository repository;

    @InjectMocks
    private ServiceCatalogServiceImpl service;

    @Test
    void searchServicesUsesFindAllForBlankName() {
        List<ServiceEntity> expected = List.of(new ServiceEntity());
        when(repository.findAll()).thenReturn(expected);

        assertSame(expected, service.searchServices(" "));
        assertSame(expected, service.searchServices(null));
    }

    @Test
    void searchServicesUsesContainingForName() {
        List<ServiceEntity> expected = List.of(new ServiceEntity());
        when(repository.findByNameContaining("hair")).thenReturn(expected);

        assertSame(expected, service.searchServices("hair"));
    }

    @Test
    void createServiceRejectsNonPositivePrice() {
        ServiceEntity s = new ServiceEntity();
        s.setPrice(0.0);

        assertThrows(RuntimeException.class, () -> service.createService(s));
    }

    @Test
    void updateAndDeleteAndGetById() {
        ServiceEntity existing = new ServiceEntity();
        existing.setName("old");
        ServiceEntity updated = new ServiceEntity();
        updated.setName("new");
        updated.setDescription("desc");
        updated.setDuration(30);
        updated.setPrice(50.0);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        ServiceEntity result = service.updateService(1L, updated);
        assertEquals("new", result.getName());
        assertEquals("desc", result.getDescription());
        assertEquals(30, result.getDuration());
        assertEquals(50.0, result.getPrice());

        assertSame(existing, service.getServiceById(1L));
        service.deleteService(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void methodsThrowWhenServiceMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.updateService(99L, new ServiceEntity()));
        assertThrows(RuntimeException.class, () -> service.deleteService(99L));
        assertThrows(RuntimeException.class, () -> service.getServiceById(99L));
    }
}
