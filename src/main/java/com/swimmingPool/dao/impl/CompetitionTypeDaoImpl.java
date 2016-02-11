package com.swimmingPool.dao.impl;

import com.swimmingPool.dao.interfaces.CompetitionTypeDao;
import com.swimmingPool.models.CompetitionType;
import com.swimmingPool.resources.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public class CompetitionTypeDaoImpl implements CompetitionTypeDao {

    private static final String DB = Constants.DB;
    private static final String USER = Constants.USER;
    private static final String PASS = Constants.PASS;

    private String insert = "INSERT INTO competition_type (id, type) VALUES (NULL, ?)";
    private String getAll = "SELECT * FROM competition_type";

    public void insert(CompetitionType competitionType) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(insert);

            preparedStatement.setString(1, competitionType.getType());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CompetitionType> getAll() {
        List<CompetitionType> competitionTypes = new LinkedList<CompetitionType>();
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(getAll);

            CompetitionType competitionType = null;
            while(resultSet.next()){
                competitionType = new CompetitionType();
                competitionType.setId(resultSet.getInt("id"));
                competitionType.setType(resultSet.getString("type"));

                competitionTypes.add(competitionType);
            }
            resultSet.close();
            statement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return competitionTypes;
    }
}
