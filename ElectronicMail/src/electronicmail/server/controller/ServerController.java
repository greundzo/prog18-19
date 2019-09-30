/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicmail.server.controller;

import electronicmail.server.model.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author greundzo
 */
public class ServerController implements Initializable, Observer {

    private ServerModel servermodel;
    private ServerSocket serverlink;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    @FXML
    private Button offButton;

    @FXML
    private TextArea consolelog;

    /**
     * Crea il modello, la connessione ServerSocket ed un thread per gestire le connessioni.
     * Il thread ausiliario conosce il modello ed il ServerSocket.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        consolelog.setWrapText(true);
        servermodel = new ServerModel();
        servermodel.addObserver(this);
        logMsg("Server started");
        new Thread(() -> {
            try {
                serverlink = new ServerSocket(8189);
                while (!serverlink.isClosed()) {
                    socket = serverlink.accept();
                    servermodel.launchHandle(socket, serverlink, consolelog, servermodel);
                }
            } catch (IOException e) {
                
            }
        }).start();
    }    
    
    public void setModel(ServerModel model) {
        servermodel = model;
    }
    
    /**
     * Aggiorna il log della finestra.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && arg instanceof String) {
            String msg = (String) arg;
            logMsg(msg);
        }
    }
    
    @FXML
    public void logMsg(String msg) {
        String on = java.time.LocalDateTime.now() + "  " + msg;
        consolelog.appendText(on + "\n");
    }

    /**
     * Spegne il server invocando un metodo del demone. 
     */
    @FXML
    private void offAction(ActionEvent event) {
        try {
            shutdown();
        } catch (IOException e) {
            
        }    
    }
    
    public void shutdown() throws IOException {
        serverlink.close();
        logMsg("Server off");
    }
}    
