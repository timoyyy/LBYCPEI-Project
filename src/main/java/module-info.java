module com.example.lbycpeiproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lbycpeiproject to javafx.fxml;
    exports com.example.lbycpeiproject;
}