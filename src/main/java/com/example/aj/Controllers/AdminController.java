package com.example.aj.Controllers;

import com.example.aj.Entities.Enconter;
import com.example.aj.Services.EnconterService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AdminController {

    @FXML
    private TextField awayTeamTextField;

    @FXML
    private TextField capacityTextField;

    @FXML
    private TextField homeTeamTextField;

    @FXML
    private TextField matchDateTextField;

    @FXML
    private TextField matchTimeTextField;

    @FXML
    private TextField stadiumTextField;

    @FXML
    private TableView<Enconter> table;

    private EnconterService encounterService = new EnconterService();

    private Enconter selectedEncounter;

    @FXML
    void add(ActionEvent event) {
        Enconter newEncounter = new Enconter();
        newEncounter.setHomeTeam(homeTeamTextField.getText());
        newEncounter.setAwayTeam(awayTeamTextField.getText());
        newEncounter.setStadium(stadiumTextField.getText());
        newEncounter.setMatchDate(matchDateTextField.getText());
        newEncounter.setMatchTime(matchTimeTextField.getText());
        newEncounter.setCapacity(Integer.parseInt(capacityTextField.getText()));

        try {
            encounterService.addEncounter(newEncounter);
            loadGamesData(); // Refresh the table after adding
            showAlert(Alert.AlertType.INFORMATION, "Encounter Added", "Encounter added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add encounter.");
        }
    }

    @FXML
    void update(ActionEvent event) {
        if (selectedEncounter != null) {
            selectedEncounter.setHomeTeam(homeTeamTextField.getText());
            selectedEncounter.setAwayTeam(awayTeamTextField.getText());
            selectedEncounter.setStadium(stadiumTextField.getText());
            selectedEncounter.setMatchDate(matchDateTextField.getText());
            selectedEncounter.setMatchTime(matchTimeTextField.getText());
            selectedEncounter.setCapacity(Integer.parseInt(capacityTextField.getText()));

            try {
                encounterService.updateEncounter(selectedEncounter);
                loadGamesData(); // Refresh the table after updating
                showAlert(Alert.AlertType.INFORMATION, "Encounter Updated", "Encounter updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update encounter.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Encounter Selected", "Please select an encounter to update.");
        }
    }

    @FXML
    void delete(ActionEvent event) {
        if (selectedEncounter != null) {
            try {
                encounterService.deleteEncounter(selectedEncounter.getId());
                loadGamesData(); // Refresh the table after deleting
                clearTextFields();
                showAlert(Alert.AlertType.INFORMATION, "Encounter Deleted", "Encounter deleted successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete encounter.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Encounter Selected", "Please select an encounter to delete.");
        }
    }

    public void initialize() {
        initializeGamesTable();
        loadGamesData();
        addTableClickListener();
    }

    private void initializeGamesTable() {
        TableColumn<Enconter, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Enconter, String> homeTeamColumn = new TableColumn<>("Home Team");
        homeTeamColumn.setCellValueFactory(new PropertyValueFactory<>("homeTeam"));

        TableColumn<Enconter, String> awayTeamColumn = new TableColumn<>("Away Team");
        awayTeamColumn.setCellValueFactory(new PropertyValueFactory<>("awayTeam"));

        TableColumn<Enconter, String> stadiumColumn = new TableColumn<>("Stadium");
        stadiumColumn.setCellValueFactory(new PropertyValueFactory<>("stadium"));

        TableColumn<Enconter, Date> matchDateColumn = new TableColumn<>("Match Date");
        matchDateColumn.setCellValueFactory(new PropertyValueFactory<>("matchDate"));

        TableColumn<Enconter, String> matchTimeColumn = new TableColumn<>("Match Time");
        matchTimeColumn.setCellValueFactory(new PropertyValueFactory<>("matchTime"));

        TableColumn<Enconter, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));


        table.getColumns().addAll(idColumn, homeTeamColumn, awayTeamColumn, stadiumColumn,
                matchDateColumn, matchTimeColumn, capacityColumn);
    }

    private void loadGamesData() {
        table.getItems().clear();
        try {
            List<Enconter> encounters = encounterService.getAllEncounters();
            table.getItems().addAll(encounters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void clearTextFields() {
        homeTeamTextField.clear();
        awayTeamTextField.clear();
        stadiumTextField.clear();
        matchDateTextField.clear();
        matchTimeTextField.clear();
        capacityTextField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void addTableClickListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedEncounter = newSelection;
                fillTextFields(selectedEncounter);
            }
        });
    }

    private void fillTextFields(Enconter encounter) {
        if (encounter != null) {
            homeTeamTextField.setText(encounter.getHomeTeam());
            awayTeamTextField.setText(encounter.getAwayTeam());
            stadiumTextField.setText(encounter.getStadium());
            matchDateTextField.setText(encounter.getMatchDate());
            matchTimeTextField.setText(encounter.getMatchTime());
            capacityTextField.setText(String.valueOf(encounter.getCapacity()));
        }
    }
}
