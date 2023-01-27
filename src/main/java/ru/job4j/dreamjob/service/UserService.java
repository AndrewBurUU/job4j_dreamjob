package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.User;

import java.util.*;

public interface UserService {

    Optional<User> save(User user);

    boolean deleteById(int id);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findById(int id);

    Collection<User> findAll();
}
