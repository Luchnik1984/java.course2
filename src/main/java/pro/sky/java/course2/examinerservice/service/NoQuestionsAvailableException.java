package pro.sky.java.course2.examinerservice.service;

/**
 * Исключение, выбрасываемое при попытке получить случайный вопрос из пустой коллекции.
 */
public class NoQuestionsAvailableException extends RuntimeException {
    /**
     * @param message описание причины ошибки
     */
    public NoQuestionsAvailableException(String message) {
        super(message);
    }
}
