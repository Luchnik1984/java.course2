package pro.sky.java.course2.examinerservice.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course2.examinerservice.service.MathQuestionService;

/**
 * Контроллер для работы с вопросами по математике.
 */
@RestController
@RequestMapping("/exam/math")
public class MathQuestionController extends AbstractQuestionController {


    public MathQuestionController(@Qualifier("mathQuestionService") MathQuestionService service) {
        super(service);
    }
}

