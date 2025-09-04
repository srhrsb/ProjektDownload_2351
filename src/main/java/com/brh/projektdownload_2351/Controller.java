package com.brh.projektdownload_2351;

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
    private ArrayList<TextField> urlList = new ArrayList<>();


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

    @FXML
    protected void addUrlTf(){
        VBox container = urlContainer;
        TextField tf = new TextField();
        container.getChildren().add( tf );
        urlList.add(tf);
    }


    @FXML
    protected void onClickDownload() {
          String target = targetTf.getText();

          for( TextField tf : urlList ){
              String url = tf.getText();
              Download download = new Download(url, target);
              new Thread( download).start();
          }
    }
}