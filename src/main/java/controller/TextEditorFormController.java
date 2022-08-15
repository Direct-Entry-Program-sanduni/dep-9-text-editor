package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.web.HTMLEditor;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TextEditorFormController {
    public MenuItem mnuNew;
    public MenuItem mnuOpen;
    public MenuItem mnuPrint;
    public MenuItem mnuClose;
    public MenuItem mnuCut;
    public MenuItem mnuCopy;
    public MenuItem mnuSelectAll;
    public MenuItem mnuAbout;
    public HTMLEditor txtEditor;
    public MenuBar mnu;
    public MenuItem mnuSave;
    public TextArea txtArea;
    public MenuItem mnuPaste;

    private File srcFile;
    private boolean IsOpenFile;

    public void initialize(){
        mnuNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                IsOpenFile = false;
                txtEditor.setHtmlText("");
                txtArea.setText("");
            }
        });
        mnuOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                    fileChooser.setTitle("Open File");
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
                    srcFile = fileChooser.showOpenDialog(mnu.getScene().getWindow());
                    if (srcFile != null){
                        IsOpenFile = true;
                        loadFileToTextArea();
                    }
                }
                catch (IOException e) {
                    System.out.println(e);
                }
            }
        });

        mnuSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String text =  txtArea.getText();
                    txtEditor.setHtmlText(text);
                    save(text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mnuPrint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (Printer.getDefaultPrinter() == null){
                    new Alert(Alert.AlertType.ERROR, "No default printer has been selected").showAndWait();
                    return;
                }
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                if (printerJob != null){
                    printerJob.showPageSetupDialog(txtEditor.getScene().getWindow());
                    boolean success = printerJob.printPage(txtArea);
                    if (success){
                        printerJob.endJob();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Failed to print, try again").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Failed to initialize a new printer job").show();
                }
            }
        });

        mnuClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });

        mnuSelectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtArea.selectAll();
            }
        });

        mnuCut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtArea.cut();
            }
        });

        mnuPaste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtArea.paste();
            }
        });

        mnuCopy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtArea.copy();
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

    private void save(String htmlText) throws IOException {
        try{
            String saveFilePath;
            if (IsOpenFile){
                saveFilePath = srcFile.getAbsolutePath();
            }
            else{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select a destination folder");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
                var fileDes = fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
                if(fileDes == null){
                    return;
                }
                saveFilePath = fileDes.getAbsolutePath();
            }

            File file = new File(saveFilePath);
            if (!file.exists()){
                file.createNewFile();
            }
            byte[] bytes = htmlText.getBytes();
            FileOutputStream fos = new FileOutputStream(file);
            for (int i = 0; i < bytes.length ; i++) {
                fos.write(bytes[i]);
            }
            fos.close();
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    private  void loadFileToTextArea() throws IOException {
        FileInputStream fis = new FileInputStream(srcFile);
        var bis = new BufferedInputStream(fis);
        byte[] byteStream = new byte[(int) srcFile.length()];
        for (int i = 0; i < byteStream.length; i++){
            byteStream[i] = (byte)bis.read();
        }

        bis.close();
        txtArea.setText(new String(byteStream));
        txtEditor.setHtmlText(new String(byteStream));
    }
}
