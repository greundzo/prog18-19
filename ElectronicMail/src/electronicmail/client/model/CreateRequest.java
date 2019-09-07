/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicmail.client.model;

import electronicmail.publics.Email;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author greundzo
 */
public class CreateRequest implements Runnable {
    private final ClientModel model;
    private final String user;
    private final String request;
    private final Email object;
    private final Socket socket;
    private ObjectOutputStream out;
    
    public CreateRequest(ClientModel mod, String usr, String rqs, Email obj, Socket s) {
        model = mod;
        user = usr;
        request = rqs;
        object = obj;
        socket = s;
    }
    
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(user);
            out.flush();
            out.writeObject(request);
            out.flush();
            out.writeObject(object);
            out.flush();            
        } catch (IOException e) {
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
    
    public void selectMsg(String rqs) {
        
    }
}
