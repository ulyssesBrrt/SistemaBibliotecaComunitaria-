module biblioteca.comunitaria {

    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    opens br.com.biblioteca.controller to javafx.fxml;
    exports br.com.biblioteca.aplicacao;
}