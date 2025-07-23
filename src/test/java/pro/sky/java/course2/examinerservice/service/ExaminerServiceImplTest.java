package pro.sky.java.course2.examinerservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.NotEnoughQuestionsException;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
   // @Qualifier("javaQuestionService")
    private QuestionService javaQuestionService;

    @Mock
   // @Qualifier("mathQuestionService")
    private QuestionService mathQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final Question javaQuestion1 = new Question("Q1", "A1");
    private final Question javaQuestion2 = new Question("Q2", "A2");
    private final Question mathQuestion1 = new Question("2+2", "4");
    private final Question mathQuestion2 = new Question("3*3", "9");

//    @BeforeEach
//    void setUp() {
//        // Базовая настройка моков
//        when(javaQuestionService.getAllQuestions()).thenReturn(Set.of(javaQuestion1, javaQuestion2));
//        when(mathQuestionService.getAllQuestions()).thenReturn(Set.of(mathQuestion1, mathQuestion2));
//    }

    @Test
    void getQuestions_ShouldReturnJavaQuestions_WhenJavaSubject() {
        // Arrange
        when(javaQuestionService.getRandomQuestion())
                .thenReturn(javaQuestion1)
                .thenReturn(javaQuestion2);

        // Act
        Collection<Question> result = examinerService.getQuestions(2, "java");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(javaQuestion1));
        assertTrue(result.contains(javaQuestion2));
        verify(javaQuestionService, times(2)).getRandomQuestion();
    }

    @Test
    void getQuestions_ShouldReturnMathQuestions_WhenMathSubject() {
        // Arrange
        when(mathQuestionService.getRandomQuestion())
                .thenReturn(mathQuestion1)
                .thenReturn(mathQuestion2);

        // Act
        Collection<Question> result = examinerService.getQuestions(2, "math");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(mathQuestion1));
        assertTrue(result.contains(mathQuestion2));
        verify(mathQuestionService, times(2)).getRandomQuestion();
    }

    @Test
    void getQuestions_ShouldThrowNotEnoughQuestionsException_WhenNotEnoughQuestions() {
        // Arrange
        when(javaQuestionService.getAllQuestions()).thenReturn(Set.of(javaQuestion1));

        // Act & Assert
        NotEnoughQuestionsException exception = assertThrows(
                NotEnoughQuestionsException.class,
                () -> examinerService.getQuestions(2, "java")
        );

        assertEquals("Запрошено 2 вопросов, из 1 доступных.", exception.getMessage());
    }

    @Test
    void getQuestions_ShouldThrowIllegalArgumentException_WhenInvalidSubject() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> examinerService.getQuestions(1, "physics")
        );

        assertEquals("Предмет не поддерживается: physics", exception.getMessage());
    }

    @Test
    void getQuestions_ShouldReturnEmptyCollection_WhenZeroAmount() {
        // Act
        Collection<Question> result = examinerService.getQuestions(0, "java");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getQuestions_ShouldHandleDuplicates_WhenRandomServiceReturnsSameQuestion() {
        // Arrange
        when(javaQuestionService.getRandomQuestion())
                .thenReturn(javaQuestion1)
                .thenReturn(javaQuestion1) // Дубликат
                .thenReturn(javaQuestion2);

        // Act
        Collection<Question> result = examinerService.getQuestions(2, "java");

        // Assert
        assertEquals(2, result.size());
        verify(javaQuestionService, times(3)).getRandomQuestion();
    }

    @Test
    void getQuestions_ShouldReturnUniqueQuestions_WhenEnoughAvailable() {
        // Arrange
        when(javaQuestionService.getAllQuestions()).thenReturn(Set.of(javaQuestion1, javaQuestion2));
        when(javaQuestionService.getRandomQuestion())
                .thenReturn(javaQuestion1)
                .thenReturn(javaQuestion2);

        // Act
        Collection<Question> result = examinerService.getQuestions(2, "java");

        // Assert
        assertEquals(2, result.size());
        assertEquals(2, result.stream().distinct().count());
    }
}