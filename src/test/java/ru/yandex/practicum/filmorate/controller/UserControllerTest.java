package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {


    private final InMemoryUserStorage userStorage = new InMemoryUserStorage();
    private final UserService userService = new UserService(userStorage);

    @Test
    void bdayToday() {
        UserController controller = new UserController(userService);
        User user = validUser();
        user.setBirthday(LocalDate.now());
        assertNotNull(controller.addUser(user));
    }

    @Test
    void nameUsesLogin() {
        UserController controller = new UserController(userService);
        User user = validUser();
        user.setName(null);
        assertEquals("validLogin", controller.addUser(user).getName());
    }

    @Test
    void nameBlankUsesLogin() {
        UserController controller = new UserController(userService);
        User user = validUser();
        user.setName("   ");
        assertEquals("validLogin", controller.addUser(user).getName());
    }

    @Test
    void createOk() {
        UserController controller = new UserController(userService);
        User user = controller.addUser(validUser());
        assertEquals(1, user.getId());
        assertEquals(1, controller.getAllUsers().size());
    }

    @Test
    void updNotFound() {
        UserController controller = new UserController(userService);
        User user = validUser();
        user.setId(999);
        assertThrows(NotFoundException.class, () -> controller.updateUser(user));
    }

    @Test
    void updOk() {
        UserController controller = new UserController(userService);
        User created = controller.addUser(validUser());
        created.setEmail("new@x.com");
        assertEquals("new@x.com", controller.updateUser(created).getEmail());
    }

    private User validUser() {
        User user = new User();
        user.setEmail("a@b.com");
        user.setLogin("validLogin");
        user.setName("Имя");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        return user;
    }
}
