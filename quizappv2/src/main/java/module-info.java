module com.dht.quizappv2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.dht.quizappv2 to javafx.fxml;
    exports com.dht.quizappv2;
}
