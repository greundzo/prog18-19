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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ObservableList<?> log;

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
            service = new Thread(new ServerListener(serverlink));
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
        /*
        if (servermodel.checkConnection()) {
            try {
                serverlink = new ServerSocket(8189);
                socket = serverlink.accept();
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("ccc");
                /*String power = java.time.LocalDateTime.now() + "Server On";
        //serverList.add(power);
        list.getItems().addAll(power);
        list.refresh();
            } catch (IOException e) {
                System.out.println("ERRORE APERTURA");
            } finally {
                
            }
        } else {
            try {
                socket.close();
                serverlink.close();
            } catch (IOException e) {
                System.out.println("ERRORE CHIUSURA");
            } finally {
                try {
                    socket.close();
                    serverlink.close();
                } catch (IOException e) {
                    System.out.println("FINALLY: ERRORE CHIUSURA");
                }
            }
        }*/
    
    }
}    
