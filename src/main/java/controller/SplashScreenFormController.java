package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class SplashScreenFormController {
    public Label lblLoding;
    public ImageView imgSplash;
    public void initialize(){
        Timeline timeline = new Timeline();
        var keyFrame1 = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoding.setText("Initializing the UI..!");
            }
        });
        var keyFrame2 = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoding.setText("Loading Plugins..!");
            }
        });
        var keyFrame3 = new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoding.setText("Setup the UI Logic..!");
            }
        });
        var keyFrame4 = new KeyFrame(Duration.millis(3500), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    URL resource = this.getClass().getResource("/view/TextEditorForm.fxml");
                    Parent container = FXMLLoader.load(resource);
                    Scene editorScene = new Scene(container);
                    Stage primaryStage = new Stage();
                    primaryStage.setScene(editorScene);
                    primaryStage.setTitle("DEP-9 Text Editor");
                    primaryStage.show();
                    primaryStage.centerOnScreen();
                    lblLoding.getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        timeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3, keyFrame4);
        timeline.playFromStart();
    }
}
