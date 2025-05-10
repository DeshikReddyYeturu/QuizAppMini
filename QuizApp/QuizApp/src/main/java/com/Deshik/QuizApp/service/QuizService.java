package com.Deshik.QuizApp.service;

import com.Deshik.QuizApp.dao.*;
import com.Deshik.QuizApp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q : questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
    Optional<Quiz> quizOptional = quizDao.findById(id);
    if (quizOptional.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Quiz quiz = quizOptional.get();
    List<Question> questions = quiz.getQuestions();
    int right = 0;


    Map<Integer, String> questionAnswerMap = new HashMap<>();
    for (Question question : questions) {
        questionAnswerMap.put(question.getId(), question.getRightAnswer());
    }
    for (Response response : responses) {
        Integer questionId = response.getId();
        String userAnswer = response.getResponse();
        if (questionAnswerMap.containsKey(questionId)) {
            String correctAnswer = questionAnswerMap.get(questionId);
            if (userAnswer.equals(correctAnswer)) {
                right++;
            }
        }
    }

    return new ResponseEntity<>(right, HttpStatus.OK);
}
}
