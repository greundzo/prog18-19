/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author greundzo
 */
public class ClientModel {
    private static String userName;
    private DataOutputStream out;
    private DataInputStream in;
    
    
    public ClientModel(String usr) {
        userName = usr;
    }

    public static String getUser() {
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
    
    public boolean logRequest(Socket sock) {
        try {
            out = new DataOutputStream(sock.getOutputStream());
            out.writeUTF(getUser() + "," + "in");
            out.close();
            return true;
        } catch (IOException e){
            e.printStackTrace();
        }   
        return false;
    }
}