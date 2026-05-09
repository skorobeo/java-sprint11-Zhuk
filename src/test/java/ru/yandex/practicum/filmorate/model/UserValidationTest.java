package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    private User validUser() {
        User user = new User();
        user.setEmail("a@b.com");
        user.setLogin("validLogin");
        user.setName("Имя");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        return user;
    }

    @Test
    void emailNull() {
        User user = validUser();
        user.setEmail(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Пустой email - ошибка валидации");
    }

    @Test
    void emailBlank() {
        User user = validUser();
        user.setEmail("   ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Email из пробелов - ошибка валидации");
    }

    @Test
    void emailNoAt() {
        User user = validUser();
        user.setEmail("no-at.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Email без @ - ошибка валидации");
    }

    @Test
    void loginNull() {
        User user = validUser();
        user.setLogin(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Пустой логин - ошибка валидации");
    }

    @Test
    void loginBlank() {
        User user = validUser();
        user.setLogin("   ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Логин из пробелов - ошибка валидации");
    }

    @Test
    void loginWithSpace() {
        User user = validUser();
        user.setLogin("log in");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Пробел в логине - ошибка валидации");
    }

    @Test
    void birthdayFuture() {
        User user = validUser();
        user.setBirthday(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Дата рождения в будущем - ошибка валидации");
    }

    @Test
    void birthdayToday() {
        User user = validUser();
        user.setBirthday(LocalDate.now());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Дата рождения сегодня - ошибок нет");
    }

    @Test
    void emailValid() {
        User user = validUser();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Валидный email - ошибок нет");
    }

    @Test
    void loginValid() {
        User user = validUser();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Валидный логин - ошибок нет");
    }

    @Test
    void nameNullValid() {
        User user = validUser();
        user.setName(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Имя null - ошибок нет");
    }

    @Test
    void nameBlankValid() {
        User user = validUser();
        user.setName("   ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Имя из пробелов - ошибок нет");
    }

    @Test
    void allValid() {
        User user = validUser();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Все поля валидны - ошибок нет");
    }
}