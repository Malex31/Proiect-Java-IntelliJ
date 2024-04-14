package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainInterface extends Application {

    public static void showInterface(Stage primaryStage) {
        Button instrumenteButton = new Button("Instrumente Muzicale");
        instrumenteButton.setOnAction(event -> {
            DataInterface.showInstrumenteInterface(primaryStage);
        });

        Button cantaretiButton = new Button("Cantareti");
        cantaretiButton.setOnAction(event -> {
            DataInterface.showCantaretiInterface(primaryStage);
        });


        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(instrumenteButton, cantaretiButton);


        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Alegere Baza de Date");
        primaryStage.show();
    }


    @Override
    public void start(Stage primaryStage) {
    }
}
