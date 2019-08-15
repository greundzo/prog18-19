/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import server.model.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

    private final ServerModel servermodel = new ServerModel();
    private ServerSocket serverlink;
    private Socket socket;
    private Thread service;
    private DataInputStream input;
    private InputStream in;
    private DataOutputStream output;
    private OutputStream out;

    @FXML
    private Button offButton;

    @FXML
    private TextArea consolelog;

    @FXML
    private Pane serverPane;

    @FXML
    private AnchorPane serverAnchor;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //servermodel.startStop();
            serverlink = new ServerSocket(8189);
            service = new ServerListener(serverlink);
            service.setDaemon(true);
            service.start();
            logMsg("Server Started");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            service.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}    
