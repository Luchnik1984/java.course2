package pro.sky.java.course2.examinerservice.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.service.MathQuestionService;

import java.util.Collection;

@RestController
@RequestMapping("/exam/math")
public class MathQuestionController {
    private final MathQuestionService service;

    public MathQuestionController(@Qualifier("mathQuestionService") MathQuestionService service) {
        this.service = service;
    }

    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public Question addQuestion(
            @RequestParam String question,
            @RequestParam String answer) {
        return service.addQuestion(question, answer);
    }

    @RequestMapping(value = "/remove", method = {RequestMethod.GET, RequestMethod.DELETE})
    public ResponseEntity<Question> removeQuestion(
            @RequestParam String question,
            @RequestParam String answer) {

        Question removed = service.removeQuestion(new Question(question, answer));
        return removed != null
                ? ResponseEntity.ok(removed) //200
                : ResponseEntity.notFound().build(); //404
    }

    @GetMapping
    public ResponseEntity<Collection<Question>> getAllQuestions() {

        Collection<Question> questions = service.getAllQuestions();
        return questions.isEmpty()
                ? ResponseEntity.noContent().build() // 204
                : ResponseEntity.ok(questions); // 200
    }

    @GetMapping("/random")
    public ResponseEntity<Question> getRandomQuestion() {
        return ResponseEntity.ok(service.getRandomQuestion());
    }
}
