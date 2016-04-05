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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

    private boolean openDialog(Competition competition){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/view/CompetitionEditForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редагування змагання");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CompetitionEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCompetition(competition);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void newCompetiton(){
        Competition temp = new Competition();
        boolean okClicked = openDialog(temp);
        if (okClicked) {
            personData.add(temp);
            competitionDao.insert(temp);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Змагання додано");
            alert.setHeaderText("Змагання додано");
            alert.showAndWait();
        }
    }

    @FXML
    private void editCompetition(){
        Competition selectedCompetition = competitionTable.getSelectionModel().getSelectedItem();
        if (selectedCompetition != null) {
            boolean okClicked = openDialog(selectedCompetition);
            if (okClicked) {
                competitionDao.update(selectedCompetition);
                showPersonDetails(selectedCompetition);
                competitionTable.refresh();
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Не обрано");
            alert.setHeaderText("Не обрано жодного змагання");
            alert.setContentText("Будь ласка оберіть змагання");

            alert.showAndWait();
        }
    }

    @FXML
    private void deleteCompetition(){
        int selectedIndex = competitionTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            competitionDao.delete(competitionTable.getSelectionModel().getSelectedItem());
            competitionTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Не обрано");
            alert.setHeaderText("Не обрано жодного змагання");
            alert.setContentText("Будь ласка оберіть змагання");
            alert.showAndWait();
        }
    }


}
