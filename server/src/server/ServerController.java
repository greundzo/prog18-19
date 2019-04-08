/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author greundzo
 */
public class ServerController implements Initializable {
    
    @FXML
    private Button onOffButton;
    
    @FXML
    private ListView<String> serverLog;    

    @FXML
    private Pane serverPane;

    @FXML
    private AnchorPane serverAnchor;

    //ObservableList<String> serverList = FXCollections.observableArrayList("a", "b", "c");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //serverLog.setItems(serverList);
        //serverLog.getItems().addAll(serverList);
    }    

    @FXML
    private void onOffAction(ActionEvent event) {
        System.out.println("ccc");
        String power = java.time.LocalDateTime.now() + "Server On";
        //serverList.add(power);
        serverLog.getItems().addAll(power);
        serverLog.refresh();
        
    }
    
}
