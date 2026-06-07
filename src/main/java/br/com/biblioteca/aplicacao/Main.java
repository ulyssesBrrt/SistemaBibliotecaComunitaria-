package br.com.biblioteca.aplicacao;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        AnchorPane root = new AnchorPane();

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Biblioteca Comunitária");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}