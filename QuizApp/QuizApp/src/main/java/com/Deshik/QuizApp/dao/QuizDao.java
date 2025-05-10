package com.Deshik.QuizApp.dao;

import com.Deshik.QuizApp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
}