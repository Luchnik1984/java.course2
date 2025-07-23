package pro.sky.java.course2.examinerservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.EmptyQuestionListException;


import pro.sky.java.course2.examinerservice.repository.QuestionRepository;

import java.util.*;

@Service

public class MathQuestionService implements QuestionService{
    private final QuestionRepository repository;
    private final Random random;

    @Autowired
    public MathQuestionService(@Qualifier("mathQuestionRepository") QuestionRepository repository, Random random) {
        this.repository = repository;
        this.random = random;
    }

    @Override
    public Question addQuestion(String question, String answer) {
        return repository.add(new Question(question, answer));
    }

    @Override
    public Question addQuestion(Question question) {
        return addQuestion(question.getQuestion(), question.getAnswer());
    }

    @Override
    public Question removeQuestion(Question question) {
        return repository.remove(question);
    }

    @Override
    public Collection<Question> getAllQuestions() {
        return repository.getAll();
    }


    @Override
    public Question getRandomQuestion() {
        Collection<Question> questions = repository.getAll();
        if (questions.isEmpty()) {
            throw new EmptyQuestionListException("Нет доступных вопросов");
        }
        // 1. Преобразуем Set в массив для быстрого доступа по индексу
        Question[] questionArray = questions.toArray(new Question[0]);

        // 2. Генерируем случайный индекс
        int randomIndex = random.nextInt(questionArray.length);

        // 3. Возвращаем вопрос по индексу
        return questionArray[randomIndex];
    }
}
