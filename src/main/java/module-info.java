module com.example.hogsheadale {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hogsheadale to javafx.fxml;
    exports com.example.hogsheadale;
}