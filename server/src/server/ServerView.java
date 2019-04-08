/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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
public class ServerView extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        try {                       
            FXMLLoader serverLoader = new FXMLLoader();
            serverLoader.setLocation(ServerView.class.getResource("FXMLServer.fxml"));
            Parent root = serverLoader.load();
            ServerController controller = serverLoader.getController();
            Scene scene = new Scene(root);

            stage.setTitle("@DiMailServer");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();  
        }catch(IOException e) {
            System.out.println("SERVER: FXML NOT FOUND");
        }    
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
