/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

//import server.ServerModel;
import client.model.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author greundzo
 */
public class ClientController implements Initializable {
    
    /**
     * @param model è il nostro modello 
     * @param clientSocket è il socket del nostro client
     * @param output è uno stream di oggetti per comunicare con il server
     * @param in e out sono altri stream
     */
    private ClientModel model;
    private OutputStream output;
    private DataOutputStream out;
    private DataInputStream in;
    private Socket clientSocket;

    @FXML
    private Button button;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ChoiceBox<String> choiceUser;
    @FXML
    private Label label;
    @FXML
    private Label ghostUserLabel;
    
    /**
     * Inizializza il menù a tendina. 
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choiceUser.getItems().add("user1@di.unito.it");
        choiceUser.getItems().add("user2@di.unito.it");
        choiceUser.getItems().add("user3@di.unito.it");
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
            String host = InetAddress.getLocalHost().getHostName();
            clientSocket = new Socket(host, 8189);
            model = new ClientModel(choiceUser.getValue());
            model.logRequest(clientSocket);         
            loadClient();
        } catch (IOException e) {
            ghostUserLabel.setText("SERVER OFFLINE");
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
            loader.setLocation(this.getClass().getResource("../fxml/ReadMail.fxml"));
            //ReadMailController readmail = new ReadMailController(model);
            ReadMailController readmail = loader.getController();
            
            Parent rootSecond = (Parent) loader.load();

            Stage stageSecond = new Stage();

            stageSecond.setTitle("@DiMailService - " + choiceUser.getValue());
            stageSecond.setScene(new Scene(rootSecond));
            stageSecond.setResizable(false);
            stageSecond.show();

            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();

        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }
}


