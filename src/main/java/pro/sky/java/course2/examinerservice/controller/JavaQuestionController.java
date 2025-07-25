package pro.sky.java.course2.examinerservice.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course2.examinerservice.service.JavaQuestionService;

/**
 * Контроллер для работы с вопросами по Java.
 */
@RestController
@RequestMapping("exam/java")
public class JavaQuestionController extends AbstractQuestionController {

    public JavaQuestionController(@Qualifier("javaQuestionService") JavaQuestionService service) {
        super(service);
    }
}


