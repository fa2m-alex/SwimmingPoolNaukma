package com.swimmingPool.controllers;

import com.swimmingPool.App;
import com.swimmingPool.dao.impl.InjuryDaoImpl;
import com.swimmingPool.dao.impl.SwimmerDaoImpl;
import com.swimmingPool.dao.interfaces.SwimmerDao;
import com.swimmingPool.models.Injury;
import com.swimmingPool.dao.interfaces.InjuryDao;
import com.swimmingPool.models.Swimmer;
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
 * Created by west on 05.04.2016.
 */
public class InjuryController {

    @FXML
    private TableView<Injury> injuryTable;
    @FXML
    private TableColumn<Injury, String> titleColumn;

    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label swimmerLabel;


    // Reference to the main application.
    private App mainApp;

    private List<Injury> injuries = null;
    ObservableList<Injury> injuryData = null;

    private List<Swimmer> swimmers = null;

    private InjuryDao injuryDao;
    private SwimmerDao swimmerDao;


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public InjuryController() {
        injuryDao = new InjuryDaoImpl();
        injuries = injuryDao.getAll();

        swimmerDao = new SwimmerDaoImpl();
        swimmers = swimmerDao.getAll();

        injuryData = FXCollections.observableArrayList();
        for (Injury injury : injuries) {
            injuryData.add(injury);
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.

        titleColumn.setCellValueFactory(new PropertyValueFactory<Injury, String>("title"));

        // Clear person details.
        showInjuryDetails(null);

        // Listen for selection changes and show the person details when changed.
        injuryTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showInjuryDetails(newValue));
    }

    private void showInjuryDetails(Injury injury) {
        if (injury != null) {
            // Fill the labels with info from the person object.
            titleLabel.setText(injury.getTitle());
            dateLabel.setText(injury.getDate().toString());
            //swimerLabel.setText(injury.getSwimmer_id());
            if(injury.getSwimmer_id() != 0)
                swimmerLabel.setText(getSwimmerByIdFromAll(injury.getSwimmer_id()).toString());
            else
                swimmerLabel.setText("");
        } else {
            // Person is null, remove all the text.
            titleLabel.setText("");
            dateLabel.setText("");
            swimmerLabel.setText("");
        }
    }

    private Swimmer getSwimmerByIdFromAll(int id){
        for (Swimmer swimmer:swimmers) {
            if(id == swimmer.getId())
                return swimmer;
        }
        return null;
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        injuryTable.setItems(injuryData);
    }

    @FXML
    private void newInjury() {
        Injury temp = new Injury();
        boolean okClicked = showInjuryEditDialog(temp);
        if (okClicked) {
            injuryData.add(temp);
            injuryDao.insert(temp);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Травму було додано");
            alert.setHeaderText("Травму було додано");
            alert.showAndWait();
        }
    }

    public boolean showInjuryEditDialog(Injury injury) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/view/InjuryEditForm2.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Додавання травми");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            InjuryEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setInjury(injury);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
