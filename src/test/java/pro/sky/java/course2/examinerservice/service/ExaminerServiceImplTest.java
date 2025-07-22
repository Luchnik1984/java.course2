package pro.sky.java.course2.examinerservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.NotEnoughQuestionsException;

import java.util.Collection;
import java.util.HashSet;
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

    private final Question question1 = new Question("Q1", "A1");
    private final Question question2 = new Question("Q2", "A2");


    /**
     * Проверяет генерацию набора уникальных вопросов.
     * Ожидается что:
     * Метод вернет ровно указанное количество вопросов.
     * Все вопросы будут уникальными.
     * Будут использованы методы сервиса вопросов
     */
    @Test
    void getQuestions_ShouldReturnUniqueQuestions() {
        when(questionServiceMock.getAllQuestions())
                .thenReturn(Set.of(question1, question2));
        when(questionServiceMock.getRandomQuestion())
                .thenReturn(question1)
                .thenReturn(question2);

        Collection<Question> result = examinerService.getQuestions(2);

        assertEquals(2, result.size(), "Должно вернуться 2 вопроса");
        assertEquals(2, new HashSet<>(result).size()); // Проверка уникальности
    }

    /**
     * Проверяет обработку ситуации, когда запрашивается больше вопросов, чем есть в сервисе.
     * Ожидается выброс NotEnoughQuestionsException.
     */
    @Test
    void getQuestions_ShouldThrowIfNotEnoughQuestions() {
        when(questionServiceMock.getAllQuestions())
                .thenReturn(Set.of(question1));

        assertThrows(NotEnoughQuestionsException.class,
                () -> examinerService.getQuestions(2));
    }

    /**
     * Проверяет точное соответствие количества возвращаемых вопросов запрошенному.
     */
    @Test
    void getQuestions_ShouldReturnExactAmount() {
        when(questionServiceMock.getAllQuestions())
                .thenReturn(Set.of(question1, question2));
        when(questionServiceMock.getRandomQuestion())
                .thenReturn(question1)
                .thenReturn(question2);

        Collection<Question> result = examinerService.getQuestions(2);

        assertEquals(2, result.size(),"Количество вопросов должно соответствовать запрошенному");
        assertTrue(result.containsAll(Set.of(question1, question2)));
    }
}