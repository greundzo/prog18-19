/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserver.client.control;

import java.io.IOException;
import java.net.URL;
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
public class ReadMailController implements Initializable {

    /**
     * Initializes the controller class.
     */

     @FXML
    private TextArea readArea;

    @FXML
    private Button deleteButton;

    @FXML
    private AnchorPane readPane;

    @FXML
    private Button forwardButton;

    @FXML
    private TitledPane mailList;

    @FXML
    private Button ansButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button mailButton;

    @FXML
    private Button ansToAllButton;

    @FXML
    void newMailAction(ActionEvent event) {
        
    }

    @FXML
    void ansMailAction(ActionEvent event) {

    }

    @FXML
    void ansToAllAction(ActionEvent event) {

    }

    @FXML
    void forwardMailAction(ActionEvent event) {

    }

    @FXML
    void deleteMailAction(ActionEvent event) {

    }
    
    @FXML
    void logoutAction(ActionEvent e) {          
        try {
            Parent login = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage loginStage = new Stage();
            
            loginStage.setTitle("@DiMailService");
            loginStage.setScene(new Scene(login));
            loginStage.show();
            
        }catch(IOException f){
            System.out.println("Logout Error");
        }
        
        Stage current = (Stage)logoutButton.getScene().getWindow();
        current.close();
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
