package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.yandex.practicum.filmorate.validation.ReleaseDate;
import java.util.Set;
import java.util.HashSet;

/**
 * Film.
 */
@Data
public class Film {

    private long id;
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания - 200 символов")
    private String description;
    @ReleaseDate
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private int duration;
    private Set<Long> likes = new HashSet<>();
}
