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

        Label hogsheadsLabel = new Label("Number of Hogsheads:");
        TextField hogsheadsTextField = new TextField();
        hogsheadsTextField.setPromptText("Enter an integer");

        Label amountPerHogsheadLabel = new Label("Dollar Amount per Hogshead:");
        TextField amountPerHogsheadTextField = new TextField();
        amountPerHogsheadTextField.setPromptText("Enter a decimal");

        Label distanceLabel = new Label("Distance from Hogsmeade (miles):");
        TextField distanceTextField = new TextField();
        distanceTextField.setPromptText("Enter a decimal");

        Button calculateButton = new Button("Calculate");
        TextArea resultTextArea = new TextArea();
        resultTextArea.setEditable(false);

        // Instructions label
        Label instructionsLabel = new Label("Instructions:\n"
                + "- Enter the number of hogsheads as an integer.\n"
                + "- Enter the dollar amount per hogshead as a decimal.\n"
                + "- Enter the distance from Hogsmeade in miles as a decimal.\n"
                + "- Click the 'Calculate' button to get the summary.");

        // Calculate button event handler
        calculateButton.setOnAction(event -> {
            try {
                int numHogsheads = Integer.parseInt(hogsheadsTextField.getText());
                double dollarAmountPerHogshead = Double.parseDouble(amountPerHogsheadTextField.getText());
                double distanceMiles = Double.parseDouble(distanceTextField.getText());

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
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
                String formattedUSD = currencyFormat.format(totalUSD);
                String formattedEuros = currencyFormat.format(totalEuros);
                String formattedPounds = currencyFormat.format(totalPounds);

                String summary = "Amount: " + numHogsheads + " hogsheads, " + String.format("%.1f", totalGallons) + " gallons\n"
                        + "Delivery Time: " + String.format("%.1f", deliveryTimeHours) + " hours\n"
                        + "Cost:\n"
                        + "  U.S. Dollars:       " + formattedUSD + "\n"
                        + "  Euros:        " + formattedEuros + "\n"
                        + "  British Pounds: " + formattedPounds + "\n"
                        + "  Galleons: " + (int) totalGalleons + ", Sickles: " + totalSickles + ", Knuts: " + totalKnuts;

                resultTextArea.setText(summary);
            } catch (NumberFormatException e) {
                resultTextArea.setText("Invalid input. Please enter valid numbers.");
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
}
