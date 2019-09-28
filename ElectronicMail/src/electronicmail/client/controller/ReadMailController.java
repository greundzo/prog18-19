/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicmail.client.controller;

import electronicmail.client.model.*;
import electronicmail.publics.Email;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author greundzo
 */
public class ReadMailController implements Initializable, Observer {

    private ClientModel model;
    private ObservableList<Email> emails;
    private Email currentEmail;

    @FXML
    private TitledPane mailList;
    @FXML
    private ListView eList;
    @FXML
    private AnchorPane readPane;
    @FXML
    private Button mailButton;
    @FXML
    private TextArea readArea;
    @FXML
    private Button ansButton;
    @FXML
    private Button ansToAllButton;
    @FXML
    private Button forwardButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button logoutButton;

    public ReadMailController() {
        model = null;
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    @SuppressWarnings("Convert2Lambda")
    public void initialize(URL url, ResourceBundle rb) {
        readArea.setWrapText(true);

        eList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Email>() {
                @Override
                public void changed(ObservableValue<? extends Email> ov, Email old_val, Email new_val) {
                    try { readMailContent(new_val); } catch (NullPointerException e) {}                       
            }
        });
        
        eList.setCellFactory(param -> new ListCell<Email>() {             
            @Override
            protected void updateItem(Email email, boolean empty) {
                super.updateItem(email, empty);

                if (empty || email == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10); 
                    String prev = "From: " + (email.getFrom().length() > 15 ? email.getFrom().substring(0,10) + "..." : email.getFrom()) + "\nSubject: " +
                            (email.getSubject().length() > 15 ? email.getSubject().substring(0, 10) + "..." : email.getSubject());
                    Label lab = new Label(prev);
                    hbox.getChildren().addAll(lab);
                    setGraphic(hbox);
                }
            }
        });
    }

    public void getModel(ClientModel m){
        model = m;
    }

    public void init() {
        RefreshMails r = new RefreshMails(model, eList, readArea);
        new Thread(r).start();
    }

    public void readMailContent(Email email) throws NullPointerException {
        currentEmail = email;
        readArea.setVisible(true);
        readArea.setDisable(false);
        ArrayList<String> arr = new ArrayList(email.getTo());
        arr.remove(model.getUser());
        readArea.setText(
                "DATA: " + email.getDate() + "\n" +
                "MITTENTE: " + email.getFrom() + "\n" +
                "ALTRI DESTINATARI: " + arr.toString() + "\n\n" +
                "OGGETTO: " + email.getSubject() + "\n\n" +
                "TESTO: " + "\n" + email.getText());
          
    }

    @FXML
    private void newMailAction(ActionEvent event) {
        if (!model.getWidget()) {
            model.reverseWidget();
            try {
                sendWidget(null);
            } catch (IOException e) {
                model.alert("Internal error. (Code Error: 10141)");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void ansMailAction(ActionEvent event) {
        if (!model.getWidget()) {
            model.reverseWidget();
            try{
                if (currentEmail != null) {
                    sendWidget("reply");
                }
            } catch(IOException e){
                model.alert("Internal error. (Code Error: 10155)");
            }
        }
    }

    @FXML
    private void ansToAllAction(ActionEvent event) {
        if (!model.getWidget()) {
            model.reverseWidget();
            try{
                if (currentEmail != null) {
                    sendWidget("reply all");
                }
            } catch(IOException e){
                model.alert("Internal error. (Code Error: 10152)");
            }
        }
    }

    @FXML
    private void forwardMailAction(ActionEvent event) {
        if (!model.getWidget()) {
            model.reverseWidget();
            try {
                if (currentEmail != null) {
                    sendWidget("forward");
                }
            } catch (IOException e) {
                model.alert("Internal error. (Code Error: 10144)");
            }
        }
    }

    @FXML
    private void deleteMailAction(ActionEvent event) {
        if (!model.getWidget()) {
            model.reverseWidget();
            try {
                if (currentEmail != null) {
                    model.deleteRequest(currentEmail);
                    readArea.clear();
                    //updateCell();
                }
            } catch (IOException e) {
                model.alert("Internal error. (Code Error: 10128)");
            } 
        }
    }

    /**
     * Gestisce il logout del client.
     * Il metodo del modello outRequest comunica al server che il client
     * sta uscendo. Il metodo backToLogin viene invocato.
     */
    @FXML
    private void logoutAction(ActionEvent event) {
        try {
            model.outRequest();
            eList.getItems().clear();
            backToLogin();
        } catch (IOException | NullPointerException e) {
            model.alert("Connection interrupted. (Code Error: 11010)");
            backToLogin();
        }
    }

    /**
     * Chiude la finestra corrente e genera una nuova vista di login.
     */
    @FXML
    public void backToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/electronicmail/client/fxml/Login.fxml"));

            Parent loginRoot = (Parent) loader.load();
            ClientController control = loader.getController();

            Scene loginScene = new Scene(loginRoot);

            Stage loginStage = new Stage();
            loginStage.setTitle("@DiMailService");
            loginStage.setScene(loginScene);
            loginStage.show();

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();
        }catch(IOException notFound) {
            model.alert("Internal error. (Code Error: 10141)");
        }
    }

    /**
     *
     * @param action
     * @throws IOException
     */
    @FXML
    public void sendWidget(String action) throws IOException {
        try {
            FXMLLoader sendScene = new FXMLLoader();
            sendScene.setLocation(this.getClass().getResource("/electronicmail/client/fxml/SendMail.fxml"));

            Parent parent = (Parent) sendScene.load();

            SendMailController control = sendScene.getController();
            model.addObserver(control);
            control.getModel(model);

            Scene scene2 = new Scene(parent);
            Stage stage2 = new Stage();
            stage2.setTitle("@DiMailService");
            stage2.setOnCloseRequest(e -> model.reverseWidget());
            stage2.setScene(scene2);
            stage2.show();
            control.getStage();

            if(action!=null) {
                control.setParamethers(action, currentEmail);
            }
        } catch (IOException e) {
            model.alert("Internal error. (Code Error: 10141)");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable obs, Object obj) {}
    
    // --------------metodo per display oggetto e mittente cella nella list ------------
    private void updateCell() {
        
    }
}


