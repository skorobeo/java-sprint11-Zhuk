package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private final InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
    private final InMemoryUserStorage userStorage = new InMemoryUserStorage();
    private final FilmService filmService = new FilmService(filmStorage, userStorage);

    @Test
    void desc200() {
        FilmController controller = new FilmController(filmService);
        Film film = validFilm();
        film.setDescription("A".repeat(200));
        assertEquals(1, controller.addFilm(film).getId());
    }

    @Test
    void dateOk() {
        FilmController controller = new FilmController(filmService);
        Film film = validFilm();
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        assertNotNull(controller.addFilm(film));
    }

    @Test
    void createOk() {
        FilmController controller = new FilmController(filmService);
        Film r = controller.addFilm(validFilm());
        assertEquals(1, r.getId());
        assertEquals("Фильм", r.getName());
        assertEquals(1, controller.getAllFilms().size());
    }

    @Test
    void updNotFound() {
        FilmController controller = new FilmController(filmService);
        Film film = validFilm();
        film.setId(999);
        assertThrows(NotFoundException.class, () -> controller.updateFilm(film));
    }

    @Test
    void updOk() {
        FilmController controller = new FilmController(filmService);
        Film created = controller.addFilm(validFilm());
        created.setName("Новое");
        assertEquals("Новое", controller.updateFilm(created).getName());
    }

    private Film validFilm() {
        Film film = new Film();
        film.setName("Фильм");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);
        return film;
    }
}
