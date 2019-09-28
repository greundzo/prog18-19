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
import javafx.stage.Stage;
import electronicmail.publics.Email;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * FXML Controller class
 *
 * @author greundzo
 */
public class SendMailController implements Initializable, Observer {

    private ClientModel model;
    private Stage stage;

    @FXML
    private Button goBackButton;
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
                subLabel.setText("Re:" + email.getSubject());
                /*
                Object all[] = email.getTo().toArray();
                ArrayList<String> to = new ArrayList<>();
                
                for (Object a : all) {
                    String me = (String)a;
                    if(!me.equals(model.getUser())) {
                        to.add(me);
                    }
                }                
                String formatted = formatString(to);
                */
                toLabel.setText(email.getFrom() + ";" + email.getTo().toString().replace(",", ";").replace("[", "").replace("]", "") );                
                break;                                     
        }
    }
    
    public String formatString(ArrayList<String> myArrayList) {
        String formattedString = myArrayList.toString()
        .replace(",", ";")  //remove the commas
        .replace("[", "")  //remove the right bracket
        .replace("]", "")  //remove the left bracket
        .trim(); 
        return formattedString;//remove trailing spaces from partially initialized arrays
    }
    
    @Override
    public void update(Observable o, Object obj) {
        
    }
}
