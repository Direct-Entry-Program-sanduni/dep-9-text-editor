package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class TextEditorFormController {
    public MenuItem mnuNew;
    public MenuItem mnuOpen;
    public MenuItem mnuPrint;
    public MenuItem mnuClose;
    public MenuItem mnuCut;
    public MenuItem mnuCopy;
    public MenuItem mnuSeelctAll;
    public MenuItem mnuAbout;
    public HTMLEditor txtEditor;
    public MenuBar mnu;
    public MenuItem mnuSave;

    public void initialize(){
        mnuNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtEditor.setHtmlText("");
            }
        });
        mnuClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });

        mnuAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                URL resource = this.getClass().getResource("/view/AboutForm.fxml");
                try {
                    Parent container = FXMLLoader.load(resource);
                    Scene scene = new Scene(container);
                    Stage stage=new Stage();
                    stage.setScene(scene);
                    stage.setTitle("About");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }

        });
    }


}