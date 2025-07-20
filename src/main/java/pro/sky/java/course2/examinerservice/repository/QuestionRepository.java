package pro.sky.java.course2.examinerservice.repository;

import org.springframework.stereotype.Repository;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.QuestionAlreadyExistsException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Репозиторий для хранения и управления вопросами.
 * Содержит предустановленный набор вопросов при инициализации.
 * Гарантирует уникальность вопросов через Set.
 * Потокобезопасен за счет synchronizedSet.
 */
@Repository
public class QuestionRepository {
    private final Set<Question> questionBank = Collections.synchronizedSet(new HashSet<>());

    /**
     * Инициализирует хранилище предопределенными вопросами.
     */
    public QuestionRepository() {
        initDefaultQuestions();
    }

    private void initDefaultQuestions() {
        questionBank.addAll(List.of(
                new Question("Что такое interface в Java?", "Это абстрактный тип, задающий поведение класса."),
                new Question("Как объявить класс в Java?", "class MyClass {}"),
                new Question("Что такое полиморфизм?", "Возможность объекта принимать разные формы."),
                new Question("Чем отличается ArrayList от LinkedList?", "ArrayList основан на массиве, LinkedList — на списке узлов."),
                new Question("Что такое JVM?", "Виртуальная машина Java, которая исполняет байт-код.")
        ));
    }

    /**
     * Возвращает неизменяемую копию всех вопросов.
     * @return коллекция вопросов
     */
    public Set<Question> getAllQuestions() {
        return Collections.unmodifiableSet(questionBank);
    }

    /**
     * Добавляет вопрос в хранилище.
     * @param question вопрос для добавления
     * @return true если вопрос был добавлен
     * @throws QuestionAlreadyExistsException если вопрос уже существует
     */
    public boolean addQuestion(Question question) {
        if (questionBank.contains(question)) {
            throw new QuestionAlreadyExistsException("Вопрос уже существует: " + question);
        }
        return questionBank.add(question);
    }

    /**
     * Проверяет наличие вопроса в хранилище.
     * @param question вопрос для проверки
     * @return true если вопрос существует
     */
    public boolean contains(Question question) {
        return questionBank.contains(question);
    }

    /**
     * Удаляет вопрос из хранилища.
     * @param question вопрос для удаления
     * @return true если вопрос был удалён, false если не найден
     */
    public boolean removeQuestion(Question question) {
        return questionBank.remove(question);
    }

    /**
     * Ищет вопросы, содержащие указанный текст (без учёта регистра).
     * @param text текст для поиска
     * @return список подходящих вопросов
     */
    public List<Question> findQuestionsContaining(String text) {
        String lowerText = text.toLowerCase();
        return questionBank.stream()
                .filter(q -> q.getQuestion().toLowerCase().contains(lowerText) ||
                        q.getAnswer().toLowerCase().contains(lowerText))
                .collect(Collectors.toList());
    }
}
