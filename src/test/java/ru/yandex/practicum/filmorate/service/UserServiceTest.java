package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private InMemoryUserStorage userStorage;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userStorage = new InMemoryUserStorage();
        userService = new UserService(userStorage);
    }

    @Test
    void addFriendOk() {
        User user1 = userService.add(validUser("login1", "user1@mail.com"));
        User user2 = userService.add(validUser("login2", "user2@mail.com"));
        userService.addFriend(user1.getId(), user2.getId());
        List<User> friendsOf1 = userService.getFriends(user1.getId());
        List<User> friendsOf2 = userService.getFriends(user2.getId());
        assertEquals(1, friendsOf1.size());
        assertEquals(user2.getId(), friendsOf1.get(0).getId());
        assertEquals(1, friendsOf2.size());
        assertEquals(user1.getId(), friendsOf2.get(0).getId());
    }

    @Test
    void removeFriendOk() {
        User user1 = userService.add(validUser("login1", "user1@mail.com"));
        User user2 = userService.add(validUser("login2", "user2@mail.com"));
        userService.addFriend(user1.getId(), user2.getId());
        userService.removeFriend(user1.getId(), user2.getId());
        assertEquals(0, userService.getFriends(user1.getId()).size());
        assertEquals(0, userService.getFriends(user2.getId()).size());
    }

    @Test
    void getCommonFriendsOk() {
        User user1 = userService.add(validUser("login1", "user1@mail.com"));
        User user2 = userService.add(validUser("login2", "user2@mail.com"));
        User common = userService.add(validUser("common", "common@mail.com"));
        userService.addFriend(user1.getId(), common.getId());
        userService.addFriend(user2.getId(), common.getId());
        List<User> commonFriends = userService.getCommonFriends(user1.getId(), user2.getId());
        assertEquals(1, commonFriends.size());
        assertEquals(common.getId(), commonFriends.get(0).getId());
    }

    @Test
    void addFriendUnknownUser() {
        User user = userService.add(validUser("login1", "user1@mail.com"));
        long unknownId = 999L;
        assertThrows(NotFoundException.class,
                () -> userService.addFriend(user.getId(), unknownId));
    }

    @Test
    void removeFriendUnknownUser() {
        User user = userService.add(validUser("login1", "user1@mail.com"));
        long unknownId = 999L;
        assertThrows(NotFoundException.class,
                () -> userService.removeFriend(user.getId(), unknownId));
    }

    @Test
    void nameFallbackOnAdd() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setLogin("testLogin");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        User created = userService.add(user);
        assertEquals("testLogin", created.getName());
    }

    private User validUser(String login, String email) {
        User user = new User();
        user.setLogin(login);
        user.setName("Имя");
        user.setEmail(email);
        user.setBirthday(LocalDate.of(1990, 1, 1));
        return user;
    }
}