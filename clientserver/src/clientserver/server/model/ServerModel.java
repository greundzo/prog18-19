/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserver.server.model;

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
public class ServerModel extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        try {
            //serverLoader.setLocation(clientserver.server.model.getClass().getResource("ServerStage.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("ServerStage.fxml"));

            Scene scene = new Scene(root);

            stage.setTitle("@DiMailServer");
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
