package pro.sky.java.course2.examinerservice.repository;

import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.QuestionAlreadyExistsException;
import pro.sky.java.course2.examinerservice.exceptions.QuestionNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AbstractQuestionRepository implements QuestionRepository {
    protected final Set<Question> questions = new HashSet<>();

    @Override
    public Question add(Question question) {
        if (questions.contains(question)) {
            throw new QuestionAlreadyExistsException("Вопрос уже существует");
        }
        questions.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questions.remove(question)) {
            throw new QuestionNotFoundException("Вопрос не найден");
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableSet(questions);
    }
}

