package pro.sky.java.course2.examinerservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.QuestionAlreadyExistsException;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class QuestionRepositoryTest {

    private QuestionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new QuestionRepository();
    }

    @Test
    void getAllQuestions_ShouldReturnUnmodifiableSet() {
        Set<Question> questions = repository.getAllQuestions();

        assertThrows(UnsupportedOperationException.class,
                () -> questions.add(new Question("Новый вопрос", "Ответ")));
    }

    @Test
    void addQuestion_ShouldAddNewQuestion() {
        Question newQuestion = new Question("Что такое Stream API?", "Интерфейс для работы с данными");

        boolean result = repository.addQuestion(newQuestion);

        assertTrue(result);
        assertTrue(repository.getAllQuestions().contains(newQuestion));
    }

    @Test
    void addQuestion_ShouldThrowForDuplicate() {
        // Получаем существующий вопрос из репозитория
        Question existingQuestion = repository.getAllQuestions().iterator().next();

        // Пытаемся добавить его снова
        assertThrows(QuestionAlreadyExistsException.class,
                () -> repository.addQuestion(existingQuestion),
                "При добавлении дубликата должно выбрасываться QuestionAlreadyExistsException");

        // Проверяем, что размер не изменился
        assertEquals(5, repository.getAllQuestions().size(),
                "Количество вопросов не должно измениться после попытки добавить дубликат");
    }

    @Test
    void contains_ShouldReturnTrueForExistingQuestion() {
        Question question = new Question("Что такое interface в Java?", "Это абстрактный тип, задающий поведение класса.");

        assertTrue(repository.contains(question));
    }

    @Test
    void removeQuestion_ShouldRemoveExistingQuestion() {
        Question question = new Question("Что такое interface в Java?", "Это абстрактный тип, задающий поведение класса.");

        boolean result = repository.removeQuestion(question);

        assertTrue(result);
        assertFalse(repository.contains(question));
    }

    @ParameterizedTest
    @MethodSource("provideSearchCases")
    void findQuestionsContaining_ShouldReturnMatchingQuestions(String searchText, int expectedCount) {
        List<Question> result = repository.findQuestionsContaining(searchText);

        assertEquals(expectedCount, result.size());
    }

    private static Stream<Arguments> provideSearchCases() {
        return Stream.of(
                Arguments.of("java", 3),
                Arguments.of("Java", 3),  // Проверка регистронезависимости
                Arguments.of("массив", 1),
                Arguments.of("полиморфизм", 1),
                Arguments.of("несуществующий", 0)
        );
    }

    @Test
    void findQuestionsContaining_ShouldBeCaseInsensitive() {
        List<Question> result = repository.findQuestionsContaining("jAvA");

        assertFalse(result.isEmpty());
    }

    @Test
    void concurrentAccess_ShouldBeThreadSafe() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            repository.addQuestion(new Question("Q1", "A1"));
            repository.addQuestion(new Question("Q2", "A2"));
        });

        Thread thread2 = new Thread(() -> {
            repository.removeQuestion(new Question("Что такое JVM?", "Виртуальная машина Java"));
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertDoesNotThrow(() -> repository.getAllQuestions());
    }
}
