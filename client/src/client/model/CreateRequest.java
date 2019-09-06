/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author greundzo
 */
public class CreateRequest implements Runnable {
    private final ClientModel model;
    private final Object user;
    private final Object request;
    private final Object object;
    private final Socket socket;
    private ObjectOutputStream out;
    
    public CreateRequest(ClientModel mod, String usr, Object rqs, Object obj, Socket s) {
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
            out.writeObject(request);
            out.writeObject(object);
            out.flush();
            
        } catch (IOException e) {
            //switch in base alla rqs
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
