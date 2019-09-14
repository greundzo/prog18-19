/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicmail.client.model;

import electronicmail.publics.Email;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author greundzo
 */
public class RefreshRequest implements Runnable {
    
    private ClientModel model;
    private final String user;
    private final String request;
    private final Email object;
    private final Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    public RefreshRequest(ClientModel mod, String usr, String rqs, Email obj, Socket s) {
        model = mod;
        user = usr;
        request = rqs;
        object = obj;
        socket = s;
    }
    
    @Override
    public void run() {
        try {
            sendRequest();
            in = new ObjectInputStream(socket.getInputStream());
            Object ems = in.readObject();
            model.setEmails((ArrayList<Email>) ems);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            this.stop();
        }   
    }
    
    public void stop() {
        try {
            out.close();
            socket.close();
        } catch (IOException e) {
            
        }    
    }
    
    public void sendRequest() throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(user);
        out.flush();
        out.writeObject(request);
        out.flush();
        out.writeObject(object);
        out.flush();      
    }
}
