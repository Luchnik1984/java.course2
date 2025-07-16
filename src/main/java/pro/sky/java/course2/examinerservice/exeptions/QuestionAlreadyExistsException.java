package pro.sky.java.course2.examinerservice.exeptions;

/**
 * Исключение при попытке добавить уже существующий вопрос.
 */
public class QuestionAlreadyExistsException extends RuntimeException{
    public QuestionAlreadyExistsException(String massage){
        super(massage);
    }
}
