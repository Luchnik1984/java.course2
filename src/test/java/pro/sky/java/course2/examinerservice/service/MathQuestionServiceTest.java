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
import pro.sky.java.course2.examinerservice.repository.MathQuestionRepository;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {

    @Mock
    MathQuestionRepository repository;

    @Mock
    private Random random;

    @InjectMocks
    private MathQuestionService service;
    private final Question testQuestion = new Question("2+3", "5");

    @Test
    void addQuestion_WithStrings_ShouldDelegateToRepository() {
        when(repository.add(any(Question.class))).thenReturn(testQuestion);
        Question result = service.addQuestion("2+3", "5");
        assertEquals(testQuestion, result);
        verify(repository).add(testQuestion);
    }

    @Test
    void addQuestion_WithStrings_ShouldThrowWhenQuestionExists() {
        when(repository.add(any(Question.class))).thenThrow(QuestionAlreadyExistsException.class);
        assertThrows(QuestionAlreadyExistsException.class, () -> service.addQuestion("2+3", "5"));
    }

    @Test
    void removeQuestion_ShouldDelegateToRepository() {
        when(repository.remove(testQuestion)).thenReturn(testQuestion);
        Question result = service.removeQuestion(testQuestion);
        assertEquals(testQuestion, result);
        verify(repository).remove(testQuestion);
    }

    @Test
    void removeQuestion_ShouldThrowWhenQuestionNotFound() {
        when(repository.remove(testQuestion)).thenThrow(QuestionNotFoundException.class);
        assertThrows(QuestionNotFoundException.class, () -> service.removeQuestion(testQuestion));
    }

    @Test
    void getRandomQuestion_ShouldReturnQuestionByIndex() {
        // Подготовка данных
        Set<Question> questions = new HashSet<>(Set.of(
                new Question("2+3", "5"),
                new Question("5*5", "25")
        ));

        // Настройка моков
        when(repository.getAll()).thenReturn(questions);
        when(random.nextInt(questions.size())).thenReturn(1); // Фиксируем индекс 1

        // Вызов метода
        Question result = service.getRandomQuestion();

        // Проверка
        assertEquals(new Question("5*5", "25"), result);

        // Проверка вызовов
        verify(repository).getAll();
        verify(random).nextInt(questions.size());
    }

    @Test
    void getRandomQuestion_ShouldThrowWhenEmpty() {
        when(repository.getAll()).thenReturn(Set.of());
        assertThrows(EmptyQuestionListException.class, () -> service.getRandomQuestion());
    }

}