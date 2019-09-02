/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 *
 * @author greundzo
 */
public class HandleRequest implements Runnable {
    
    private final Socket socket;
    private final ServerSocket server;
    private final TextArea area;
    private final ServerModel model;
    private String user;
    private String request;
    
    public HandleRequest (Socket s, ServerSocket sr, TextArea ar, ServerModel m) {
        socket = s;
        server = sr;
        area = ar;
        model = m;
    }
    
    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            user = (String)in.readObject();
            request = (String)in.readObject();
            
            model.logAction(user, request);
            
        } catch (ClassNotFoundException | IOException e) {
            Platform.runLater(() -> area.appendText("L'utente: " + user + " si è disconnesso\n"));
        }
    }
}
