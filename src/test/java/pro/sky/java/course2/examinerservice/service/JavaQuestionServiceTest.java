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

    @Test
    void addQuestion_ShouldAddQuestionWhenNotExists() {
        when(repository.contains(testQuestion)).thenReturn(false);
        when(repository.addQuestion(testQuestion)).thenReturn(true);

        Question result = service.addQuestion(testQuestion);


       assertEquals(testQuestion, result);
        verify(repository).addQuestion(testQuestion);
    }

    @Test
    void addQuestion_ShouldThrowWhenQuestionExists() {
        when(repository.contains(testQuestion)).thenReturn(true);

        assertThrows(QuestionAlreadyExistsException.class,
                () -> service.addQuestion(testQuestion));

        verify(repository, never()).addQuestion(any());
    }

    @Test
    void removeQuestion_ShouldRemoveWhenExists() {
        when(repository.contains(testQuestion)).thenReturn(true);
        when(repository.removeQuestion(testQuestion)).thenReturn(true);

        Question result = service.removeQuestion(testQuestion);

        assertEquals(testQuestion, result);
        verify(repository).removeQuestion(testQuestion);
    }

    @Test
    void removeQuestion_ShouldThrowWhenNotExists() {
        when(repository.contains(testQuestion)).thenReturn(false);

        assertThrows(QuestionNotFoundException.class,
                () -> service.removeQuestion(testQuestion));

        verify(repository, never()).removeQuestion(any());
    }

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