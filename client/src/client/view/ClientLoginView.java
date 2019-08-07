/**
 * @author Niccolò Alleyson 833974 
 * 
 * 
 */
package client.view;

import client.controller.*;
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
            loginLoader.setLocation(ClientLoginView.class.getResource("../fxml/Login.fxml"));
            ClientController control = loginLoader.getController();
            
            Parent root = loginLoader.load();
            
            Scene scene = new Scene(root);

            loginStage.setTitle("@DiMailService");
            loginStage.setScene(scene);
            loginStage.setResizable(false);
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
