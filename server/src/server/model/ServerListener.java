/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author greundzo
 */
public class ServerListener extends Thread {
    private ServerModel model;
    private final ServerSocket srv;
    private Socket incoming;
    private DataOutputStream out;
    private DataInputStream in;
    private InputStream input;
    private OutputStream output;
    boolean shutdown = false;
   
    
    public ServerListener(ServerSocket in) {
        srv = in;
    }
    
    public void shutdown() {
        shutdown = !shutdown;
    }
    
    public void run() {
        while(!shutdown) {
            try {
                incoming = srv.accept();
                /*in = new DataInputStream(incoming.getInputStream());
                String s = in.readUTF();
                model.checkUserLogged(s);*/
                //ora dovrebbe avvisare la vista e fare l'update
 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            incoming.close();
        } catch (IOException e) {
            e.printStackTrace();
        }           
    }    
}    