package pro.sky.java.course2.examinerservice.domain;

import java.util.Objects;

/**
 * Класс, представляющий вопрос и ответ.
 */

public class Question {
    private final String question;
    private final String answer;

    /**
     * Создает новый вопрос.
     *
     * @param question текст вопроса (не может быть null или пустым)
     * @param answer   текст ответа (не может быть null или пустым)
     * @throws IllegalArgumentException если question или answer null/пустые
     */

    public Question(String question, String answer) {
        this.question = Objects.requireNonNull(question, "Вопрос не может быть null");
        this.answer = Objects.requireNonNull(answer, "ответ не может быть null");
        if (this.question.isBlank() || this.answer.isBlank()) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть пустыми");
        }
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return question.equals(question1.question) && answer.equals(question1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
