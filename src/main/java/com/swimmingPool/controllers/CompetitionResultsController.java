package com.swimmingPool.controllers;

import com.swimmingPool.App;
import com.swimmingPool.dao.impl.*;
import com.swimmingPool.dao.interfaces.*;
import com.swimmingPool.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Alex on 4/5/2016.
 */
public class CompetitionResultsController {

    @FXML
    private TableView<Competition> tbl_competitions;
    @FXML
    private TableColumn<Competition, String> tc_date;
    @FXML
    private TableColumn<Competition, String> tc_title;

    @FXML
    private TableView<SwimmerInCompetition> tbl_swimmers;
    @FXML
    private TableColumn<SwimmerInCompetition, String> tc_name;
    @FXML
    private TableColumn<SwimmerInCompetition, String> tc_surname;
    @FXML
    private TableColumn<SwimmerInCompetition, String> tc_rating;
    @FXML
    private TableColumn<SwimmerInCompetition, String> tc_time;

    @FXML
    private Button btn_closeCompetition;

    private App mainApp;

    private List<Competition> competitions = null;
    private List<Swimmer> swimmers = null;
    ObservableList<Competition> competitionData = null;
    ObservableList<Swimmer> personData = null;


    private List<CompetitionType> competitionTypes = null;
    private List<CompetitionTable> competitionTables = null;
    private List<Discipline> disciplines = null;

    private CompetitionDao competitionDao;
    private CompetitionTypeDao competitionTypeDao;
    private DisciplineDao disciplineDao;
    private SwimmerDao swimmerDao;
    private CompetitionTableDao competitionTableDao;

    public CompetitionResultsController(){
        competitionDao = new CompetitionDaoImpl();
        competitions = competitionDao.getAll();

        competitionTypeDao = new CompetitionTypeDaoImpl();
        competitionTypes = competitionTypeDao.getAll();

        competitionTableDao = new CompetitionTableDaoImpl();
        competitionTables = competitionTableDao.getAll();

        disciplineDao = new DisciplineDaoImpl();
        disciplines = disciplineDao.getAll();

        swimmerDao = new SwimmerDaoImpl();
        swimmers = swimmerDao.getAll();


        competitionData = FXCollections.observableArrayList();
        for (Competition competition : competitions) {
            competitionData.add(competition);
        }

        personData = FXCollections.observableArrayList();
        for (Swimmer swimmer : swimmers) {
            personData.add(swimmer);
        }
    }
    @FXML
    private void initialize() {
        tc_name.setCellValueFactory(new PropertyValueFactory<SwimmerInCompetition, String>("name"));
        tc_surname.setCellValueFactory(new PropertyValueFactory<SwimmerInCompetition, String>("surname"));
        tc_time.setCellValueFactory(new PropertyValueFactory<SwimmerInCompetition, String>("time"));
        tc_rating.setCellValueFactory(new PropertyValueFactory<SwimmerInCompetition, String>("rating"));

        tc_title.setCellValueFactory(new PropertyValueFactory<Competition, String>("title"));
        tc_date.setCellValueFactory(new PropertyValueFactory<Competition, String>("date"));
        // Clear person details.
        showCompetitionData(null);

        // Listen for selection changes and show the person details when changed.
        tbl_competitions.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> showCompetitionData(newValue));

    }

    public void setMainApp(App mainApp){
        this.mainApp = mainApp;

        // Add observable list data to the table
        tbl_competitions.setItems(competitionData);
    }


    private CompetitionType getTypeByIdFromAll(int id){
        for (CompetitionType competitionType:competitionTypes) {
            if(id == competitionType.getId())
                return competitionType;
        }
        return null;
    }

    private void showCompetitionData(Competition comp) {
        if (comp != null) {
            // Fill the labels with info from the person object.
            List<Swimmer> swimmers = getSwimmersByIdFromAll(comp.getId());
            ObservableList<SwimmerInCompetition> swimmersInComp =  FXCollections.observableArrayList();
            for(Swimmer s:swimmers) {
                SwimmerInCompetition newSwim = new SwimmerInCompetition();
                newSwim.setRating_mark(s.getRating_mark());
                newSwim.setBirthday(s.getBirthday());
                newSwim.setCoach_id(s.getCoach_id());
                newSwim.setGrowth(s.getGrowth());
                newSwim.setId(s.getId());
                newSwim.setName(s.getSurname());
                newSwim.setSurname(s.getName());
                newSwim.setTime(0);
                newSwim.setPlace(0);
                swimmersInComp.add(newSwim);
            }
            tbl_swimmers.setItems(swimmersInComp);
        } else {

        }
    }

    private List<Swimmer> getSwimmersByIdFromAll(int id){
        List<Swimmer> res = new ArrayList<Swimmer>();
        for (CompetitionTable competitionTable:competitionTables) {
            if(id == competitionTable.getCompetition_id())
                res.add(swimmerDao.getById(competitionTable.getSwimmer_id()));
        }
        return res;
    }

    private Discipline getDisciplineByIdFromAll(int id){
        for (Discipline discipline:disciplines) {
            if(id == discipline.getId())
                return discipline;
        }
        return null;
    }
}
