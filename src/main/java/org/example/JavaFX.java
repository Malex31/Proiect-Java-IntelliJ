package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Autentificare");
        Label usernameLabel = new Label("Nume utilizator:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Parolă:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Autentificare");

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.equals("admin") && password.equals("admin123")) {
                MainInterface.showInterface(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare de autentificare");
                alert.setHeaderText(null);
                alert.setContentText("Nume de utilizator sau parolă incorectă!");
                alert.showAndWait();
            }
        });
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(10));
        loginLayout.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel,
                passwordField, loginButton);

        Scene loginScene = new Scene(loginLayout, 300, 200);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Autentificare");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
