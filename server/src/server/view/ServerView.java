/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view;

import server.controller.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
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
    public void start(Stage stage) throws IOException {
                             
        FXMLLoader serverLoader = new FXMLLoader();
        serverLoader.setLocation(this.getClass().getResource("../fxml/FXMLServer.fxml"));
        ServerController controller = serverLoader.getController();

        Parent root = (Parent) serverLoader.load();

        
        Scene scene = new Scene(root);
        stage.setTitle("@DiMailServer");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> { /*model.end;*/ Platform.exit(); System.exit(0);});
        stage.show();       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
