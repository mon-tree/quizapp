/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services.questions;

import com.dht.pojo.Category;
import com.dht.pojo.Question;
import com.dht.utils.MyConnectionSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class QuestionServices {
    public List<Question> getQuestions() throws SQLException {
        Connection conn = MyConnectionSingleton.getInstance().connect();

        String sql = "SELECT * FROM question";
        PreparedStatement stm = conn.prepareCall(sql);
        ResultSet rs = stm.executeQuery();

        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String content = rs.getString("content");

            questions.add(new Question.Builder().setContent(content).setId(id).build());
        }
        
        return questions;
    }
}
