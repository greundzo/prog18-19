/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

//import server.ServerModel;
import client.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author greundzo
 */
public class ClientController implements Initializable {

    private ClientModel model;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    //private ServerModel servermodel;
    private Socket clientSocket;

    @FXML
    private Button button;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ChoiceBox<String> choiceUser;
    @FXML
    private Label label;
    @FXML
    private Label ghostUserLabel;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choiceUser.getItems().add("user1");
        choiceUser.getItems().add("user2");
        choiceUser.getItems().add("user3");
    }
        
    @FXML
    private void loginAction(ActionEvent event) {     
        model = new ClientModel(choiceUser.getValue());        
        try {
            String host = InetAddress.getLocalHost().getHostName();
            clientSocket = new Socket(host, 8189);
        } catch (IOException e) {
            ghostUserLabel.setText("SERVER OFFLINE");
        }
    }

    @FXML
    public void loadClient() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientController.class.getResource("ReadMail.fxml"));
            ReadMailController readMail = loader.getController();
            Parent rootSecond = (Parent) loader.load();

            Stage stageSecond = new Stage();

            stageSecond.setTitle("@DiMailService - " + choiceUser.getValue());
            stageSecond.setScene(new Scene(rootSecond));
            stageSecond.setResizable(false);
            stageSecond.show();

            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            System.out.println("Can't load window.");
        }
    }
}




/*button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                usr = userText.getText();         ///   COMINCIA LA PARTE CRITICA
                if (model.getUser().equals(usr)) {
                    if (model.checkUser(usr)) {
                        if (model.setUser(usr)) {
                            loadClient();
                        } else {
                            ghostUserLabel.setText("USER LOGGED.");
                        }
                    } else {
                        ghostUserLabel.setText("USER NOT FOUND");
                    }
                } else {
                    ClientModel newModel = new ClientModel();
                    if (newModel.checkUser(usr)) {
                        if (newModel.setUser(usr)) {
                            loadClient();
                        } else {
                            ghostUserLabel.setText("ERROR LOGGING");
                        }
                    } else {
                        ghostUserLabel.setText("USER NOT FOUND");
                    }
                }
            }                           /// FINE PARTE CRITICA
        }
        );*/

