package com.swimmingPool.dao.impl;

import com.swimmingPool.dao.interfaces.SwimmerDao;
import com.swimmingPool.models.Swimmer;
import com.swimmingPool.resources.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SwimmerDaoImpl implements SwimmerDao {

    private static final String DB = Constants.DB;
    private static final String USER = Constants.USER;
    private static final String PASS = Constants.PASS;

    private String insert = "INSERT INTO swimmer (id, name, surname, birthday, growth, coach_id) VALUES (NULL, ?, ?, ?, ?, ?)";
    private String getAll = "SELECT * FROM swimmer";
    private String getById = "SELECT * FROM swimmer WHERE id = ?";

    public void insert(Swimmer swimmer) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(insert);

            preparedStatement.setString(1, swimmer.getName());
            preparedStatement.setString(2, swimmer.getSurname());
            preparedStatement.setDate(3, (Date) swimmer.getBirthday());
            preparedStatement.setInt(4, swimmer.getGrowth());
            preparedStatement.setInt(5, swimmer.getCoach_id());

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

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(getAll);

            Swimmer swimmer = null;
            while(resultSet.next()){
                swimmer = new Swimmer();
                swimmer.setId(resultSet.getInt("id"));
                swimmer.setName(resultSet.getString("name"));
                swimmer.setSurname(resultSet.getString("surname"));
                swimmer.setBirthday(resultSet.getDate("birthday"));
                swimmer.setGrowth(resultSet.getInt("growth"));
                swimmer.setCoach_id(resultSet.getInt("coach_id"));

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

    public Swimmer getById(int id){
        Swimmer swimmer = new Swimmer();
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(getById);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                swimmer = new Swimmer();
                swimmer.setId(resultSet.getInt("id"));
                swimmer.setName(resultSet.getString("name"));
                swimmer.setSurname(resultSet.getString("surname"));
                swimmer.setBirthday(resultSet.getDate("birthday"));
                swimmer.setGrowth(resultSet.getInt("growth"));
                swimmer.setCoach_id(resultSet.getInt("coach_id"));
            }

            resultSet.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return swimmer;
    }
}
