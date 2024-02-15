module org.example.icecreamorder {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.icecreamorder to javafx.fxml;
    exports org.example.icecreamorder;
}