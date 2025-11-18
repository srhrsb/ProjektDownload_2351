package com.brh.projektdownload_2351;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        tf.setPrefWidth(600);

        HBox hbox = new HBox();
        Button deleteBtn = new Button("X");
        deleteBtn.setOnAction( this::deleteDownloadPath );

        hbox.getChildren().add( tf );
        hbox.getChildren().add( deleteBtn );

        container.getChildren().add(hbox);

        urlList.add(tf);
    }

    private void deleteDownloadPath( ActionEvent event){
        System.out.println("Löschen");

        Button btn = (Button) event.getSource();
        HBox hbox = (HBox) btn.getParent();

        TextField tf = (TextField) hbox.getChildren().get(0);
        urlList.remove(tf);

        urlContainer.getChildren().remove( hbox);
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

    @FXML
    protected void deleteAllDownloads(){
        urlContainer.getChildren().clear();
        urlList.clear();
    }
}