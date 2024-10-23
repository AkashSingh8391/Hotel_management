module com.example.hotel_management {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hotel_management to javafx.fxml;
    exports com.example.hotel_management;
}