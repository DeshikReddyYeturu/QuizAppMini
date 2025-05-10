package com.Deshik.QuizApp.service;
import com.Deshik.QuizApp.dao.QuestionDao;
import com.Deshik.QuizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateQuestion(Integer id, Question updatedQuestion) {
        Optional<Question> existingQuestionOptional = questionDao.findById(id);
        if (existingQuestionOptional.isPresent()) {
            Question existingQuestion = existingQuestionOptional.get();
            if (updatedQuestion.getQuestionTitle() != null) existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
            if (updatedQuestion.getOption1() != null) existingQuestion.setOption1(updatedQuestion.getOption1());
            if (updatedQuestion.getOption2() != null) existingQuestion.setOption2(updatedQuestion.getOption2());
            if (updatedQuestion.getOption3() != null) existingQuestion.setOption3(updatedQuestion.getOption3());
            if (updatedQuestion.getOption4() != null) existingQuestion.setOption4(updatedQuestion.getOption4());
            if (updatedQuestion.getRightAnswer() != null) existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
            if (updatedQuestion.getDifficultyLevel() != null) existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
            if (updatedQuestion.getCategory() != null) existingQuestion.setCategory(updatedQuestion.getCategory());
            questionDao.save(existingQuestion);
            return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        if (questionDao.existsById(id)) {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
