package com.brh.projektdownload_2351;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Controller {

    @FXML
    private VBox urlContainer;
    @FXML
    private TextField targetTf;
    @FXML
    private Label progressLabel;
    private int totalProgress;
    private ArrayList<TextField> urlList = new ArrayList<>();

    /**
     * Button Event, wenn Search-Button gedrückt wird,
     * ruft Auswahl für Dateiordner auf um den Ziel-Pfad
     * auszuwählen
     */
    @FXML
    protected void onClickSearch() {
        Stage currentStage = App.getStage();
        DirectoryChooser chooser = new DirectoryChooser();

        chooser.setInitialDirectory( new File( System.getProperty("user.home")+"/downloads"));
        File selected = chooser.showDialog( currentStage );

        if(selected != null){
            String path = selected.getAbsolutePath();
            targetTf.setText( path );
        }
    }

    /**
     * Fügt ein weiteres Textfeld für einen Download hinzu
     */
    @FXML
    protected void addUrlTf(){
        VBox container = urlContainer;
        TextField tf = new TextField();
        container.getChildren().add( tf );
        urlList.add(tf);
    }

    /**
     * Download Button gedrückt, Download-Liste wird nacheinander
     * durchlaufen und entsprechende Downloads werden ausgelöst
     */
    @FXML
    protected void onClickDownload() {

          String target = targetTf.getText();

          for( TextField tf : urlList ){
              String url = tf.getText();
              Download download = new Download(url, target, this::addBytesToProgressDisplay);
              new Thread( download).start();
          }
    }

    /**
     * Zählt die von einem Download gerade geladene Bytes zum Gesamt-
     * fortschritt hinzu und zeigt den Fortschritt an
     * @param bytes
     */
    private void addBytesToProgressDisplay( int bytes ){

        totalProgress += bytes;

        //Im Thread des Downloads (kein JavaFX-Thread)
        // kann die Nutzeroberfläche
        // nicht direkt geändert werden, daher erfolgt die
        //Weitergabe an JavaFX
        Platform.runLater(
                () -> {
                    progressLabel.setText("runtergeladen: "+ totalProgress + " Bytes");
                }
        );


    }

}