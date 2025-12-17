package com.ap.repository;

import com.ap.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findByName(String name);
}
