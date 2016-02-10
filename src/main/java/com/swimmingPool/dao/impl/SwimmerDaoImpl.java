package com.swimmingPool.dao.impl;

import com.swimmingPool.dao.interfaces.SwimmerDao;
import com.swimmingPool.models.Swimmer;
import com.swimmingPool.resources.Constants;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class SwimmerDaoImpl implements SwimmerDao{
    private static final String DB = Constants.DB;
    private static final String USER = Constants.USER;
    private static final String PASS = Constants.PASS;


    public void insert(Swimmer swimmer) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            String query = "INSERT INTO swimmer (id, name, surname, birthday, growth) VALUES (NULL, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, swimmer.getName());
            preparedStatement.setString(2, swimmer.getSurname());
            preparedStatement.setDate(3, (Date) swimmer.getBirthday());
            preparedStatement.setInt(4, swimmer.getGrowth());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Swimmer> getAll() {
        List<Swimmer> swimmers = new LinkedList<Swimmer>();
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            String query = "SELECT * FROM swimmer";
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            Swimmer swimmer = null;
            while(resultSet.next()){
                swimmer = new Swimmer();
                swimmer.setId(resultSet.getInt("id"));
                swimmer.setName(resultSet.getString("name"));
                swimmer.setSurname(resultSet.getString("surname"));
                swimmer.setBirthday(resultSet.getDate("birthday"));
                swimmer.setGrowth(resultSet.getInt("growth"));

                swimmers.add(swimmer);
            }
            resultSet.close();
            statement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return swimmers;
    }
}
