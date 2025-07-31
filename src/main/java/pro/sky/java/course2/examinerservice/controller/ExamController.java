package pro.sky.java.course2.examinerservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.service.ExaminerService;

import java.util.Collection;

/**
 * Контроллер для проведения экзаменов.
 */
@RestController
@RequestMapping("/exam")
public class ExamController {
    private final ExaminerService examinerService;

    public ExamController(ExaminerService examinerService) {
        this.examinerService = examinerService;
    }


    /**
     * Получить вопросы для экзамена.
     *
     * @param amount  количество вопросов (по умолчанию 5)
     * @param subject предмет (по умолчанию java)
     * @return коллекция уникальных вопросов
     */
    @GetMapping("/questions")
    public ResponseEntity<Collection<Question>> getExamQuestions(
            @RequestParam(defaultValue = "5") int amount,
            @RequestParam(defaultValue = "java") String subject) {
        return ResponseEntity.ok(examinerService.getQuestions(amount, subject));
    }
}
