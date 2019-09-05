/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.model.ClientModel;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import publics.Email;

/**
 * FXML Controller class
 *
 * @author greundzo
 */
public class SendMailController implements Initializable, Observer {

    private ClientModel model;
    private Stage stage;
    private boolean bold = false, italic = false, underlined = false;
    @FXML
    private Button goBackButton;
    @FXML
    private Button sendButton;
    @FXML
    private Button boldButton;
    @FXML
    private Button underButton;
    @FXML
    private Button cursiveButton;
    @FXML
    private TextField toLabel;
    @FXML
    private TextField subLabel;
    @FXML
    private TextArea txtArea;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void getStage() {
        stage = (Stage) goBackButton.getScene().getWindow();
    }
    
    public void getModel(ClientModel m) {
        model = m;
    }

    @FXML
    public void underText(ActionEvent event) {
        
    }
    
    @FXML
    public void boldText(ActionEvent event) {
        if (!bold) {
            txtArea.setFont(Font.font("Serif", FontWeight.BOLD, 13));
            bold = !bold;
        } else {
            txtArea.setFont(Font.font("Serif", FontWeight.NORMAL, 13));
            bold = !bold;
        }  
    }
    
    @FXML
    public void cursiveText(ActionEvent event) {
        if (!italic) {
            txtArea.setFont(Font.font("Serif", FontPosture.ITALIC, 13));
            italic = !italic;
        } else {
            txtArea.setFont(Font.font("Serif", FontPosture.REGULAR, 13));
            italic = !italic;
        }    
    }
    
    @FXML
    private void goBackAction(ActionEvent event) {       
        stage.close();
    }
    
    @FXML
    private void send(ActionEvent event) {
        Email email = new Email(model.getUser(), toLabel.getText(), subLabel.getText(), txtArea.getText());
        try {
            model.sendRequest(email);
            stage.close();
        } catch (IOException e) {
            model.alert("Send failed. (Code Error: 11100)");
            stage.close();
        }    
    }
    
    @Override
    public void update(Observable o, Object obj) {
        
    }
}
