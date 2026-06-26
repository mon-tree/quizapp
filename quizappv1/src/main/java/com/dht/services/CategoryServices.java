/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services;

import com.dht.pojo.Category;
import com.dht.utils.MyConnectionSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class CategoryServices {

    public List<Category> getCates() throws SQLException {
        Connection conn = MyConnectionSingleton.getInstance().connect();

        // B3 -> Thuc thi truy van
        String sql = "SELECT * FROM category";
        PreparedStatement stm = conn.prepareCall(sql);
        ResultSet rs = stm.executeQuery();
        
        List<Category> cates = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");

            cates.add(new Category(id, name));
        }
        
        return cates;
    }
}
