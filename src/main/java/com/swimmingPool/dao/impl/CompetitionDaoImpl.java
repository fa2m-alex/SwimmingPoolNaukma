package com.swimmingPool.dao.impl;

import com.swimmingPool.dao.interfaces.CompetitionDao;
import com.swimmingPool.models.Competition;
import com.swimmingPool.resources.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public class CompetitionDaoImpl implements CompetitionDao {

    private static final String DB = Constants.DB;
    private static final String USER = Constants.USER;
    private static final String PASS = Constants.PASS;

    private String insert = "INSERT INTO competition (id, type_id, discipline_id, title, date) VALUES (NULL, ?, ?, ?, ?)";
    private String getAll = "SELECT * FROM competition";
    private String update = "UPDATE competition SET type_id=?, discipline_id=?, title=?, date=? WHERE id=?";
    private String delete = "DELETE FROM competition WHERE id=?";

    public void insert(Competition competition) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(insert);

            preparedStatement.setInt(1, competition.getType_id());
            preparedStatement.setInt(2, competition.getDiscipline_id());
            preparedStatement.setString(3, competition.getTitle());
            preparedStatement.setDate(4, (Date) competition.getDate());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Competition> getAll() {
        List<Competition> competitions = new LinkedList<Competition>();
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(getAll);

            Competition competition = null;
            while(resultSet.next()){
                competition = new Competition();
                competition.setId(resultSet.getInt("id"));
                competition.setType_id(resultSet.getInt("type_id"));
                competition.setDiscipline_id(resultSet.getInt("discipline_id"));
                competition.setTitle(resultSet.getString("title"));
                competition.setDate(resultSet.getDate("date"));

                competitions.add(competition);
            }
            resultSet.close();
            statement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return competitions;
    }

    public void update(Competition competition) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(update);

            preparedStatement.setInt(1, competition.getType_id());
            preparedStatement.setInt(2, competition.getDiscipline_id());
            preparedStatement.setString(3, competition.getTitle());
            preparedStatement.setDate(4, (Date) competition.getDate());
            preparedStatement.setInt(5, competition.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Competition competition) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(delete);

            preparedStatement.setInt(1, competition.getId());

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
