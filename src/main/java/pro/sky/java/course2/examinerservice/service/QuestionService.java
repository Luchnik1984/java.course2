package pro.sky.java.course2.examinerservice.service;

import pro.sky.java.course2.examinerservice.domain.Question;

import java.util.Collection;

public interface QuestionService {

    Question addQuestion(String question, String answer);

    Question addQuestion(Question question);

    Question removeQuestion(Question question);

    Collection<Question> getAllQuestions();

    Question getRandomQuestion();

}
