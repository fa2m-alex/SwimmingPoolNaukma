package com.swimmingPool.dao.impl;

import com.swimmingPool.dao.interfaces.CoachDao;
import com.swimmingPool.models.Coach;
import com.swimmingPool.resources.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public class CoachDaoImpl implements CoachDao {

    private static final String DB = Constants.DB;
    private static final String USER = Constants.USER;
    private static final String PASS = Constants.PASS;

    private String insert = "INSERT INTO coach (id, name, surname, birthday) VALUES (NULL, ?, ?, ?)";
    private String getAll = "SELECT * FROM coach";
    private String getById = "SELECT * FROM coach WHERE id = ?";

    public void insert(Coach coach) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(insert);

            preparedStatement.setString(1, coach.getName());
            preparedStatement.setString(2, coach.getSurname());
            preparedStatement.setDate(3, (Date) coach.getBirthday());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Coach> getAll() {
        List<Coach> coaches = new LinkedList<Coach>();
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(getAll);

            Coach coach = null;
            while(resultSet.next()){
                coach = new Coach();
                coach.setId(resultSet.getInt("id"));
                coach.setName(resultSet.getString("name"));
                coach.setSurname(resultSet.getString("surname"));
                coach.setBirthday(resultSet.getDate("birthday"));

                coaches.add(coach);
            }
            resultSet.close();
            statement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coaches;
    }

    public Coach getById(int id) {
        Coach coach = new Coach();
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(getById);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                coach = new Coach();
                coach.setId(resultSet.getInt("id"));
                coach.setName(resultSet.getString("name"));
                coach.setSurname(resultSet.getString("surname"));
                coach.setBirthday(resultSet.getDate("birthday"));
            }

            resultSet.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coach;
    }
}
