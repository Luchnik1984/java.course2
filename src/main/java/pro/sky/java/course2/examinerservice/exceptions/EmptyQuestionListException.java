package pro.sky.java.course2.examinerservice.exceptions;

/**
 * Исключение при пустой коллекции вопросов.
 */
public class EmptyQuestionListException extends RuntimeException {
    public EmptyQuestionListException(String message) {
        super(message);
    }
}
