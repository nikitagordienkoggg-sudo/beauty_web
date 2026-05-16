package com.example.booking.foundation.repository.impl;

import com.example.booking.entity.Client;
import com.example.booking.foundation.repository.IClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ClientRepositoryImpl implements IClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Client save(Client client) {

        if (client.getId() == null) {
            entityManager.persist(client);
            return client;
        }

        return entityManager.merge(client);
    }

    @Override
    public Optional<Client> findById(Long id) {

        return Optional.ofNullable(
                entityManager.find(Client.class, id)
        );
    }

    @Override
    public Optional<Client> findByEmail(String email) {

        List<Client> result = entityManager.createQuery(
                        "SELECT c FROM Client c WHERE c.email = :email",
                        Client.class
                )
                .setParameter("email", email)
                .getResultList();

        return result.stream().findFirst();
    }

    @Override
    public List<Client> findAll() {

        return entityManager.createQuery(
                "SELECT c FROM Client c",
                Client.class
        ).getResultList();
    }

    @Override
    public void deleteById(Long id) {

        Client client = entityManager.find(Client.class, id);

        if (client != null) {
            entityManager.remove(client);
        }
    }
}