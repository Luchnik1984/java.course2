package pro.sky.java.course2.examinerservice.repository;

import org.junit.jupiter.api.Test;
import pro.sky.java.course2.examinerservice.domain.Question;

import pro.sky.java.course2.examinerservice.exceptions.QuestionNotFoundException;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MathQuestionRepositoryTest {
    private final MathQuestionRepository repository = new MathQuestionRepository();
    private final Question testQuestion = new Question("2+3", "5");

    @Test
    void add_ShouldAddQuestion() {
        Question added = repository.add(testQuestion);
        assertEquals(testQuestion, added);
        Collection<Question> questions = repository.getAll();
        assertTrue(questions.contains(testQuestion));
    }

    @Test
    void remove_ShouldRemoveQuestion() {
        repository.add(testQuestion);
        Question removed = repository.remove(testQuestion);
        assertEquals(testQuestion, removed);
        assertFalse(repository.getAll().contains(testQuestion));
    }

    @Test
    void remove_ShouldThrowWhenQuestionNotFound() {
        assertThrows(QuestionNotFoundException.class, () -> repository.remove(testQuestion));
    }

    @Test
    void getAll_ShouldReturnUnmodifiableCollection() {
        repository.add(testQuestion);
        Collection<Question> questions = repository.getAll();
        assertThrows(UnsupportedOperationException.class,
                () -> questions.add(new Question("5*5", "25")));
    }
}