package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    private int currentId = 1;

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {

        film.setId(currentId++);
        films.put(film.getId(), film);
        log.info("Фильм добавлен: id={}, name={}", film.getId(), film.getName());
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с id=" + film.getId() + " не найден");
        }
        films.put(film.getId(), film);
        log.info("Фильм обновлён: id={}, name={}", film.getId(), film.getName());
        return film;
    }


}
