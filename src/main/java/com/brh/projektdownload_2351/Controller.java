package com.brh.projektdownload_2351;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML
    private TextField urlTf;

    @FXML
    private TextField targetTf;


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
    protected void onClickDownload() {
          String path = urlTf.getText();

          Download download = new Download(path, targetTf.getText());
          download.execute();
    }
}