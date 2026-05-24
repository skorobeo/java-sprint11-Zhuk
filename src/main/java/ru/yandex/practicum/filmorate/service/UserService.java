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
        return userStorage.add(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User getId(int id) {
        return userStorage.getId(id);
    }

    public void addFriend(int userId, int friendId) {
        User user = getId(userId);
        User friend = getId(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        log.info("Пользователи {} и {} теперь друзья", userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        User user = getId(userId);
        User friend = getId(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        log.info("Пользователи {} и {} больше не друзья", userId, friendId);
    }

    public List<User> getFriends(int userId) {
        User user = getId(userId);
        List<User> friendsList = new ArrayList<>();
        for (Integer friendId : user.getFriends()) {
            friendsList.add(getId(friendId));
        }
        return friendsList;
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        User user = getId(userId);
        User other = getId(otherId);
        Set<Integer> common = new HashSet<>(user.getFriends());

        common.retainAll(other.getFriends());
        List<User> commonFriends = new ArrayList<>();
        for (Integer id : common) {
            commonFriends.add(getId(id));
        }
        return commonFriends;
    }
}
