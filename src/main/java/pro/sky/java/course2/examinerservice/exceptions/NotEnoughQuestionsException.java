package pro.sky.java.course2.examinerservice.exceptions;

/**
 * Исключение при недостатке вопросов.
 */
public class NotEnoughQuestionsException extends RuntimeException {
    public NotEnoughQuestionsException(String message) {
        super(message);
    }
}

