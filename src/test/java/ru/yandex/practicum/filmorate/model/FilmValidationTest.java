package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validator;

public class FilmValidationTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = validatorFactory.getValidator();

    private Film validFilm() {
        Film film = new Film();
        film.setName("Фильм");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(2020,03,18));
        film.setDuration(290);
        return film;

    }

    @Test
    void nameNull() {
        Film film = validFilm();
        film.setName(null);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Пустое имя - ошибка валидации");
    }

    @Test
    void nameBlank() {
        Film film = validFilm();
        film.setName("   ");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Имя из пробелов - ошибка валидации");
    }

    @Test
    void nameOk() {
        Film film = validFilm();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "Валидное имя - ошибок нет");
    }

    @Test
    void descriptionTooLong() {
        Film film = validFilm();
        film.setDescription("A".repeat(201));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Описание больше 200 символов - ошибка валидации");
    }

    @Test
    void descriptionOk() {
        Film film = validFilm();
        film.setDescription("A".repeat(200));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "Описание ровно 200 символов - ошибок нет");
    }

    @Test
    void releaseDateEarly() {
        Film film = validFilm();
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Дата раньше 28.12.1895 - ошибка валидации");
    }

    @Test
    void releaseDateOk() {
        Film film = validFilm();
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "Дата ровно 28.12.1895 - ошибок нет");
    }

    @Test
    void durationZero() {
        Film film = validFilm();
        film.setDuration(0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Продолжительность 0 - ошибка валидации");
    }

    @Test
    void durationNegative() {
        Film film = validFilm();
        film.setDuration(-10);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Отрицательная продолжительность - ошибка валидации");
    }

    @Test
    void durationOk() {
        Film film = validFilm();
        film.setDuration(120);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "Положительная продолжительность - ошибок нет");
    }

    @Test
    void allValid() {
        Film film = validFilm();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "Все поля валидны - ошибок нет");
    }
}
