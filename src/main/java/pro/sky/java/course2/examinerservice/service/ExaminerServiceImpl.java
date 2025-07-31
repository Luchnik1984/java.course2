package pro.sky.java.course2.examinerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exceptions.NotEnoughQuestionsException;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final Map<String, QuestionService> subjectServices; // Хранит сервисы по ключам "java" и "math"

    @Autowired
    public ExaminerServiceImpl(@Qualifier("javaQuestionService") QuestionService javaService,
                               @Qualifier("mathQuestionService") QuestionService mathService
    ) {
        this.subjectServices = Map.of(
                "java", javaService,
                "math", mathService
        );
    }

    @Override
    public Collection<Question> getQuestions(int amount, String subject) {
        QuestionService service = Optional.ofNullable(subjectServices.get(subject))

                .orElseThrow(() -> new IllegalArgumentException("Предмет не поддерживается: " + subject));

        Collection<Question> allQuestions = service.getAllQuestions();
        System.out.println(service.getAllQuestions().size()); // для проверки
        System.out.println(allQuestions.size());// для проверки
        if (amount > allQuestions.size()) {
            throw new NotEnoughQuestionsException("Запрошено " + amount +
                    " вопросов, из " + allQuestions.size() + " доступных.");
        }
        Set<Question> uniqueQuestions = new HashSet<>();
        while (uniqueQuestions.size() < amount) {
            uniqueQuestions.add(service.getRandomQuestion());
        }
        return uniqueQuestions;
    }
}
