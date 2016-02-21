package com.swimmingPool.dao.impl;

import com.swimmingPool.dao.interfaces.DisciplineDao;
import com.swimmingPool.models.Discipline;
import com.swimmingPool.resources.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public class DisciplineDaoImpl implements DisciplineDao {
    private static final String DB = Constants.DB;
    private static final String USER = Constants.USER;
    private static final String PASS = Constants.PASS;

    private String insert = "INSERT INTO discipline (id, title) VALUES (NULL, ?)";
    private String getAll = "SELECT * FROM discipline";
    private String update = "UPDATE discipline SET title=? WHERE id=?";
    private String delete = "DELETE FROM discipline WHERE id=?";

    public void insert(Discipline discipline) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(insert);

            preparedStatement.setString(1, discipline.getTitle());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Discipline> getAll() {
        List<Discipline> disciplines = new LinkedList<Discipline>();
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(getAll);

            Discipline discipline = null;
            while(resultSet.next()){
                discipline = new Discipline();
                discipline.setId(resultSet.getInt("id"));
                discipline.setTitle(resultSet.getString("title"));

                disciplines.add(discipline);
            }
            resultSet.close();
            statement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disciplines;
    }

    public void update(Discipline discipline) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(update);

            preparedStatement.setString(1, discipline.getTitle());
            preparedStatement.setInt(2, discipline.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Discipline discipline) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(delete);

            preparedStatement.setInt(1, discipline.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
