package com.brh.projektdownload_2351;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Stage currentStage;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 530, 300);
        stage.setTitle("Downloader");
        stage.setScene(scene);
        stage.show();
        App.currentStage = stage;
    }

    public static Stage getStage(){
        return currentStage;
    }

    public static void main(String[] args) {
        launch();
    }
}