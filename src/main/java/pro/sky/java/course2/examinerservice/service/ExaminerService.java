package pro.sky.java.course2.examinerservice.service;

import pro.sky.java.course2.examinerservice.domain.Question;

import java.util.Collection;

/**
 * Интерфейс для проведения экзамена.
 */
public interface ExaminerService {
    /**
     * Возвращает коллекцию уникальных случайных вопросов.
     *
     * @param amount количество вопросов
     * @return коллекция вопросов
     * @throws IllegalArgumentException если запрашиваемое количество превышает доступное
     */
    Collection<Question> getQuestions(int amount,String subject);
}
