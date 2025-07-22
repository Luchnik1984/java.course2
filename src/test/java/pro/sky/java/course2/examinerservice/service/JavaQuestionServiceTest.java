package pro.sky.java.course2.examinerservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.QuestionAlreadyExistsException;
import pro.sky.java.course2.examinerservice.exceptions.QuestionNotFoundException;
import pro.sky.java.course2.examinerservice.repository.QuestionRepository;

import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для {@link JavaQuestionService}.
 * Проверяют добавление, удаление и получение вопросов, включая обработку исключений.
 */
@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {

    @Mock
    private QuestionRepository repository;

    @Mock
    private Random random;

    @InjectMocks
    private JavaQuestionService service;

    private final Question testQuestion = new Question("Q1", "A1");

    /**
     * Проверяет успешное добавление нового вопроса.
     * Ожидается что:
     * Метод вернет добавленный вопрос.
     * Будет вызван метод репозитория addQuestion().
     */
    @Test
    void addQuestion_ShouldAddQuestionWhenNotExists() {
        when(repository.contains(testQuestion)).thenReturn(false);
        when(repository.addQuestion(testQuestion)).thenReturn(true);

        Question result = service.addQuestion(testQuestion);


       assertEquals(testQuestion, result, "Метод должен вернуть добавленный вопрос");
        verify(repository).addQuestion(testQuestion);
    }

    /**
     * Проверяет обработку попытки добавления дубликата вопроса.
     * Ожидается выброс QuestionAlreadyExistsException.
     */
    @Test
    void addQuestion_ShouldThrowWhenQuestionExists() {
        when(repository.contains(testQuestion)).thenReturn(true);

        assertThrows(QuestionAlreadyExistsException.class,
                () -> service.addQuestion(testQuestion));

        verify(repository, never()).addQuestion(any());
    }

    /**
     * Проверяет успешное удаление существующего вопроса.
     * Ожидается что:
     * Метод вернет удаленный вопрос.
     * Будет вызван метод репозитория removeQuestion()
     */
    @Test
    void removeQuestion_ShouldRemoveWhenExists() {
        when(repository.contains(testQuestion)).thenReturn(true);
        when(repository.removeQuestion(testQuestion)).thenReturn(true);

        Question result = service.removeQuestion(testQuestion);

        assertEquals(testQuestion, result);
        verify(repository).removeQuestion(testQuestion);
    }

    /**
     * Проверяет обработку попытки удаления несуществующего вопроса.
     * Ожидается выброс QuestionNotFoundException.
     */
    @Test
    void removeQuestion_ShouldThrowWhenNotExists() {
        when(repository.contains(testQuestion)).thenReturn(false);

        assertThrows(QuestionNotFoundException.class,
                () -> service.removeQuestion(testQuestion));

        verify(repository, never()).removeQuestion(any());
    }

    /**
     * Проверяет получение случайного вопроса.
     * Ожидается что:
     * Будут вызваны методы репозитория getAllQuestions()
     * Будет использован Random для выбора вопроса
     * Метод вернет корректный вопрос
     */
    @Test
    void getRandomQuestion_ShouldReturnQuestion() {
        Set<Question> questions = Set.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2")
        );
        when(repository.getAllQuestions()).thenReturn(questions);
        when(random.nextInt(questions.size())).thenReturn(0);

        Question result = service.getRandomQuestion();

        assertNotNull(result);
        verify(repository).getAllQuestions();
        verify(random).nextInt(questions.size());
    }
}