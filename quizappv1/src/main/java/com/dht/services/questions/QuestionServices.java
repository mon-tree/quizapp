/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services.questions;

import com.dht.pojo.Category;
import com.dht.pojo.Level;
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
    public List<Question> getQuestions(String kw, Category c, Level lvl) throws SQLException {
        Connection conn = MyConnectionSingleton.getInstance().connect();

        // B3 -> Thuc thi truy van
        String sql = "SELECT * FROM question WHERE 1=1";
        
        List<Object> params = new ArrayList<>();
        if (kw != null && !kw.isEmpty()) {
            sql += " AND content like concat('%', ?, '%')";
            params.add(kw);
        }
        
        if (c != null) {
            sql += " AND category_id = ?";
            params.add(c.getId());
        }
        
        if (lvl != null) {
            sql += " AND level_id = ?";
            params.add(lvl.getId());
        }
        
        sql += " ORDER BY id DESC";
        
        PreparedStatement stm = conn.prepareCall(sql);
        for (int i = 0; i < params.size(); i++)
            stm.setObject(i + 1, params.get(i));
        
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
