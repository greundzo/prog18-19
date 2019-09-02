/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.model.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author greundzo
 */
public class ReadMailController implements Initializable, Observer {
    
    private ClientModel model; 
    private Socket sock; 
    private String usr;
    
    @FXML
    private TitledPane mailList;
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
    public void initialize(URL url, ResourceBundle rb) {        
        
    }    

    @FXML
    private void newMailAction(ActionEvent event) {
    }

    @FXML
    private void ansMailAction(ActionEvent event) {
    }

    @FXML
    private void ansToAllAction(ActionEvent event) {
    }

    @FXML
    private void forwardMailAction(ActionEvent event) {
    }

    @FXML
    private void deleteMailAction(ActionEvent event) {
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
            backToLogin();
        } catch (IOException | NullPointerException e) {
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
            loader.setLocation(this.getClass().getResource("../fxml/Login.fxml"));
            
            
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
            System.out.println("READMAILCONTROLLER: IO ERROR");
        }
    }
    
    @Override
    public void update(Observable obs, Object obj) {
        
    }
    
    public void getModel(ClientModel m){
        model = m;
    }
}
