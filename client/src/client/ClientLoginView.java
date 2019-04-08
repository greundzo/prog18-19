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
public class ClientLoginView extends Application {
    
    @Override
    public void start(Stage loginStage) {
        try {
            FXMLLoader loginLoader = new FXMLLoader();
            //FileInputStream fxmlStream = new FileInputStream("/home/greundzo/prog18-19/clientserver/src/clientserver/client/view/Login.fxml");
            //loginLoader.setLocation(ClientModel.class.getResource("/view/Login.fxml"));
            Parent root = loginLoader.load(getClass().getResource("Login.fxml"));
            
            Scene scene = new Scene(root);

            loginStage.setTitle("@DiMailService");
            loginStage.setScene(scene);
            loginStage.show();
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
