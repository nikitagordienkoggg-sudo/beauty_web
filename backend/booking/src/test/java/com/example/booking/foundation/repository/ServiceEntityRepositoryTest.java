package com.example.booking.foundation.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.booking.entity.ServiceEntity;
import com.example.booking.foundation.repository.impl.ServiceEntityRepositoryImpl;

@SpringBootTest
@ActiveProfiles("test")
@Import(ServiceEntityRepositoryImpl.class)
public class ServiceEntityRepositoryTest {

 @Autowired IServiceEntityRepository repo;

    @Test
    void shouldFindByName() {

        var result = repo.findByNameContaining("hair");

        assertNotNull(result);
    }

    @Test
    void shouldFindByNameContaining() {

        ServiceEntity s = new ServiceEntity();
        s.setName("Haircut");
        repo.save(s);

        var result = repo.findByNameContaining("hair");

        assertFalse(result.isEmpty());
    }
}
