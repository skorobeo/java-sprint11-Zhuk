package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(ValidationException e) {
        log.warn("Ошибка валидации: {}", e.getMessage());
        return new ErrorResponse(
                "Ошибка валидации",
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        StringBuilder stringBuilder = new StringBuilder();
        for (org.springframework.validation.FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            stringBuilder.append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        }
        String meassage = stringBuilder.toString();
        log.warn("Валидация модели не пройдена: {}", meassage);
        return new ErrorResponse(
                "Ошибка валидации",
                meassage,
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException e) {
        log.warn("Сущность не найдена {}", e.getMessage());
        return new ErrorResponse(
                "Сущность не найдена",
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(Throwable e) {
        log.error("Внутрення ошибка сервера", e);
        return new ErrorResponse(
                "Внутрення ошибка сервера",
                e.getMessage() != null ? e.getMessage() : "Неизвестная ошибка",
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());


    }
}
