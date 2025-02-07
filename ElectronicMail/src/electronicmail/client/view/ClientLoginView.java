package electronicmail.client.view;

import electronicmail.client.controller.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author wallahd
 * @author greundzo
 */
public class ClientLoginView extends Application {
    
    /**
     * 
     * @param loginStage è lo stage in cui verrà caricato il documento fxml
     * la grafica è impostata di maniera che le dimensioni non possano essere modificate
     */
    @Override
    public void start(Stage loginStage) {
        try {
            FXMLLoader loginLoader = new FXMLLoader();
            loginLoader.setLocation(this.getClass().getResource("/electronicmail/client/fxml/Login.fxml"));            
            Parent root = (Parent) loginLoader.load();            
            
            ClientController control = loginLoader.getController();
                        
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
