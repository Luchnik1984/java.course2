package pro.sky.java.course2.examinerservice.service;

import pro.sky.java.course2.examinerservice.domain.Question;

import java.util.*;

/**
 * Сервис для работы с вопросами по Java.
 * Хранит коллекцию вопросов и предоставляет методы для их добавления, удаления и получения.
 */
public class JavaQuestionService implements QuestionService {
    private final Set<Question> questions=new HashSet<>();
    private final Random random=new Random();

    public JavaQuestionService() {
        super();
    }

    /**
     * Проверяет валидность текста вопроса и ответа.
     *
     * @param question текст вопроса
     * @param answer   текст ответа
     * @throws IllegalArgumentException если параметры невалидны
     */
    private void validateQuestion(String question, String answer) {
        if (question == null || answer == null) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }
        if (question.isBlank() || answer.isBlank()) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть пустыми");
        }
    }

    @Override
    public Question addQuestion(String question, String answer) {
        validateQuestion(question, answer);
        Question newQuestion = new Question(question, answer);
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question не может быть null");
        }
        validateQuestion(question.getQuestion(), question.getAnswer());
        questions.add(question);
        return question;
    }

    /**
     * Удаляет вопрос из коллекции.
     *
     * @param question вопрос для удаления (должен существовать в коллекции)
     * @return удалённый вопрос, если он был найден; {@code null}, если вопрос не найден
     * @throws IllegalArgumentException если переданный вопрос равен {@code null}
     */
    @Override
    public Question removeQuestion(Question question) {
        return questions.remove(question) ? question : null;
    }

    /**
     * Возвращает неизменяемую копию коллекции всех вопросов.
     *
     * @return коллекция вопросов (не {@code null})
     */
    @Override
    public Collection<Question> getAllQuestions() {
        return Set.copyOf(questions);
    }

    /**
     * Возвращает случайный вопрос из коллекции.
     *
     * @return случайный вопрос
     * @throws NoQuestionsAvailableException если коллекция вопросов пуста
     */
    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new NoQuestionsAvailableException("Нет доступных вопросов");
        }

        // 1. Преобразуем Set в массив для быстрого доступа по индексу
        Question[] questionArray = questions.toArray(new Question[0]);

        // 2. Генерируем случайный индекс
        int randomIndex = random.nextInt(questionArray.length);

        // 3. Возвращаем вопрос по индексу
        return questionArray[randomIndex];
    }
}
