package com.example.aj.Controllers;

import com.example.aj.Entities.Enconter;
import com.example.aj.Entities.Reservation;
import com.example.aj.Services.EnconterService;
import com.example.aj.Services.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class HomeController {
    @FXML
    private TableColumn<Reservation, Date> dateColumn;

    @FXML
    private TableColumn<Reservation, Integer> gameColumn;

    @FXML
    private TableView<Enconter> gamesTable;

    @FXML
    private TableView<Reservation> reservationsTable;

    private int userId = 0;

    private ReservationService reservationService = new ReservationService();
    private EnconterService encounterService = new EnconterService();

    @FXML
    public void initialize() {
        initializeReservationsTable();
        initializeGamesTable();
        loadGamesData();
    }

    @FXML
    void OnReserve(ActionEvent event) {
        Enconter selectedGame = gamesTable.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            Reservation newReservation = new Reservation();
            newReservation.setEnconter_id(selectedGame.getId());
            newReservation.setUser_id(userId);
            newReservation.setDate(new Date());
            try {
                reservationService.addReservation(newReservation);
                loadReservationsByUserId();
                showAlert(Alert.AlertType.INFORMATION, "Reservation Added", "Reservation added successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add reservation.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Game Selected", "Please select a game to make a reservation.");
        }
    }

    @FXML
    void onCancelReservation(ActionEvent event) {
        Reservation selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            try {
                reservationService.deleteReservation(selectedReservation.getId());
                loadReservationsByUserId();
                showAlert(Alert.AlertType.INFORMATION, "Reservation Canceled", "Reservation canceled successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to cancel reservation.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Reservation Selected", "Please select a reservation to cancel.");
        }
    }

    private void initializeReservationsTable() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        gameColumn.setCellValueFactory(new PropertyValueFactory<>("Enconter_id"));
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


        gamesTable.getColumns().addAll(idColumn, homeTeamColumn, awayTeamColumn, stadiumColumn,
                matchDateColumn, matchTimeColumn, capacityColumn);
    }

    private void loadGamesData() {
        try {
            List<Enconter> encounters = encounterService.getAllEncounters();
            gamesTable.getItems().addAll(encounters);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message to the user)
        }
    }

    private void loadReservationsByUserId() {
        try {
            List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
            ObservableList<Reservation> reservationObservableList = FXCollections.observableArrayList(reservations);
            reservationsTable.setItems(reservationObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message to the user)
        }
    }

    public void setUserId(int userId) {
        this.userId = userId;
        loadReservationsByUserId();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
