package com.example.localized_database;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Locale;
import java.util.ResourceBundle;

public class HelloApplication extends Application {
    private ResourceBundle bundle;
    private Label titleLabel, firstNameLabel, lastNameLabel, emailLabel;
    private TextField firstNameField, lastNameField, emailField;
    private Button saveButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Umut Efe Uygur");


        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English", "Farsi", "Japanese");


        titleLabel = new Label();
        firstNameLabel = new Label();
        lastNameLabel = new Label();
        emailLabel = new Label();
        firstNameField = new TextField();
        lastNameField = new TextField();
        emailField = new TextField();
        saveButton = new Button();


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(new Label("Select Language:"), 0, 0);
        grid.add(languageComboBox, 1, 0);
        grid.add(firstNameLabel, 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(lastNameLabel, 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(emailLabel, 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(saveButton, 1, 4);


        switchLanguage("English");


        languageComboBox.setOnAction(e -> {
            String selectedLanguage = languageComboBox.getValue();
            switchLanguage(selectedLanguage);
        });

        saveButton.setOnAction(e -> {
            String selectedLanguage = languageComboBox.getValue();
            saveEmployeeDetails(selectedLanguage);
        });



        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void switchLanguage(String language) {
        Locale locale;
        switch (language) {
            case "Farsi":
                locale = new Locale("fa");
                break;
            case "Japanese":
                locale = new Locale("ja");
                break;
            default:
                locale = new Locale("en");
        }

        bundle = ResourceBundle.getBundle("messages", locale);
        updateLabels();
    }

    private void updateLabels() {
        titleLabel.setText(bundle.getString("title"));
        firstNameLabel.setText(bundle.getString("firstName"));
        lastNameLabel.setText(bundle.getString("lastName"));
        emailLabel.setText(bundle.getString("email"));
        saveButton.setText(bundle.getString("save"));
    }
    private void saveEmployeeDetails(String language) {
        String tableName;
        switch (language) {
            case "Farsi":
                tableName = "employee_fa";
                break;
            case "Japanese":
                tableName = "employee_ja";
                break;
            default:
                tableName = "employee_en";
        }

        String sql = "INSERT INTO " + tableName + " (first_name, last_name, email) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstNameField.getText());
            pstmt.setString(2, lastNameField.getText());
            pstmt.setString(3, emailField.getText());

            pstmt.executeUpdate();
            System.out.println("Employee details saved to " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
