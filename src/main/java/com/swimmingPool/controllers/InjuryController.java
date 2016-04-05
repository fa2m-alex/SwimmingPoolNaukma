package com.swimmingPool.controllers;

import com.swimmingPool.App;
import com.swimmingPool.dao.impl.InjuryDaoImpl;
import com.swimmingPool.models.Injury;
import com.swimmingPool.dao.interfaces.InjuryDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private Label swimerLabel;


    // Reference to the main application.
    private App mainApp;

    private List<Injury> injuries = null;
    ObservableList<Injury> injuryData = null;

    private InjuryDao injuryDao;


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public InjuryController() {
        injuryDao = new InjuryDaoImpl();
        injuries = injuryDao.getAll();

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
        } else {
            // Person is null, remove all the text.
            titleLabel.setText("");
            dateLabel.setText("");
            //swimerLabel.setText("");
        }
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        injuryTable.setItems(injuryData);
    }
}
