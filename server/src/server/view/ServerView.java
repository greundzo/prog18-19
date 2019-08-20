/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view;

import server.controller.*;
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
    public void start(Stage stage) {
        try {                       
            FXMLLoader serverLoader = new FXMLLoader();
            serverLoader.setLocation(this.getClass().getResource("../fxml/FXMLServer.fxml"));
            ServerController controller = serverLoader.getController();
            
            Parent root = (Parent) serverLoader.load();
            
            Scene scene = new Scene(root);
            stage.setTitle("@DiMailServer");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();  
        }catch(IOException e) {
            //System.out.println("SERVER: FXML NOT FOUND");
            e.printStackTrace();
        }    
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
