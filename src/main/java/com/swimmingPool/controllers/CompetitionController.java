package com.swimmingPool.controllers;

import com.swimmingPool.App;
import com.swimmingPool.dao.impl.CompetitionDaoImpl;
import com.swimmingPool.dao.impl.CompetitionTypeDaoImpl;
import com.swimmingPool.dao.impl.DisciplineDaoImpl;
import com.swimmingPool.dao.interfaces.CompetitionDao;
import com.swimmingPool.dao.interfaces.CompetitionTypeDao;
import com.swimmingPool.dao.interfaces.DisciplineDao;
import com.swimmingPool.models.Competition;
import com.swimmingPool.models.CompetitionType;
import com.swimmingPool.models.Discipline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * Created by Alex on 01.04.2016.
 */
public class CompetitionController {

    @FXML
    private TableView<Competition> competitionTable;
    @FXML
    private TableColumn<Competition, String> titleColumn;

    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label disciplineLabel;


    // Reference to the main application.
    private App mainApp;

    private List<Competition> competitions = null;
    ObservableList<Competition> personData = null;

    private List<CompetitionType> competitionTypes = null;
    private List<Discipline> disciplines = null;

    private CompetitionDao competitionDao;
    private CompetitionTypeDao competitionTypeDao;
    private DisciplineDao disciplineDao;

    public CompetitionController(){
        competitionDao = new CompetitionDaoImpl();
        competitions = competitionDao.getAll();

        competitionTypeDao = new CompetitionTypeDaoImpl();
        competitionTypes = competitionTypeDao.getAll();

        disciplineDao = new DisciplineDaoImpl();
        disciplines = disciplineDao.getAll();

        personData = FXCollections.observableArrayList();
        for (Competition competition : competitions) {
            personData.add(competition);
        }
    }

    @FXML
    private void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<Competition, String>("title"));

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        competitionTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

    }

    public void setMainApp(App mainApp){
        this.mainApp = mainApp;

        // Add observable list data to the table
        competitionTable.setItems(personData);
    }

    private void showPersonDetails(Competition competition) {
        if (competition != null) {
            // Fill the labels with info from the person object.
            titleLabel.setText(competition.getTitle());
            dateLabel.setText(competition.getDate().toString());

            if(competition.getType_id() != 0)
                typeLabel.setText(getTypeByIdFromAll(competition.getType_id()).getType());
            else
                typeLabel.setText("");

            if(competition.getDiscipline_id() != 0)
                disciplineLabel.setText(getDisciplineByIdFromAll(competition.getDiscipline_id()).getTitle());
            else
                disciplineLabel.setText("");

        } else {
            // Person is null, remove all the text.
            titleLabel.setText("");
            dateLabel.setText("");
            typeLabel.setText("");
            disciplineLabel.setText("");
        }
    }

    private CompetitionType getTypeByIdFromAll(int id){
        for (CompetitionType competitionType:competitionTypes) {
            if(id == competitionType.getId())
                return competitionType;
        }
        return null;
    }

    private Discipline getDisciplineByIdFromAll(int id){
        for (Discipline discipline:disciplines) {
            if(id == discipline.getId())
                return discipline;
        }
        return null;
    }

}
