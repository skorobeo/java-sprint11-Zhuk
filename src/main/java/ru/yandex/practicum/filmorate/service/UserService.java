package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(User user) {
        applyNameFallback(user);
        return userStorage.add(user);
    }

    public User update(User user) {
        applyNameFallback(user);
        return userStorage.update(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User getById(long id) {
        return userStorage.getById(id);
    }

    public void addFriend(long userId, long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        log.info("Пользователи {} и {} теперь друзья", userId, friendId);
    }

    public void removeFriend(long userId, long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        log.info("Пользователи {} и {} больше не друзья", userId, friendId);
    }

    public List<User> getFriends(long userId) {
        User user = getById(userId);
        List<User> friendsList = new ArrayList<>();
        for (Long friendId : user.getFriends()) {
            friendsList.add(getById(friendId));
        }
        return friendsList;
    }

    public List<User> getCommonFriends(long userId, long otherId) {
        User user = getById(userId);
        User other = getById(otherId);
        Set<Long> common = new HashSet<>(user.getFriends());

        common.retainAll(other.getFriends());
        List<User> commonFriends = new ArrayList<>();
        for (Long id : common) {
            commonFriends.add(getById(id));
        }
        return commonFriends;
    }

    private void applyNameFallback(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
