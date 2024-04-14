package org.example;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataInterface extends Application {

    public static void showInstrumenteInterface(Stage primaryStage) {

        Stage dataStage = new Stage();

        Label dataLabel = new Label("Tabela instrumente muzicale");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM instrumente_muzicale")) {

            TableView<Instrumente_muzicale> tableView = new TableView<>();
            TableColumn<Instrumente_muzicale, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
            TableColumn<Instrumente_muzicale, String> tipColumn = new TableColumn<>("Tip");
            tipColumn.setCellValueFactory(cellData -> cellData.getValue().tipProperty());
            TableColumn<Instrumente_muzicale, String> producatorColumn = new TableColumn<>("Producator");
            producatorColumn.setCellValueFactory(cellData -> cellData.getValue().producatorProperty());
            TableColumn<Instrumente_muzicale, String> modelColumn = new TableColumn<>("Model");
            modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
            TableColumn<Instrumente_muzicale, Integer> nrCorziColumn = new TableColumn<>("Nr. Corzi");
            nrCorziColumn.setCellValueFactory(cellData -> cellData.getValue().nr_corziProperty().asObject());

            tableView.getColumns().addAll(idColumn, tipColumn, producatorColumn, modelColumn, nrCorziColumn);

            while (resultSet.next()) {
                Instrumente_muzicale instrument = new Instrumente_muzicale(
                        resultSet.getInt("id"),
                        resultSet.getString("tip"),
                        resultSet.getString("producator"),
                        resultSet.getString("model"),
                        resultSet.getInt("nr_corzi")
                );
                tableView.getItems().add(instrument);
            }

            VBox root = new VBox(tableView);

            Button addButton = new Button("Adaugă Instrument");
            addButton.setOnAction(event -> showAddInstrumentInterface());
            root.getChildren().add(addButton);

            Button searchButton = new Button("Caută instrumente");
            searchButton.setOnAction(event -> showSearchInstrumentInterface());
            root.getChildren().add(searchButton);

            Button deleteButton = new Button("Șterge instrument");
            deleteButton.setOnAction(event -> {
                deleteInstrumentInterface();
            });
            root.getChildren().add(deleteButton);
            VBox filterLayout = createFilterLayout(tableView);
            root.getChildren().add(filterLayout);

            Scene scene = new Scene(root, 600, 400);
            dataStage.setScene(scene);
            dataStage.setTitle("Instrumente Muzicale");
            dataStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void showCantaretiInterface(Stage primaryStage) {
        Stage dataStage = new Stage();

        Label dataLabel = new Label("Tabela Cantareti");

        TableView<Cantareti> tableView = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM cantareti")) {

            tableView = new TableView<>();

            TableColumn<Cantareti, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
            TableColumn<Cantareti, String> numeColumn = new TableColumn<>("Nume");
            numeColumn.setCellValueFactory(cellData -> cellData.getValue().numeProperty());
            TableColumn<Cantareti, Integer> varstaColumn = new TableColumn<>("Varsta");
            varstaColumn.setCellValueFactory(cellData -> cellData.getValue().varstaProperty().asObject());
            TableColumn<Cantareti, String> idInstrumentColumn = new TableColumn<>("ID Instrument");
            idInstrumentColumn.setCellValueFactory(cellData -> {
                Instrumente_muzicale instrument = cellData.getValue().getId_instrument();
                if (instrument != null) {
                    return new SimpleStringProperty(String.valueOf(instrument.getId()));
                } else {
                    return new SimpleStringProperty("");
                }
            });

            tableView.getColumns().addAll(idColumn, numeColumn, varstaColumn, idInstrumentColumn);

            while (resultSet.next()) {
                int idInstrument = resultSet.getInt("id_instrument");
                Instrumente_muzicale instrument = getInstrumentById(idInstrument);
                Cantareti cantareti = new Cantareti(
                        resultSet.getInt("id"),
                        resultSet.getString("nume"),
                        resultSet.getInt("varsta"),
                        instrument
                );
                tableView.getItems().add(cantareti);
            }

            VBox root = new VBox(tableView);
            Scene scene = new Scene(root, 600, 400);
            dataStage.setScene(scene);
            dataStage.setTitle("Cantareti");
            dataStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        VBox root = new VBox();
        Button addButton = new Button("Adaugă cântăreț");
        addButton.setOnAction(event -> showAddSingerInterface());
        root.getChildren().add(addButton);
        Button deleteButton = new Button("Șterge cântăreț");
        deleteButton.setOnAction(event -> {
            deletesingersInterface();
        });
        root.getChildren().add(deleteButton);

        Button searchButton = new Button("Caută cântăreți");
        searchButton.setOnAction(event -> showSearchSingerInterface());
        root.getChildren().add(searchButton);

        root.getChildren().add(tableView);

        Scene scene = new Scene(root, 600, 400);
        dataStage.setScene(scene);
        dataStage.setTitle("Cantareti");
        dataStage.show();
    }

    public static Instrumente_muzicale getInstrumentById(int id) {
        Instrumente_muzicale instrument = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "root");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM instrumente_muzicale WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    instrument = new Instrumente_muzicale(
                            resultSet.getInt("id"),
                            resultSet.getString("tip"),
                            resultSet.getString("producator"),
                            resultSet.getString("model"),
                            resultSet.getInt("nr_corzi")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instrument;
    }
    public static void showSearchSingerInterface() {
        Stage searchStage = new Stage();
        searchStage.setTitle("Caută Cântăreți");

        TextField searchField = new TextField();
        searchField.setPromptText("Introdu numele cântărețului...");
        Button searchButton = new Button("Caută");
        Label resultLabel = new Label();
        searchButton.setOnAction(event -> {
            String searchName = searchField.getText();
            List<Cantareti> cantareti = cautaCantaretiDupaNume(searchName);
            if (cantareti.isEmpty()) {
                resultLabel.setText("Nu s-au găsit rezultate pentru căutarea \"" + searchName + "\"");
            } else {
                StringBuilder resultText = new StringBuilder();
                for (Cantareti cantaret : cantareti) {
                    resultText.append(cantaret.toString()).append("\n");
                }
                resultLabel.setText(resultText.toString());
            }
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(searchField, searchButton, resultLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 200);
        searchStage.setScene(scene);
        searchStage.show();
    }
    private static List<Cantareti> cautaCantaretiDupaNume(String searchName) {
        List<Cantareti> cauta = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "root");
            String query = "Select * from cantareti where nume=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, searchName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                int varsta = resultSet.getInt("varsta");
                int idInstrument = resultSet.getInt("id_instrument");
                Instrumente_muzicale instrumenteMuzicale = getInstrumentById(idInstrument);
                Cantareti singer = new Cantareti(id, nume, varsta, instrumenteMuzicale);
                cauta.add(singer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cauta;
    }
    public static void deletesingersInterface() {
        Stage deleteStage = new Stage();
        deleteStage.setTitle("Sterge Cântăreți");

        TextField nameField = new TextField();
        nameField.setPromptText("Introdu numele cântărețului...");
        Button deleteButton= new Button("Sterge");
        Label resultLabel = new Label();
        deleteButton.setOnAction(event -> {
            String singerNameToDelete = nameField.getText();
            deleteSingerByName(singerNameToDelete);
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(nameField, deleteButton, resultLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 200);
        deleteStage.setScene(scene);
        deleteStage.show();
    }

    public static void deleteSingerByName(String name) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "root");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM cantareti WHERE nume = ?")) {
            statement.setString(1, name);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cântărețul cu numele " + name + " a fost șters din baza de date.");
            } else {
                System.out.println("Nu s-au găsit cântăreți cu numele " + name + " în baza de date.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void showAddSingerInterface() {

        Stage addStage = new Stage();
        addStage.setTitle("Adaugă Cântăreț");

        TextField instrumentIdField = new TextField();
        instrumentIdField.setPromptText("ID Instrument");

        TextField idField = new TextField();
        idField.setPromptText("Id");

        TextField nameField = new TextField();
        nameField.setPromptText("Nume");

        TextField ageField = new TextField();
        ageField.setPromptText("Vârstă");
        Button addButton = new Button("Adaugă");
        addButton.setOnAction(event -> {

            int instrumentId = Integer.parseInt(instrumentIdField.getText());
            int id=Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            addSinger(instrumentId,id,name, age );
            addStage.close();
        });

        VBox root = new VBox(10);
        root.getChildren().addAll( instrumentIdField,idField,nameField, ageField, addButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 200);
        addStage.setScene(scene);
        addStage.show();
    }

    public static void addSinger(int instrumentId,int id,String name, int age ) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "root");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO cantareti (id_instrument,id,nume, varsta ) VALUES (?,?, ?, ?)")) {
            statement.setInt(1,instrumentId );
            statement.setInt(2, id);
            statement.setString(3,name );
            statement.setInt(4,age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addInstrument(int id,String tip ,String producator, String model, int nr_corzi ) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "root");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO instrumente_muzicale (id,tip,producator,model,nr_corzi ) VALUES (?,?, ?, ?,?)")) {
            statement.setInt(1,id );
            statement.setString(2, tip);
            statement.setString(3,producator );
            statement.setString(4,model);
            statement.setInt(5,nr_corzi);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void showAddInstrumentInterface() {

        Stage addStage = new Stage();
        addStage.setTitle("Adaugă Instrument muzical");

        TextField idField = new TextField();
        idField.setPromptText("id");

        TextField tipField = new TextField();
        tipField.setPromptText("tip");

        TextField producatorField = new TextField();
        producatorField.setPromptText("Producator");

        TextField modelField = new TextField();
        modelField.setPromptText("Model");

        TextField nr_corziField = new TextField();
        nr_corziField.setPromptText("Nr_corzi");

        Button addButton = new Button("Adaugă");
        addButton.setOnAction(event -> {

            int id = Integer.parseInt(idField.getText());
            String tip = tipField.getText();
            String producator = producatorField.getText();
            String model = modelField.getText();
            int nr_corzi = Integer.parseInt(nr_corziField.getText());
            addInstrument(id,tip,producator,model,nr_corzi);
            addStage.close();
        });

        VBox root = new VBox(10);
        root.getChildren().addAll( idField,tipField,producatorField,modelField,nr_corziField, addButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 200);
        addStage.setScene(scene);
        addStage.show();
    }

    private static List<Instrumente_muzicale> cautaInstrumentdupatip(String searchtip) {
        List<Instrumente_muzicale> cauta = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "root");
            String query = "Select * from instrumente_muzicale where tip=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, searchtip);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String tip = resultSet.getString("tip");
                String producator = resultSet.getString("producator");
                String model = resultSet.getString("model");
                int nr_corzi = resultSet.getInt("nr_corzi");
                Instrumente_muzicale instrumenteMuzicale=new Instrumente_muzicale(id,tip,producator,model,nr_corzi);
                cauta.add(instrumenteMuzicale);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cauta;
    }

    public static void showSearchInstrumentInterface() {
        Stage searchStage = new Stage();
        searchStage.setTitle("Caută Instrument ");

        TextField searchField = new TextField();
        searchField.setPromptText("Introdu tipul instrumentului..");
        Button searchButton = new Button("Caută");
        Label resultLabel = new Label();
        searchButton.setOnAction(event -> {
            String searchName = searchField.getText();
            List<Instrumente_muzicale> instrumenteMuzicales= cautaInstrumentdupatip(searchName);
            if (instrumenteMuzicales.isEmpty()) {
                resultLabel.setText("Nu s-au găsit rezultate pentru căutarea \"" + searchName + "\"");
            } else {
                StringBuilder resultText = new StringBuilder();
                for (Instrumente_muzicale i:instrumenteMuzicales) {

                    resultText.append(i.toString()).append("\n");
                }
                resultLabel.setText(resultText.toString());
            }
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(searchField, searchButton, resultLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 200);
        searchStage.setScene(scene);
        searchStage.show();
    }
    public static void deleteInstrumentByTip(String tip) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "root");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM instrumente_muzicale WHERE tip = ?")) {
            statement.setString(1, tip);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Instrumentul " + tip + " a fost șters din baza de date.");
            } else {
                System.out.println("Nu s-au găsit instrumentul de tipul " + tip + " în baza de date.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteInstrumentInterface() {
        Stage deleteStage = new Stage();
        deleteStage.setTitle("Sterge Instrument");

        TextField nameField = new TextField();
        nameField.setPromptText("Introdu tipul instrumentului...");
        Button deleteButton= new Button("Sterge");
        Label resultLabel = new Label();
        deleteButton.setOnAction(event -> {
            String singerNameToDelete = nameField.getText();
            deleteInstrumentByTip(singerNameToDelete);
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(nameField, deleteButton, resultLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 200);
        deleteStage.setScene(scene);
        deleteStage.show();
    }

    private static VBox createFilterLayout(TableView<Instrumente_muzicale> tableView) {

        TextField nrCorziFilterField = new TextField();
        nrCorziFilterField.setPromptText("Nr. Corzi");

        Button filterButton = new Button("Filtrează");
        filterButton.setOnAction(event -> {
            int nrCorzi = Integer.parseInt(nrCorziFilterField.getText());

            List<Instrumente_muzicale> filteredInstruments = filterInstrumentsByNrCorzi(tableView.getItems(), nrCorzi);

            tableView.getItems().clear();
            tableView.getItems().addAll(filteredInstruments);
        });

        VBox filterLayout = new VBox();
        filterLayout.getChildren().addAll(nrCorziFilterField, filterButton);

        return filterLayout;
    }

    private static List<Instrumente_muzicale> filterInstrumentsByNrCorzi(List<Instrumente_muzicale> instruments, int nrCorzi) {
        List<Instrumente_muzicale> filteredInstruments = new ArrayList<>();
        for (Instrumente_muzicale instrument : instruments) {
            if (instrument.getNr_corzi() == nrCorzi) {
                filteredInstruments.add(instrument);
            }
        }
        return filteredInstruments;
    }
    @Override
    public void start(Stage primaryStage) {

    }
}
