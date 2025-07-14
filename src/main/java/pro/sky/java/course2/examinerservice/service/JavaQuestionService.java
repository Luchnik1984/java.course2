package pro.sky.java.course2.examinerservice.service;

import pro.sky.java.course2.examinerservice.domain.Question;

import java.util.*;

public class JavaQuestionService implements QuestionService {
    private final Set<Question> questions=new HashSet<>();
    private final Random random=new Random();

    public JavaQuestionService() {
        super();
    }

    @Override
    public Question addQuestion(String question, String answer) {
        if (question == null || answer == null) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }
        if (question.isBlank() || answer.isBlank()) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть пустыми или содержать только пробелы");
        }
        Question newQuestion = new Question(question, answer);
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question не может быть null");
        }
        questions.add(question);
        return question;
    }

    @Override
    public Question removeQuestion(Question question) {
        if (questions.remove(question)){
            return question;
        }
        return null;
    }

    @Override
    public Collection<Question> getAllQuestions() {
        return Set.copyOf(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()){
            return null;
        }
        int index=random.nextInt(questions.size());
        return questions.stream()
                .skip(index)
                .findFirst()
                .orElseThrow();
    }
}
