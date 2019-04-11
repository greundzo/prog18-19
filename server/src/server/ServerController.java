/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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
    private Button onOffButton;

    @FXML
    private ListView<String> list;

    @FXML
    private Pane serverPane;

    @FXML
    private AnchorPane serverAnchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        servermodel.startStop();
        try {
            serverlink = new ServerSocket(8189);
            //service = new Thread();
            socket = serverlink.accept();
        } catch (IOException e) {
            System.out.println("ERRORE SERVER");
        } finally {
            try {
                //Thread.sleep(60000);
                socket.close();
                serverlink.close();
            } catch (IOException e) {
                System.out.println("ERRORE CHIUSURA");
            //} catch (InterruptedException e) {
              //  System.out.println("ERRORE THREAD");
            }
        }
    }

    @FXML
    private void onOffAction(ActionEvent event) {
        servermodel.startStop();
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
        list.refresh();*/
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
        }
    
    }
}    
