/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author greundzo
 */
public class ClientModel extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loginLoader = new FXMLLoader();            
            loginLoader.setLocation(ClientModel.class.getResource("Login.fxml"));
            ClientController loginControl = loginLoader.getController();
            
            Parent root = loginLoader.load();            
            Scene scene = new Scene(root);
            stage.setTitle("@DiMailService");
            stage.setScene(scene);
            stage.show();
        }catch(IOException e) {
            System.out.println("CLIENT: FXML NOT FOUND");
        }    
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
