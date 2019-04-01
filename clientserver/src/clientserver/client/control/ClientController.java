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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author greundzo
 */
public class ClientController implements Initializable {
    
    @FXML
    private Button button;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{
        try {
            Parent rootSecond = FXMLLoader.load(getClass().getResource("SendMail.fxml"));
            
            Stage stageSecond = new Stage();
            
            stageSecond.setTitle("@DiMailService");
            stageSecond.setScene(new Scene(rootSecond));
            stageSecond.show();
            
            Stage stage = (Stage) button.getScene().getWindow(); 
            stage.close(); 
            
        }catch(IOException e){
            System.out.println("Can't load window.");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
