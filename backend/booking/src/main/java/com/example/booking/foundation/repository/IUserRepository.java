package com.example.booking.foundation.repository;

import com.example.booking.entity.User;

import java.util.Optional;

public interface IUserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
}
