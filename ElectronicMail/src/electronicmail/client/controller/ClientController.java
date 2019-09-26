/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicmail.client.controller;

//import server.ServerModel;
import electronicmail.client.model.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author greundzo
 */
public class ClientController implements Initializable, Observer {

    /**
     * @param model è il nostro modello
     * @param clientSocket è il socket del nostro client
     * @param output è uno stream di oggetti per comunicare con il server
     * @param in e out sono altri stream
     */
    private ClientModel model;
    private ReadMailController readmail;
    private OutputStream output;
    private DataOutputStream out;
    private DataInputStream in;
    private Socket clientSocket;

    @FXML
    private Button button;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField choiceUser;
    @FXML
    private Label label;
    @FXML
    private Label ghostUserLabel;

    /**
     * Inizializza il menù a tendina.
     * @param url
     * @param rb
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new ClientModel();
        model.addObserver(this);
    }

    /**
     * Gestisce il login.
     * Per prima cosa viene creato un nuovo socket, assieme al modello.
     * Viene invocato poi il metodo di login del modello: se non viene lanciata
     * un'eccezione loadClient carica la prossima vista.
     */
    @FXML
    private void loginAction(ActionEvent event) {
        try {
            if(choiceUser.getText() != null && model.userEmailCheck(choiceUser.getText())) {
                model.setUser(choiceUser.getText());
                model.logRequest();
                loadClient();
            }
            else
            {model.alert("INVALID EMAIL");}
        
        } catch (IOException e) {
            model.alert("SERVER OFFLINE");
        }
    }

    /**
     * Carica la prossima vista.
     * L'oggetto controller viene collegato al modello (da sistemare).
     * La finestra di login viene chiusa.
     */
    @FXML
    public void loadClient() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/electronicmail/client/fxml/ReadMail.fxml"));

            Parent rootSecond = (Parent) loader.load();

            readmail = loader.getController();
            model.addObserver(readmail);
            readmail.getModel(model);
            readmail.init();

            Stage stageSecond = new Stage();

            stageSecond.setTitle("@DiMailService - " + choiceUser.getText());
            stageSecond.setScene(new Scene(rootSecond));
            stageSecond.setResizable(false);
            stageSecond.setOnCloseRequest(e -> { try { model.outRequest(); } catch (IOException ex) {} Platform.exit(); System.exit(0);});
            stageSecond.show();

            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();

        } catch (NullPointerException | IOException e) {
            model.alert("LOGIN ERROR");
        }
    }



    @Override
    public void update(Observable o, Object obj) {

    }
}
