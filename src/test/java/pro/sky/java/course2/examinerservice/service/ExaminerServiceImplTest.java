package pro.sky.java.course2.examinerservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.NotEnoughQuestionsException;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Тесты для {@link ExaminerServiceImpl}.
 * Проверяют генерацию уникальных вопросов для экзамена.
 */
@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {
    @Mock
    private QuestionService questionServiceMock;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    /**
     * Проверяет, что метод {@link ExaminerServiceImpl#getQuestions(int)}
     * возвращает указанное количество уникальных вопросов.
     */
    @Test
    void getQuestions_ShouldReturnUniqueQuestions() {
        when(questionServiceMock.getAllQuestions()).thenReturn(Set.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2")
        ));
        when(questionServiceMock.getRandomQuestion())
                .thenReturn(new Question("Q1", "A1")) // Первый вызов
                .thenReturn(new Question("Q2", "A2")); // Второй вызов

        Collection<Question> questions = examinerService.getQuestions(2);
        assertEquals(2, questions.size()); // Проверяем количество
        assertTrue(questions.contains(new Question("Q1", "A1"))); // Проверяем содержание
        assertTrue(questions.contains(new Question("Q2", "A2")));
    }

    /**
     * Проверяет, что метод {@link ExaminerServiceImpl#getQuestions(int)}
     * выбрасывает {@link NotEnoughQuestionsException}, если запрошено больше вопросов, чем есть в сервисе.
     */
    @Test
    void getQuestions_ShouldThrowIfNotEnoughQuestions() {
        when(questionServiceMock.getAllQuestions()).thenReturn(Set.of(
                new Question("Q1", "A1") // В коллекции только 1 вопрос
        ));
        assertThrows(NotEnoughQuestionsException.class,
                () -> examinerService.getQuestions(2)); // Запрашиваем 2
    }
}