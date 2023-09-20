package com.example.hogsheadale;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.text.NumberFormat;
import java.util.Locale;

public class AleTransactionSummaryFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ale Transaction Summary");

        // Create UI elements
        Label titleLabel = new Label("Ale Transaction Summary");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Create input fields and labels
        Label hogsheadsLabel = new Label("Number of Hogsheads:");
        TextField hogsheadsTextField = new TextField();
        hogsheadsTextField.setPromptText("Enter a positive integer");

        Label amountPerHogsheadLabel = new Label("Dollar Amount per Hogshead:");
        TextField amountPerHogsheadTextField = new TextField();
        amountPerHogsheadTextField.setPromptText("Enter a non-negative decimal");

        Label distanceLabel = new Label("Distance from Hogsmeade (miles):");
        TextField distanceTextField = new TextField();
        distanceTextField.setPromptText("Enter a positive decimal");

        // Create a result area
        TextArea resultTextArea = new TextArea();
        resultTextArea.setEditable(false);

        // Create a calculate button
        Button calculateButton = new Button("Calculate");

        // Instructions label
        Label instructionsLabel = new Label("Instructions:\n" +
                "- Enter the number of hogsheads as a positive integer.\n" +
                "- Enter the dollar amount per hogshead as a non-negative decimal.\n" +
                "- Enter the distance from Hogsmeade in miles as a positive decimal.\n" +
                "- Click the 'Calculate' button to get the summary.");

        // Calculate button event handler
        calculateButton.setOnAction(event -> {
            try {
                int numHogsheads = validatePositiveInteger(hogsheadsTextField.getText());
                double dollarAmountPerHogshead = validateNonNegativeDecimal(amountPerHogsheadTextField.getText());
                double distanceMiles = validatePositiveDecimal(distanceTextField.getText());

                // Constants for conversion rates and delivery speed
                final double GALLONS_PER_HOGSHEAD = 54.0;
                final double MILES_PER_HOUR = 80.0;
                final double USD_TO_GALLEON = 1 / 25.50;
                final double USD_TO_EUROS = 0.86;
                final double USD_TO_POUNDS = 0.76;
                final double GALLEON_TO_SICKLE = 17.0;
                final double SICKLE_TO_KNUT = 29.0;

                double totalGallons = numHogsheads * GALLONS_PER_HOGSHEAD;
                double deliveryTimeHours = distanceMiles / MILES_PER_HOUR;
                double totalUSD = numHogsheads * dollarAmountPerHogshead;
                double totalGalleons = totalUSD * USD_TO_GALLEON;
                double totalEuros = totalUSD * USD_TO_EUROS;
                double totalPounds = totalUSD * USD_TO_POUNDS;
                int totalSickles = (int) (totalGalleons * GALLEON_TO_SICKLE);
                int totalKnuts = (int) ((totalGalleons * GALLEON_TO_SICKLE - totalSickles) * SICKLE_TO_KNUT);

                // Format currency values
                String formattedUSD = formatCurrency(totalUSD);
                String formattedEuros = formatCurrency(totalEuros);
                String formattedPounds = formatCurrency(totalPounds);

                String summary = "Amount: " + numHogsheads + " hogsheads, " + String.format("%.1f", totalGallons) + " gallons\n"
                        + "Delivery Time: " + String.format("%.1f", deliveryTimeHours) + " hours\n"
                        + "Cost:\n"
                        + "  U.S. Dollars:       " + formattedUSD + "\n"
                        + "  Euros:               " + formattedEuros + "\n"
                        + "  British Pounds: " + formattedPounds + "\n"
                        + "  Galleons: " + (int) totalGalleons + ", Sickles: " + totalSickles + ", Knuts: " + totalKnuts;

                resultTextArea.setText(summary);
            } catch (IllegalArgumentException e) {
                resultTextArea.setText("Invalid input. " + e.getMessage());
            }
        });

        // Create layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(
                titleLabel,
                instructionsLabel,
                hogsheadsLabel, hogsheadsTextField,
                amountPerHogsheadLabel, amountPerHogsheadTextField,
                distanceLabel, distanceTextField,
                calculateButton,
                resultTextArea
        );

        // Create scene with 1280x720 resolution
        Scene scene = new Scene(vbox, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int validatePositiveInteger(String str) {
        int value;
        try {
            value = Integer.parseInt(str);
            if (value <= 0) {
                throw new IllegalArgumentException("Number of hogsheads must be a positive integer.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Number of hogsheads must be a positive integer.");
        }
        return value;
    }

    private double validateNonNegativeDecimal(String str) {
        double value;
        try {
            value = Double.parseDouble(str);
            if (value < 0) {
                throw new IllegalArgumentException("Dollar amount must be a non-negative decimal.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Dollar amount must be a non-negative decimal.");
        }
        return value;
    }

    private double validatePositiveDecimal(String str) {
        double value;
        try {
            value = Double.parseDouble(str);
            if (value <= 0) {
                throw new IllegalArgumentException("Distance must be a positive decimal.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Distance must be a positive decimal.");
        }
        return value;
    }

    private String formatCurrency(double value) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormat.format(value);
    }

    public void stop() {
        // Clean up any resources if needed
    }
}
