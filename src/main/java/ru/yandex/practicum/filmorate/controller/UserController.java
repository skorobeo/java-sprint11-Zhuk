package ru.yandex.practicum.filmorate.controller;



import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    private int currentId = 1;

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(currentId++);
        users.put(user.getId(),user);
        log.info("Пользователь создан: id={}, email={}", user.getId(), user.getEmail());
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь с id=" + user.getId() + " не найден");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Пользователь обновлён: id={}, email={}", user.getId(), user.getEmail());
        return user;
    }

}
