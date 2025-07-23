package pro.sky.java.course2.examinerservice.exceptions;

/**
 * Исключение при отсутствии вопроса.
 */
public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}