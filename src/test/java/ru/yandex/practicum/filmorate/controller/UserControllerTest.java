package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void emailNull() {
        UserController c = new UserController();
        User u = validUser();
        u.setEmail(null);
        assertThrows(ValidationException.class, () -> c.addUser(u));
    }

    @Test
    void emailBlank() {
        UserController c = new UserController();
        User u = validUser();
        u.setEmail("   ");
        assertThrows(ValidationException.class, () -> c.addUser(u));
    }

    @Test
    void emailNoAt() {
        UserController c = new UserController();
        User u = validUser();
        u.setEmail("no-at.com");
        assertThrows(ValidationException.class, () -> c.addUser(u));
    }

    @Test
    void loginNull() {
        UserController c = new UserController();
        User u = validUser();
        u.setLogin(null);
        assertThrows(ValidationException.class, () -> c.addUser(u));
    }

    @Test
    void loginBlank() {
        UserController c = new UserController();
        User u = validUser();
        u.setLogin("   ");
        assertThrows(ValidationException.class, () -> c.addUser(u));
    }

    @Test
    void loginSpaces() {
        UserController c = new UserController();
        User u = validUser();
        u.setLogin("log in");
        assertThrows(ValidationException.class, () -> c.addUser(u));
    }

    @Test
    void bdayToday() {
        UserController c = new UserController();
        User u = validUser();
        u.setBirthday(LocalDate.now());
        assertNotNull(c.addUser(u));
    }

    @Test
    void bdayFuture() {
        UserController c = new UserController();
        User u = validUser();
        u.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> c.addUser(u));
    }

    @Test
    void nameUsesLogin() {
        UserController c = new UserController();
        User u = validUser();
        u.setName(null);
        assertEquals("validLogin", c.addUser(u).getName());
    }

    @Test
    void nameBlankUsesLogin() {
        UserController c = new UserController();
        User u = validUser();
        u.setName("   ");
        assertEquals("validLogin", c.addUser(u).getName());
    }

    @Test
    void createOk() {
        UserController c = new UserController();
        User r = c.addUser(validUser());
        assertEquals(1, r.getId());
        assertEquals(1, c.getAllUsers().size());
    }

    @Test
    void updNotFound() {
        UserController c = new UserController();
        User u = validUser();
        u.setId(999);
        assertThrows(ValidationException.class, () -> c.updateUser(u));
    }

    @Test
    void updOk() {
        UserController c = new UserController();
        User created = c.addUser(validUser());
        created.setEmail("new@x.com");
        assertEquals("new@x.com", c.updateUser(created).getEmail());
    }

    private User validUser() {
        User u = new User();
        u.setEmail("a@b.com");
        u.setLogin("validLogin");
        u.setName("Имя");
        u.setBirthday(LocalDate.of(1990, 1, 1));
        return u;
    }
}
