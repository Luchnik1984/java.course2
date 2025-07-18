package pro.sky.java.course2.examinerservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.service.JavaQuestionService;

import java.util.Collection;

/**
 * Контроллер для работы с вопросами по Java.
 */
@RestController
@RequestMapping("exam/java")
public class JavaQuestionController {
    private final JavaQuestionService service;

    public JavaQuestionController(JavaQuestionService service) {
        this.service = service;
    }

    /**
     * Добавляет новый вопрос.
     *
     * @param question текст вопроса (не может быть пустым)
     * @param answer   текст ответа (не может быть пустым)
     * @return добавленный вопрос (200 OK)
     */
    @PostMapping("/add")
    public Question addQuestion(
            @RequestParam String question,
            @RequestParam String answer) {
        return service.addQuestion(question, answer);
    }


    /**
     * Удаляет вопрос.
     *
     * @param question текст вопроса
     * @param answer   текст ответа
     * @return удаленный вопрос (200 OK) или NOT_FOUND (404) если вопрос не существует
     */
    @DeleteMapping("remove")
    public ResponseEntity<Question> removeQuestion(
            @RequestParam String question,
            @RequestParam String answer) {

        Question removed = service.removeQuestion(new Question(question, answer));
        return removed != null
                ? ResponseEntity.ok(removed) //200
                : ResponseEntity.notFound().build(); //404
    }

    /**
     * Возвращает все вопросы.
     *
     * @return коллекция вопросов (200 OK) или NO_CONTENT (204) если список пуст
     */
    @GetMapping
    public ResponseEntity<Collection<Question>> getAllQuestions() {

        Collection<Question> questions = service.getAllQuestions();
        return questions.isEmpty()
                ? ResponseEntity.noContent().build() // 204
                : ResponseEntity.ok(questions); // 200
    }

    /**
     * Возвращает случайный вопрос.
     *
     * @return случайный вопрос (200 OK) или NO_CONTENT (204) если список пуст
     */
    @GetMapping("/random")
    public ResponseEntity<Question> getRandomQuestion() {
        return ResponseEntity.ok(service.getRandomQuestion());
    }
}
