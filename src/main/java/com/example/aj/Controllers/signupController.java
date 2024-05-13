package com.example.aj.Controllers;

import com.example.aj.Entities.User;
import com.example.aj.MainApplication;
import com.example.aj.Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class signupController {
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextField;

    private UserService userService = new UserService();

    @FXML
    void goToLogin(ActionEvent event) {
        Stage primaryStage = (Stage) usernameTextField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return; // Handle the exception
        }

        // Create a new scene with the loaded root
        Scene scene = new Scene(root);

        // Set the scene to the primary stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void submit(ActionEvent event) {
        if (!validateInput()) {
            return; // Stop execution if input validation fails
        }

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("USER");

        userService.addUser(newUser);

        // Show a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Account created successfully!");
        alert.showAndWait();

        // Clear the form fields after successful signup
        usernameTextField.clear();
        passwordTextField.clear();

        Stage primaryStage = (Stage) usernameTextField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return; // Handle the exception
        }

        HomeController homeController = loader.getController();
        homeController.setUserId(userService.findUserByUsername(username).getId());
        //PropertiesController propertiesController = loader.getController();
        //propertiesController.setUser(userService.findUserByEmail(email).getId());
        // Create a new scene with the loaded root
        Scene scene = new Scene(root);

        // Set the scene to the primary stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean validateInput() {
        if (usernameTextField.getText().isEmpty() ||
                passwordTextField.getText().isEmpty()) {
            showAlert("Missing Fields", "Please fill in all required fields.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
