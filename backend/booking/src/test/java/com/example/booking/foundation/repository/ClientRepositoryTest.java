package com.example.booking.foundation.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.booking.entity.Client;
import com.example.booking.entity.Role;
import com.example.booking.foundation.repository.impl.ClientRepositoryImpl;

@SpringBootTest
@ActiveProfiles("test")
@Import(ClientRepositoryImpl.class)
public class ClientRepositoryTest{

    @Autowired IClientRepository repo;

    @Test
    void shouldSaveClient() {

        Client c = new Client();
        c.setName("Test");
        c.setEmail("a@a.com");
        c.setPassword("pwd");
        c.setRole(Role.ROLE_CLIENT);

        Client saved = repo.save(c);

        assertNotNull(saved.getId());
    }
    
    @Test
    void shouldFindByEmail() {

        Client c = new Client();
        c.setEmail("test@mail.com");
        c.setName("Test");
        c.setPassword("pwd");
        c.setRole(Role.ROLE_CLIENT);

        repo.save(c);

        var result = repo.findByEmail("test@mail.com");

        assertTrue(result.isPresent());
    }

}
