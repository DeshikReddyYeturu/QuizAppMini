package com.Deshik.QuizApp.controller;

import com.Deshik.QuizApp.model.Question;
import com.Deshik.QuizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return  questionService.addQuestion(question);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer id, @RequestBody Question updatedQuestion) {
        return questionService.updateQuestion(id, updatedQuestion);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }

}