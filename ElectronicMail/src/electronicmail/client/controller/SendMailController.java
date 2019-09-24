/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicmail.client.controller;

import electronicmail.client.model.ClientModel;
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
import electronicmail.publics.Email;
import java.util.ArrayList;

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
       txtArea.setWrapText(true);
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
        model.reverseWidget();
        stage.close();
    }
    
    @FXML
    private void send(ActionEvent event) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(toLabel.getText());
        
        Email email = new Email(model.getUser(), arr, subLabel.getText(), txtArea.getText());
        try {
            model.sendRequest(email);
            model.reverseWidget();
            stage.close();
        } catch (IOException e) {
            model.alert("Connection interrupted. (Code Error: 11100)");
            stage.close();
        }    
    }
    
    public void setParamethers(String action, Email email) {
        switch(action) {
            case "forward": 
                subLabel.setText(email.getSubject());
                txtArea.setText(email.getText());
                break;
            case "reply":
                subLabel.setText("Re:" + email.getSubject());
                toLabel.setText(email.getFrom());
                break;
            case "reply all":
                /*    MODO ALTERNATIVO DI FARLO ma credo che quello sotto possa funzionare 
                String mittenti = email.getFrom().get(0);
                for (int i = 1; i < email.getFrom().size(); i++) {
                mittenti += "," + emails.getFrom().get(i);
                }
                */
                subLabel.setText("Re:" + email.getSubject());
                toLabel.setText(email.getFrom()+ email.getTo(/*remove user*/));
                
                
                break;           
                
        }
    }
    
    @Override
    public void update(Observable o, Object obj) {
        
    }
}
