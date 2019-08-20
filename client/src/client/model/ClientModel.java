/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author greundzo
 */
public class ClientModel {
    private static String userName;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    public ClientModel(String usr) {
        userName = usr;
    }

    public ClientModel getModel(String name) {
        if(this.getUser().equals(name)) {
            return this;
        } else {
            return new ClientModel(name);
        }
    }
    
    public String getUser() {
        return userName;
    }
    
    public static boolean setUser(String user) {
        if(userName == null) {
            userName = user;
            return true;
        }
        return false;
    }
    
    public void resetUser() {
        userName = null;
    }
    
    public boolean logRequest(Socket s) {
        socket = s;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeUTF(this.getUser() + ", in");
            return true;
        } catch (IOException e){
            e.printStackTrace();
        }   
        return false;
    }
    
    public boolean outRequest() {
        try {
            //out = new ObjectOutputStream(socket.getOutputStream());
            out.writeUTF(this.getUser() + ", out");
            out.close();
            socket.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}