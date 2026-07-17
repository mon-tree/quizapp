/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services.exams;

import com.dht.pojo.Question;
import com.dht.pojo.QuestionQueryBuilder;
import com.dht.services.questions.QuestionFacade;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public class SpecificExam implements ExamStrategy {
    private int num;

    public SpecificExam(int num) {
        this.num = num;
    }
    
    public SpecificExam(String num) {
        this(Integer.parseInt(num));
    }

    @Override
    public List<Question> getQuestions() throws SQLException {
        QuestionQueryBuilder q = new QuestionQueryBuilder().setLimit(this.num).setOrderBy("rand()");
        
        return QuestionFacade.getLazyQuestions(q);
    }
    
}
