package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FilmServiceTest {

    private InMemoryFilmStorage filmStorage;
    private InMemoryUserStorage userStorage;
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmStorage = new InMemoryFilmStorage();
        userStorage = new InMemoryUserStorage();
        filmService = new FilmService(filmStorage, userStorage);
    }

    @Test
    void addLikeOk() {
        Film film = filmService.add(validFilm());
        User user = userStorage.add(validUser());
        filmService.addLike(film.getId(), user.getId());
        assertEquals(1, film.getLikes().size());
        assertTrue(film.getLikes().contains(user.getId()));
    }

    @Test
    void removeLikeOk() {
        Film film = filmService.add(validFilm());
        User user = userStorage.add(validUser());
        filmService.addLike(film.getId(), user.getId());
        filmService.removeLike(film.getId(), user.getId());
        assertEquals(0, film.getLikes().size());
    }

    @Test
    void getTopFilmsOk() {
        Film film1 = filmService.add(validFilm());
        Film film2 = filmService.add(validFilm());
        User user1 = userStorage.add(validUser());
        User user2 = userStorage.add(validUser());
        filmService.addLike(film1.getId(), user1.getId());
        filmService.addLike(film1.getId(), user2.getId());
        List<Film> topFilms = filmService.getTopFilms(10);
        assertEquals(film1.getId(), topFilms.get(0).getId());
    }

    @Test
    void getTopFilmsLimit() {
        filmService.add(validFilm());
        filmService.add(validFilm());
        filmService.add(validFilm());
        List<Film> topFilms = filmService.getTopFilms(2);
        assertEquals(2, topFilms.size());
    }

    @Test
    void addLikeUnknownFilm() {
        User user = userStorage.add(validUser());
        long unknownFilmId = 999L;
        assertThrows(NotFoundException.class,
                () -> filmService.addLike(unknownFilmId, user.getId()));
    }

    @Test
    void addLikeUnknownUser() {
        Film film = filmService.add(validFilm());
        long unknownUserId = 999L;
        assertThrows(NotFoundException.class,
                () -> filmService.addLike(film.getId(), unknownUserId));
    }

    private Film validFilm() {
        Film film = new Film();
        film.setName("Фильм");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);
        return film;
    }

    private User validUser() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setLogin("testLogin");
        user.setName("Имя");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        return user;
    }
}