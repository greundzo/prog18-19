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
public class RefreshHandle implements Runnable {
    private final Socket socket;
    private final ServerSocket server;
    private final TextArea area;
    private final ServerModel model;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final String user;
    private final String request;
    private final Email email;
    private Socket clsocket;
    
    public RefreshHandle(Socket s, ServerSocket sr, TextArea ar, ServerModel m, String usr, String rqs, Email em, ObjectInputStream inp) {
        socket = s;
        server = sr;
        area = ar;
        model = m;
        user = usr;
        request = rqs;
        email = em;
        in = inp;
    }
    
    @Override
    public void run() {
        try {
            model.logAction(user, request, email); 
            System.out.println("HAndle: sono qui");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(model.getEmails(user));
                out.flush();
                System.out.println("Handle: sono qui 2");
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }    
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
}
