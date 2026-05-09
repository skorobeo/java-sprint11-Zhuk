package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    @Test
    void desc200() {
        FilmController c = new FilmController();
        Film f = validFilm();
        f.setDescription("A".repeat(200));
        assertEquals(1, c.addFilm(f).getId());
    }

    @Test
    void dateOk() {
        FilmController c = new FilmController();
        Film f = validFilm();
        f.setReleaseDate(LocalDate.of(1895, 12, 28));
        assertNotNull(c.addFilm(f));
    }

    @Test
    void createOk() {
        FilmController c = new FilmController();
        Film r = c.addFilm(validFilm());
        assertEquals(1, r.getId());
        assertEquals("Фильм", r.getName());
        assertEquals(1, c.getAllFilms().size());
    }

    @Test
    void updNotFound() {
        FilmController c = new FilmController();
        Film f = validFilm();
        f.setId(999);
        assertThrows(NotFoundException.class, () -> c.updateFilm(f));
    }

    @Test
    void updOk() {
        FilmController c = new FilmController();
        Film created = c.addFilm(validFilm());
        created.setName("Новое");
        assertEquals("Новое", c.updateFilm(created).getName());
    }

    private Film validFilm() {
        Film f = new Film();
        f.setName("Фильм");
        f.setDescription("Описание");
        f.setReleaseDate(LocalDate.of(2000, 1, 1));
        f.setDuration(120);
        return f;
    }
}
