package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements  FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    private long  currentId = 1;

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film add(Film film) {
        film.setId(currentId++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с id: " + film.getId() + " не найден.");
        }

        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getById(long id) {
        Film film = films.get(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id=" + id + " не найден");
        }
        return film;
    }

        @Override
    public void delete(long id) {
        if (films.remove(id) == null) {
            throw new NotFoundException("Фильм с id=" + id + " не найден");
        }
    }

}
