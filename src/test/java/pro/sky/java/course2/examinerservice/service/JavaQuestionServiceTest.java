package pro.sky.java.course2.examinerservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.EmptyQuestionListException;
import pro.sky.java.course2.examinerservice.exceptions.QuestionAlreadyExistsException;
import pro.sky.java.course2.examinerservice.exceptions.QuestionNotFoundException;

import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Тесты для {@link JavaQuestionService}.
 * Проверяют добавление, удаление и получение вопросов, включая обработку исключений.
 */
@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {

    @Mock
    private Random random;

    @InjectMocks
    private JavaQuestionService service;

    private final Question testQuestion = new Question("Q1", "A1");

    /**
     * Проверяет, что метод {@link JavaQuestionService#addQuestion(String, String)}
     * корректно добавляет вопрос и возвращает его.
     */
    @Test
    void addQuestion_WithStrings_ShouldAddQuestion() {
        Question added = service.addQuestion("Q1", "A1");
        assertEquals(new Question("Q1", "A1"), added);
        assertTrue(service.getAllQuestions().contains(added));
    }

    /**
     * Проверяет, что метод {@link JavaQuestionService#addQuestion(String, String)}
     * выбрасывает {@link QuestionAlreadyExistsException} при попытке добавить дубликат.
     */
    @Test
    void addQuestion_WithStrings_ShouldThrowIfQuestionExists() {
        service.addQuestion("Q1", "A1");
        assertThrows(QuestionAlreadyExistsException.class,
                () -> service.addQuestion("Q1", "A1"));
    }

    /**
     * Проверяет, что метод {@link JavaQuestionService#addQuestion(Question)}
     * корректно добавляет вопрос и возвращает его.
     */
    @Test
    void addQuestion_WithQuestionObject_ShouldAddQuestion() {
        Question added = service.addQuestion(testQuestion);
        assertEquals(testQuestion, added);
        assertTrue(service.getAllQuestions().contains(testQuestion));
    }

    /**
     * Проверяет, что метод {@link JavaQuestionService#addQuestion(Question)}
     * выбрасывает {@link QuestionAlreadyExistsException} при попытке добавить дубликат.
     */
    @Test
    void addQuestion_WithQuestionObject_ShouldThrowIfQuestionExists() {
        service.addQuestion(testQuestion);
        assertThrows(QuestionAlreadyExistsException.class,
                () -> service.addQuestion(testQuestion));
    }

    /**
     * Проверяет, что метод {@link JavaQuestionService#removeQuestion(Question)}
     * удаляет вопрос и возвращает его.
     */
    @Test
    void removeQuestion_ShouldRemoveQuestion() {
        service.addQuestion(testQuestion);
        Question removedQuestion = service.removeQuestion(testQuestion);
        assertEquals(testQuestion, removedQuestion);
        assertFalse(service.getAllQuestions().contains(testQuestion));
    }

    /**
     * Проверяет, что метод {@link JavaQuestionService#removeQuestion(Question)}
     * выбрасывает {@link QuestionNotFoundException} при попытке удалить несуществующий вопрос.
     */
    @Test
    void removeQuestion_ShouldThrowIfQuestionNotFound() {
        assertThrows(QuestionNotFoundException.class,
                () -> service.removeQuestion(testQuestion));
    }

    /**
     * Проверяет, что метод {@link JavaQuestionService#getAllQuestions()}
     * возвращает неизменяемую коллекцию.
     */
    @Test
    void getAllQuestions_ShouldReturnUnmodifiableCollection() {
        Collection<Question> questions = service.getAllQuestions();
        assertThrows(UnsupportedOperationException.class,
                () -> questions.add(testQuestion));
    }

    /**
     * Проверяет, что метод {@link JavaQuestionService#getRandomQuestion()}
     * корректно возвращает вопрос по заданному случайному индексу.
     * <p>
     * Тест фиксирует поведение {@link Random}, чтобы гарантированно получить вопрос с индексом 1.
     * Это проверяет, что:
     * 1. Метод правильно использует {@link Random#nextInt(int)} для выбора индекса.
     * 2. Возвращается именно тот вопрос, который соответствует сгенерированному индексу.
     */
    @Test
    void getRandomQuestion_ShouldReturnQuestionByIndex() {
        // Фиксируем поведение Random: всегда возвращаем индекс 1
        when(random.nextInt(anyInt())).thenReturn(1);

        // Добавляем два вопроса в сервис
        Question question1 = new Question("Q1", "A1");
        Question question2 = new Question("Q2", "A2");
        service.addQuestion(question1);
        service.addQuestion(question2);

        // Проверяем, что возвращается второй вопрос (индекс 1)
        Question result = service.getRandomQuestion();
        assertEquals(question2, result);
    }

    /**
     * Проверяет, что метод {@link JavaQuestionService#getRandomQuestion()}
     * выбрасывает {@link EmptyQuestionListException} при пустой коллекции.
     */
    @Test
    void getRandomQuestion_ShouldThrowIfListIsEmpty() {
        assertThrows(EmptyQuestionListException.class,
                () -> service.getRandomQuestion()); // Коллекция пуста
    }

}