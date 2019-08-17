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
public class ServerListener implements Runnable {
    
    
    private final ServerSocket srv;
    private Thread current;
    private Socket incoming;
    private ServerModel model;
    private DataOutputStream out;
    private DataInputStream in;
    private InputStream input;
    private OutputStream output;
    boolean shutdown = false;
   
    
    public ServerListener(ServerSocket in) {
        srv = in;
        current = new Thread(this);
        current.setDaemon(true);
    }
    
    public void startService() {
        current.start();
    }
    
    public void shutdown() {
        //shutdown = !shutdown;
        cleanUp();
    }
    
    public void cleanUp() {
        try {
            incoming.close();
            srv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
    
    public void run() {
        
        try { 
            incoming = srv.accept(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
        
        while(true/*!shutdown*/) {
            /*in = new DataInputStream(incoming.getInputStream());
                String s = in.readUTF();
                model.checkUserLogged(s);*/
                //ora dovrebbe avvisare la vista e fare l'update
        }             
    }    
}    