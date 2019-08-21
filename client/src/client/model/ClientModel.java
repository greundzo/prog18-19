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
            Object obj = this.getUser() + ",in";
            out.writeObject(obj);
            return true; // deve tornare conferma del log o errore perché utente già dentro
        } catch (IOException e){
            e.printStackTrace();
        }   
        return false;
    }
    
    public boolean outRequest() {
        try {
            Object obj = this.getUser() + ",out";
            out.writeObject(obj);
            out.close();
            socket.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}