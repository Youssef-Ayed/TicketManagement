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

public class LoginController {
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextFIeld;

    private UserService userService = new UserService();

    @FXML
    void goToSignUp(ActionEvent event) {
        Stage primaryStage = (Stage) usernameTextFIeld.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("SignUp.fxml"));
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
        String username = usernameTextFIeld.getText();
        String password = passwordTextField.getText();

        if (!validateInput()) {
            return; // Stop execution if input validation fails
        }

        String loginResult = userService.login(username, password);
        User u = userService.findUserByUsername(username);

        if (loginResult.equals("admin")) {
            Stage primaryStage = (Stage) usernameTextFIeld.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("admin.fxml"));
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
        } else if (loginResult.equals("user")) {
            Stage primaryStage = (Stage) usernameTextFIeld.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));

            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return; // Handle the exception
            }
            System.out.println(u.getId());
            HomeController homeController = loader.getController();
            homeController.setUserId(u.getId());
            //PropertiesController propertiesController = loader.getController();
            //propertiesController.setUser(u.getId());
            // Create a new scene with the loaded root
            Scene scene = new Scene(root);

            // Set the scene to the primary stage
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            showAlert("Error", "Invalid email or password.");
        }

        // Clear the password field after login attempt
        passwordTextField.clear();
    }

    private boolean validateInput() {
        if (usernameTextFIeld.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
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
