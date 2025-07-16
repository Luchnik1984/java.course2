package pro.sky.java.course2.examinerservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.sky.java.course2.examinerservice.exeptions.EmptyQuestionListException;
import pro.sky.java.course2.examinerservice.exeptions.QuestionAlreadyExistsException;

/**
 * Обработчик исключений для контроллеров.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает невалидные аргументы (400 Bad Request).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Обрабатывает отсутствующие вопросы (404 Not Found).
     */
    public ResponseEntity<String> handleNotFound(RuntimeException e) {
        return ResponseEntity.notFound().build();// 404
    }

    /**
     * Обрабатывает конфликты (дубликаты вопросов).
     */
    @ExceptionHandler(QuestionAlreadyExistsException.class)
    public ResponseEntity<String> handleQuestionAlreadyExists(QuestionAlreadyExistsException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    /**
     * Обрабатывает пустую коллекцию вопросов ( NO_CONTENT).
     */
    @ExceptionHandler(EmptyQuestionListException.class)
    public ResponseEntity<String> handleEmptyList(EmptyQuestionListException e) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(e.getMessage());
    }

    /**
     * Обрабатывает непредвиденные ошибки (500).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalError(Exception e) {
        return ResponseEntity.internalServerError().body("Внутренняя ошибка сервера");
    }
}

