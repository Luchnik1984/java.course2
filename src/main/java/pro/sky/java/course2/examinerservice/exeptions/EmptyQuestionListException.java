package pro.sky.java.course2.examinerservice.exeptions;

public class EmptyQuestionListException extends RuntimeException {
    public EmptyQuestionListException(String message) {
        super(message);
    }
}
