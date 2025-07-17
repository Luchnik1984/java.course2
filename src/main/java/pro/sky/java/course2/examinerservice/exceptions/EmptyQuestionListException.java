package pro.sky.java.course2.examinerservice.exceptions;

public class EmptyQuestionListException extends RuntimeException {
    public EmptyQuestionListException(String message) {
        super(message);
    }
}
