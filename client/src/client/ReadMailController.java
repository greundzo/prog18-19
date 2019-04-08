/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.FileInputStream;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReadMailController.class.getResource("Login.fxml"));
            Parent loginRoot = (Parent) loader.load();
            
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
    
}
