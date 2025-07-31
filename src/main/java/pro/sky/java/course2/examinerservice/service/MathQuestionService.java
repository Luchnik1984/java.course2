package pro.sky.java.course2.examinerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pro.sky.java.course2.examinerservice.repository.QuestionRepository;

import java.util.*;

@Service

public class MathQuestionService extends AbstractQuestionService {

    @Autowired
    public MathQuestionService(
            @Qualifier("mathQuestionRepository") QuestionRepository repository, Random random) {
        super(repository, random);
    }
}
