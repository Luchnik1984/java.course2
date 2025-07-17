package pro.sky.java.course2.examinerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.EmptyQuestionListException;
import pro.sky.java.course2.examinerservice.exceptions.QuestionAlreadyExistsException;
import pro.sky.java.course2.examinerservice.exceptions.QuestionNotFoundException;

import java.util.*;

/**
 * Сервис для работы с вопросами по Java.
 * Хранит коллекцию вопросов и предоставляет методы для их добавления, удаления и получения.
 */
@Service
public class JavaQuestionService implements QuestionService {
    private final Set<Question> questions = new HashSet<>();
    private final Random random;

    @Autowired
    public JavaQuestionService(Random random) {
        this.random = random;
    }

    @Override
    public Question addQuestion(String question, String answer) {

        Question newQuestion = new Question(question, answer);
        if (questions.contains(newQuestion)) {
            throw new QuestionAlreadyExistsException("Вопрос уже существует");
        }
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question addQuestion(Question question) {
        return addQuestion(question.getQuestion(), question.getAnswer());
    }

    @Override
    public Question removeQuestion(Question question) {

        if (!questions.remove(question)) {
                throw new QuestionNotFoundException("Вопрос не найден: " + question);
        }
        return question;
    }

    @Override
    public Collection<Question> getAllQuestions() {
        return Collections.unmodifiableSet(questions);
    }

    /**
     * Возвращает случайный вопрос из коллекции.
     *
     * @return случайный вопрос
     * @throws EmptyQuestionListException если коллекция вопросов пуста
     */
    @Override
    public Question getRandomQuestion() {
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


