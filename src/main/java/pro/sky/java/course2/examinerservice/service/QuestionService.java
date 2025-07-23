package pro.sky.java.course2.examinerservice.service;

import pro.sky.java.course2.examinerservice.domain.Question;

import java.util.Collection;

/**
 * Сервис для работы с вопросами.
 */

public interface QuestionService {

    /**
     * Добавляет вопрос.
     *
     * @param question текст вопроса
     * @param answer   текст ответа
     * @return добавленный вопрос
     */
    Question addQuestion(String question, String answer);

    /**
     * Добавляет готовый объект (готовый вопрос).
     *
     * @param question объект вопроса
     * @return добавленный вопрос
     */
    Question addQuestion(Question question);

    /**
     * Удаляет вопрос.
     *
     * @param question вопрос для удаления
     * @return удаленный вопрос или null, если вопрос не найден
     */
    Question removeQuestion(Question question);

    /**
     * Возвращает все вопросы.
     *
     * @return коллекция вопросов
     */
    Collection<Question> getAllQuestions();

    /**
     * Возвращает случайный вопрос.
     *
     * @return случайный вопрос
     */
    Question getRandomQuestion();

}
