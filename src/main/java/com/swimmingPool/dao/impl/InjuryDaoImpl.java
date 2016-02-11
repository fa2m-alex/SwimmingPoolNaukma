package com.swimmingPool.dao.impl;

import com.swimmingPool.dao.interfaces.InjuryDao;
import com.swimmingPool.models.Injury;
import com.swimmingPool.resources.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public class InjuryDaoImpl implements InjuryDao {

    private static final String DB = Constants.DB;
    private static final String USER = Constants.USER;
    private static final String PASS = Constants.PASS;

    private String insert = "INSERT INTO injury (id, swimmer_id, title, date) VALUES (NULL, ?, ?, ?)";
    private String getAll = "SELECT * FROM injury";

    public void insert(Injury injury) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(insert);

            preparedStatement.setInt(1, injury.getSwimmer_id());
            preparedStatement.setString(2, injury.getTitle());
            preparedStatement.setDate(3, (Date) injury.getDate());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Injury> getAll() {
        List<Injury> injuries = new LinkedList<Injury>();
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(getAll);

            Injury injury = null;
            while(resultSet.next()){
                injury = new Injury();
                injury.setId(resultSet.getInt("id"));
                injury.setSwimmer_id(resultSet.getInt("swimmer_id"));
                injury.setTitle(resultSet.getString("title"));
                injury.setDate(resultSet.getDate("date"));

                injuries.add(injury);
            }
            resultSet.close();
            statement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return injuries;
    }
}
