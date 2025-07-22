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

    /**
     * Тестируемый экземпляр репозитория.
     * Инициализируется перед каждым тестом.
     */
    private QuestionRepository repository;

    /**
     * Инициализация тестового окружения перед каждым тестом.
     * Создает новый экземпляр репозитория с предустановленными вопросами.
     */
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

    /**
     * Проверяет успешное добавление нового уникального вопроса.
     */
    @Test
    void addQuestion_ShouldAddNewQuestion() {
        Question newQuestion = new Question("Новый вопрос", "Ответ");
        int initialSize = repository.getAllQuestions().size();

        boolean result = repository.addQuestion(newQuestion);

        assertTrue(result, "Метод должен вернуть true при успешном добавлении");
        assertTrue(repository.getAllQuestions().contains(newQuestion),
                "Добавленный вопрос должен присутствовать в репозитории");
        assertEquals(initialSize + 1, repository.getAllQuestions().size(),
                "Количество вопросов должно увеличиться на 1");
    }

    /**
     * Проверяет обработку попытки добавления дубликата вопроса.
     * Ожидается, что при попытке добавить уже существующий вопрос:
     * Будет выброшено QuestionAlreadyExistsException.
     * Размер хранилища не изменится.
     */
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

    /**
     * Проверяет удаление существующего вопроса.
     * Тест проверяет что:
     * Метод возвращает true.
     * Вопрос удаляется из хранилища.
     * Общее количество вопросов уменьшается на 1
     */
    @Test
    void removeQuestion_ShouldRemoveExistingQuestion() {
        Question question = repository.getAllQuestions().iterator().next();
        int initialSize = repository.getAllQuestions().size();

        boolean result = repository.removeQuestion(question);

        assertTrue(result, "Метод должен вернуть true при успешном удалении");
        assertFalse(repository.getAllQuestions().contains(question),
                "Удаленный вопрос не должен присутствовать в репозитории");
        assertEquals(initialSize - 1, repository.getAllQuestions().size(),
                "Количество вопросов должно уменьшиться на 1");
    }

    /**
     * Проверяет попытку удаления несуществующего вопроса.
     * Ожидается что:
     * Метод вернет false.
     * Количество вопросов не изменится.
     */
    @Test
    void removeQuestion_ShouldReturnFalseForNonExistingQuestion() {
        Question nonExisting = new Question("Несуществующий", "Вопрос");
        int initialSize = repository.getAllQuestions().size();

        boolean result = repository.removeQuestion(nonExisting);

        assertFalse(result, "Метод должен вернуть false для несуществующего вопроса");
        assertEquals(initialSize, repository.getAllQuestions().size(),
                "Количество вопросов не должно измениться");
    }

    /**
     * Параметризованный тест для проверки поиска вопросов.
     * Проверяет корректность работы метода findQuestionsContaining()
     * с различными вариантами поисковых запросов.
     * @param searchText текст для поиска
     * @param expectedCount ожидаемое количество найденных вопросов
     */
    @ParameterizedTest
    @MethodSource("provideSearchCases")
    void findQuestionsContaining_ShouldReturnMatchingQuestions(String searchText, int expectedCount) {
        List<Question> result = repository.findQuestionsContaining(searchText);

        assertEquals(expectedCount, result.size(),
                "Количество найденных вопросов не соответствует ожидаемому");
    }

    /**
     * Провайдер тестовых данных для параметризованного теста поиска.
     *
     * @return поток аргументов в формате: (текст для поиска, ожидаемое количество результатов)
     */
    private static Stream<Arguments> provideSearchCases() {
        return Stream.of(
                Arguments.of("java", 3),
                Arguments.of("Java", 3),  // Проверка регистронезависимости
                Arguments.of("массив", 1),
                Arguments.of("полиморфизм", 1),
                Arguments.of("несуществующий", 0)
        );
    }

    /**
     * Проверяет регистронезависимость поиска вопросов.
     * Ожидается, что поиск должен находить одни и те же вопросы
     * независимо от регистра введенного текста.
     */
    @Test
    void findQuestionsContaining_ShouldBeCaseInsensitive() {
        List<Question> result = repository.findQuestionsContaining("jAvA");

        assertFalse(result.isEmpty(),
                "Поиск должен находить вопросы независимо от регистра");
    }

}
