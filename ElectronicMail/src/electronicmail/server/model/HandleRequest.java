/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicmail.server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.scene.control.TextArea;
import electronicmail.publics.Email;

/**
 *
 * @author greundzo
 */
public class HandleRequest implements Runnable {
    
    private final Socket socket;
    private final ServerSocket server;
    private final TextArea area;
    private final ServerModel model;
    private ObjectInputStream in;
    private String user;
    private String request;
    private Email email;
    private Socket clsocket;
    
    public HandleRequest (Socket s, ServerSocket sr, TextArea ar, ServerModel m) {
        socket = s;
        server = sr;
        area = ar;
        model = m;
    }
    
    @Override
    public void run() {       
        try {            
            in = new ObjectInputStream(socket.getInputStream());

            user = (String) in.readObject();
            request = (String) in.readObject();
            email = (Email) in.readObject();
            
            if (request.equals("refresh")) {
                System.out.println("Handle normale");
                new Thread(() -> {
                    Runnable fresh = new RefreshHandle(socket, server, area, model, user, request, email, in);
                    new Thread(fresh).start();
                }).start();
            } else {
                model.logAction(user, request, email);
                this.stop();
            }

        } catch (ClassNotFoundException | IOException e) {            
            e.printStackTrace();
        } 
    }
    
    public void stop() {
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
    
}
