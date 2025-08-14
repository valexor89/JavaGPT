package it.javagpt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class JavaGPTApplication extends Application {

    @Override
    public void start(Stage stage) {
        Button btn = new Button("Ciao JavaFX!");
        btn.setOnAction(e -> btn.setText("🎉 Funziona!"));

        StackPane root = new StackPane(btn);
        stage.setTitle("HelloFX");
        stage.setScene(new Scene(root, 400, 250));
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}