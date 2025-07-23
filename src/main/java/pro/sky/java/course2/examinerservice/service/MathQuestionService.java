package pro.sky.java.course2.examinerservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.EmptyQuestionListException;
import pro.sky.java.course2.examinerservice.exceptions.QuestionAlreadyExistsException;
import pro.sky.java.course2.examinerservice.exceptions.QuestionNotFoundException;

import java.util.*;

@Service
public class MathQuestionService implements QuestionService{
    private final Set<Question> questions = new HashSet<>();
    private final Random random;

    @Autowired
    public MathQuestionService(Random random) {
        this.random = random;
    }

    @Override
    public Question addQuestion(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        if (questions.contains(newQuestion)) {
            throw new QuestionAlreadyExistsException("Вопрос уже существует");
        }
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question addQuestion(Question question) {
        return addQuestion(question.getQuestion(), question.getAnswer());
    }


    @Override
    public Question removeQuestion(Question question) {
        if (!questions.remove(question)) {
            throw new QuestionNotFoundException("Вопрос не найден: " + question);
        }
        return question;
    }

    @Override
    public Collection<Question> getAllQuestions() {
        return Collections.unmodifiableSet(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new EmptyQuestionListException("Нет доступных вопросов");
        }
        Question[] questionArray = questions.toArray(new Question[0]);
        int randomIndex = random.nextInt(questionArray.length);
        return questionArray[randomIndex];
    }
}
