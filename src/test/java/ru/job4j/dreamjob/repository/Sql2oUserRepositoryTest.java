package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.*;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var user = sql2oUserRepository.save(new User(0, "john@gmail.com", "John", "111"));
        var savedUser = sql2oUserRepository.findById(user.get().getId());
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSameEmailThenOneFail() {
        var user1 = sql2oUserRepository.save(new User(0, "user@gmail.com", "User1", "111"));
        var user2 = sql2oUserRepository.save(new User(1, "user@gmail.com", "User2", "111"));
        var savedUser1 = sql2oUserRepository.findById(user1.get().getId());
        assertThat(savedUser1).usingRecursiveComparison().isEqualTo(user1);
        assertThat(user2).isEmpty();
    }

}