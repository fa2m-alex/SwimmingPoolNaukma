package com.swimmingPool.controllers;

import com.swimmingPool.dao.impl.CompetitionTypeDaoImpl;
import com.swimmingPool.dao.impl.DisciplineDaoImpl;
import com.swimmingPool.dao.interfaces.CompetitionTypeDao;
import com.swimmingPool.dao.interfaces.DisciplineDao;
import com.swimmingPool.models.Competition;
import com.swimmingPool.models.CompetitionType;
import com.swimmingPool.models.Discipline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Alex on 05.04.2016.
 */
public class CompetitionEditController {

    private Stage dialogStage;
    private Competition competition;

    @FXML
    private ChoiceBox<CompetitionType> typeField;
    @FXML
    private ChoiceBox<Discipline> disciplineField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField dateField;

    private List<CompetitionType> types;
    private List<Discipline> disciplines;

    private ObservableList<CompetitionType> typesOb;
    private ObservableList<Discipline> disciplinesOb;

    private CompetitionTypeDao competitionTypeDao;
    private DisciplineDao disciplineDao;
    private boolean okClicked = false;

    public CompetitionEditController(){

    }

    @FXML
    private void initialize(){
        competitionTypeDao = new CompetitionTypeDaoImpl();
        disciplineDao = new DisciplineDaoImpl();

        types = competitionTypeDao.getAll();
        disciplines = disciplineDao.getAll();

        typesOb = FXCollections.observableArrayList();
        for (CompetitionType type : types) {

            typesOb.add(type);
        }

        disciplinesOb = FXCollections.observableArrayList();
        for (Discipline discipline : disciplines) {
            disciplinesOb.add(discipline);
        }

        typeField.setItems(typesOb);
        disciplineField.setItems(disciplinesOb);

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() throws ParseException {
        if(isInputValid()){

            competition.setTitle(titleField.getText());

            if(typeField.getSelectionModel().getSelectedItem() != null)
                competition.setType_id(typeField.getSelectionModel().getSelectedItem().getId());
            else
                competition.setType_id(0);

            if(disciplineField.getSelectionModel().getSelectedItem() != null)
                competition.setDiscipline_id(disciplineField.getSelectionModel().getSelectedItem().getId());
            else
                competition.setDiscipline_id(0);

            String source=dateField.getText();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date d = new java.sql.Date(format.parse(source).getTime());
            competition.setDate(d);

            okClicked = true;

            dialogStage.close();
        }


    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    public void setCompetition(Competition competition){
        this.competition = competition;

        titleField.setText(competition.getTitle());
        typeField.getSelectionModel().select(getTypeByIdFromAll(competition.getType_id()));
        disciplineField.getSelectionModel().select(getDisciplineByIdFromAll(competition.getDiscipline_id()));

        if(competition.getDate() != null)
            dateField.setText(competition.getDate().toString());

        dateField.setPromptText("yyyy-mm-dd");
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "No valid title!\n";
        }

        if (dateField.getText() == null || dateField.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }

        if (typeField.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "No valid type!\n";
        }

        if (disciplineField.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "No valid discipline!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    private CompetitionType getTypeByIdFromAll(int id){
        for (CompetitionType competitionType:types) {
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
