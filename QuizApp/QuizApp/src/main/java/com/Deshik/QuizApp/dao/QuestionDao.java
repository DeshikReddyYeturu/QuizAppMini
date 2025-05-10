package com.Deshik.QuizApp.dao;
import com.Deshik.QuizApp.model.Question;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);
}