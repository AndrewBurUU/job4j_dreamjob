package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.*;

import java.util.*;

public interface UserRepository {

    Optional<User> save(User user);

    boolean deleteById(int id);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findById(int id);

    Collection<User> findAll();

}