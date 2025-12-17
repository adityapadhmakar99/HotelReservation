package com.ap.repository;

import com.ap.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user = new User(user.getName(), user.getEmail(), user.getPhoneNumber(), user.getUserType());
        }
        users.put(user.getId(), user);
        usersByEmail.put(user.getEmail().toLowerCase(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(usersByEmail.get(email.toLowerCase()));
    }

    @Override
    public List<User> findByName(String name) {
        return users.values().stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteById(String id) {
        findById(id).ifPresent(user -> {
            usersByEmail.remove(user.getEmail().toLowerCase());
            users.remove(id);
        });
    }

    @Override
    public boolean existsById(String id) {
        return users.containsKey(id);
    }
}
