package pro.sky.java.course2.examinerservice.service;

import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock

    private QuestionService javaQuestionService;

    @Mock

    private QuestionService mathQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final Question javaQuestion1 = new Question("Q1", "A1");
    private final Question javaQuestion2 = new Question("Q2", "A2");
    private final Question mathQuestion1 = new Question("2+2", "4");
    private final Question mathQuestion2 = new Question("3*3", "9");

    @BeforeEach
    void setUp() {
        reset(mathQuestionService, javaQuestionService);
    }


    @Test
    void getQuestions_JavaSubject_ReturnsUniqueQuestions() {
        // Подготовка данных
        when(javaQuestionService.getAllQuestions()).thenReturn(Set.of(javaQuestion1, javaQuestion2));
//        System.out.println("Mock getAllQuestions: " + javaQuestionService.getAllQuestions());
        when(javaQuestionService.getRandomQuestion())
                .thenReturn(javaQuestion1)
                .thenReturn(javaQuestion2);
//        System.out.println("Mock getAllQuestions: " + javaQuestionService.getRandomQuestion());
        // Вызов метода
        Collection<Question> result = examinerService.getQuestions(2, "java");

        // Проверка
        assertEquals(2, result.size());
        assertTrue(result.contains(javaQuestion1));
        assertTrue(result.contains(javaQuestion2));
        verify(javaQuestionService, times(2)).getRandomQuestion();
    }

    @Test
    void getQuestions_MathSubject_ReturnsUniqueQuestions() {
        when(mathQuestionService.getAllQuestions()).thenReturn(Set.of(mathQuestion1, mathQuestion2));
//       System.out.println("Mock getAllQuestions: " + mathQuestionService.getAllQuestions().size());
        when(mathQuestionService.getRandomQuestion())
                .thenReturn(mathQuestion1)
                .thenReturn(mathQuestion2);
//       System.out.println("Mock getAllQuestions: " + mathQuestionService.getRandomQuestion());

        Collection<Question> result = examinerService.getQuestions(2, "math");

        assertEquals(2, result.size());
        assertTrue(result.contains(mathQuestion1));
        assertTrue(result.contains(mathQuestion2));
        verify(mathQuestionService, times(2)).getRandomQuestion();
    }

    @Test
    void getQuestions_NotEnoughQuestions_ThrowsException() {
        when(javaQuestionService.getAllQuestions()).thenReturn(Set.of(javaQuestion1));

        assertThrows(NotEnoughQuestionsException.class,
                () -> examinerService.getQuestions(2, "java"));
    }

    @Test
    void getQuestions_InvalidSubject_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> examinerService.getQuestions(1, "physics"));
    }
}