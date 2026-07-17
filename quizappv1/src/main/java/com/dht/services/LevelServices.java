/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services;

import com.dht.pojo.Level;
import com.dht.utils.MyConnectionSingleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class LevelServices extends QueryServicesBase<Level> {

    @Override
    public PreparedStatement getStm() throws SQLException {
        return MyConnectionSingleton.getInstance().connect().prepareCall("SELECT * FROM level");
    }

    @Override
    public Level getObject(ResultSet rs) throws SQLException {
        return new Level(rs.getInt("id"), rs.getString("name"));
    }
}
