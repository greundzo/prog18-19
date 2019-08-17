/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import server.model.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author greundzo
 */
public class ServerController implements Initializable {

    private final ServerModel servermodel;
    private ServerSocket serverlink;
    private Socket socket;
    private ServerListener service;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    @FXML
    private Button offButton;

    @FXML
    private TextArea consolelog;

    @FXML
    private Pane serverPane;

    @FXML
    private AnchorPane serverAnchor;

    public ServerController() {
        servermodel = new ServerModel();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            serverlink = new ServerSocket(8189);
            service = new ServerListener(serverlink);
            service.startService();
            logMsg("Server Started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
    @FXML
    public void update(String urgent) {
        logMsg(urgent);
    }
    
    @FXML
    public boolean logMsg(String msg) {
        String on = java.time.LocalDateTime.now() + " " + msg;
        consolelog.appendText(on + "\n");
        return true;
    }

    @FXML
    private void offAction(ActionEvent event) {
        try {
            service.shutdown();
            logMsg("Server Stopped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}    
