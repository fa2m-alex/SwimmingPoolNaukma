package com.swimmingPool.dao.impl;

import com.swimmingPool.dao.interfaces.CompetitionTableDao;
import com.swimmingPool.models.CompetitionTable;
import com.swimmingPool.resources.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public class CompetitionTableDaoImpl implements CompetitionTableDao {

    private static final String DB = Constants.DB;
    private static final String USER = Constants.USER;
    private static final String PASS = Constants.PASS;

    private String insert = "INSERT INTO competition_table (id, competition_id, swimmer_id) VALUES (NULL, ?, ?)";
    private String getAll = "SELECT * FROM competition_type";

    public void insert(CompetitionTable competitionTable) {
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            PreparedStatement preparedStatement = conn.prepareStatement(insert);

            preparedStatement.setInt(1, competitionTable.getCompetition_id());
            preparedStatement.setInt(2, competitionTable.getSwimmer_id());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CompetitionTable> getAll() {
        List<CompetitionTable> competitionTables = new LinkedList<CompetitionTable>();
        try {
            Class.forName ("com.mysql.jdbc.Driver") ;
            Connection conn = DriverManager.getConnection(DB, USER, PASS);

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(getAll);

            CompetitionTable competitionTable = null;
            while(resultSet.next()){
                competitionTable = new CompetitionTable();
                competitionTable.setId(resultSet.getInt("id"));
                competitionTable.setCompetition_id(resultSet.getInt("competition_id"));
                competitionTable.setCompetition_id(resultSet.getInt("swimmer_id"));

                competitionTables.add(competitionTable);
            }
            resultSet.close();
            statement.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return competitionTables;
    }
}
