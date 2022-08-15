package controller;

import javafx.animation.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class AboutFormController {
    public TextArea txtAbout;


    public ImageView imgLogo;

    public void initialize(){
        //scale transition
//        ScaleTransition st = new ScaleTransition(Duration.millis(750), imgLogo);
//        st.setFromX(0);
//        st.setFromY(0);
//        st.setToX(1);
//        st.setToY(1);
//        st.playFromStart();

        //--- Rotate transition---
//        RotateTransition rt= new RotateTransition(Duration.millis(750), imgLogo);
//        rt.setFromAngle(0);
//        rt.setToAngle(270);
//        rt.playFromStart();

        //---Translate transition ---

//        TranslateTransition tt = new TranslateTransition(Duration.millis(750), imgLogo);
//        tt.setFromX(-500);
//        tt.setToX(50);
//        tt.playFromStart();

        //Fade transition

        FadeTransition ft =new FadeTransition(Duration.millis(750), imgLogo);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.playFromStart();



    }
}
