package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilmId(@PathVariable int id) {
        return filmService.getId(id);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        Film created = filmService.add(film);
        log.info("Фильм добавлен: id={}, name={}", created.getId(), created.getName());
        return created;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {

        Film updated = filmService.update(film);
        log.info("Фильм обновлён: id={}, name={}", updated.getId(), updated.getName());
        return updated;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getTopFilms(count);
    }
}
