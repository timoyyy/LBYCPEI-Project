package com.example.lbycpeiproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class GWACalculatorController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField courseCode1, units1, grade1;
    @FXML
    private TextField courseCode2, units2, grade2;
    @FXML
    private TextField courseCode3, units3, grade3;

    @FXML
    private Label deansLister, totalUnits, gpa;

    @FXML
    protected void initialize() {
        mainPane.setStyle("-fx-background-color: #368C28;");
    }

    @FXML
    protected void calculateGWA() {
        try {
            // Parse the inputs
            int units1Value = Integer.parseInt(units1.getText());
            double grade1Value = Double.parseDouble(grade1.getText());

            int units2Value = Integer.parseInt(units2.getText());
            double grade2Value = Double.parseDouble(grade2.getText());

            int units3Value = Integer.parseInt(units3.getText());
            double grade3Value = Double.parseDouble(grade3.getText());

            // Calculate the weighted sum of grades
            double weightedSum = (units1Value * grade1Value) + (units2Value * grade2Value) + (units3Value * grade3Value);

            // Calculate the total units (sum of all units)
            int totalUnitsValue = units1Value + units2Value + units3Value;

            // Calculate the GWA
            double finalGWA = weightedSum / totalUnitsValue;

            // Determine if the student qualifies for Dean's Lister
            boolean isDeansLister = finalGWA >= 3.0 && grade1Value >= 2.0 && grade2Value >= 2.0 && grade3Value >= 2.0;

            // Display the results
            totalUnits.setText(String.valueOf(totalUnitsValue));
            gpa.setText(String.format("%.3f", finalGWA));
            deansLister.setText(isDeansLister ? "YES" : "NO");

        } catch (NumberFormatException e) {
            // Handle invalid input
            gpa.setText("Invalid input!");
        }
    }
}
