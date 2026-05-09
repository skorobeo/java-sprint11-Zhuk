package ru.yandex.practicum.filmorate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse  {
    private final String error;
    private final String message;
    private final LocalDateTime time;
    private final int status;

}
