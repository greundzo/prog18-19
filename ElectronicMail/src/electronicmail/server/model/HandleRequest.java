/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicmail.server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String user;
    private String request;
    private Email email;
    
    public HandleRequest (Socket s, ServerSocket sr, TextArea ar, ServerModel m) {
        socket = s;
        server = sr;
        area = ar;
        model = m;
    }
    
    @Override
    public void run() {       
        try {            
            //out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            user = (String) in.readObject();
            request = (String) in.readObject();
            email = (Email) in.readObject();

            model.logAction(user, request, email, socket);
            
            if(request.equals("refresh")) {
                out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(model.getEmails()); //RIGA DEL PROBLEMA
                out.flush();
                this.stop();
            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            
        }
    }
    
    public void stop() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
    
    public void stopstreams() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
}
