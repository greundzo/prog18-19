/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author greundzo
 */
public class ServerListener implements Runnable {
    
    
    private final ServerSocket srv;
    private final Thread current;
    private Socket incoming;
    private final ServerModel model;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private InputStream input;
    private OutputStream output;
    boolean shutdown = false;
   
    
    public ServerListener(ServerSocket in, ServerModel mod) {
        srv = in;
        model = mod;
        current = new Thread(this);
        current.setName("ServerListener");
        current.setDaemon(true);
    }
    
    public void startService() {
        current.start();
    }
    
    public void shutdown() {
        shutdown = !shutdown;
        cleanUp();
    }
    
    public void cleanUp() {
        try {
            in.close();
            //incoming.close(); //socket del client
            srv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
    
    @Override
    public void run() {
        
        try { 
            incoming = srv.accept(); 
            in = new ObjectInputStream(incoming.getInputStream());
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
        
        while(!shutdown) {
            try {
                Object obj = in.readObject();
                model.selectAction(obj);
            } catch (IOException | ClassNotFoundException e) {
                if(e instanceof IOException) {
                    e.printStackTrace();
                } else if (e instanceof ClassNotFoundException) {
                    e.printStackTrace();
                } else if (e instanceof EOFException) { //qui esplode se client muore prima di server
                    model.logAction("Someone,out");
                } else if (e instanceof SocketException) {
                    
                }
            }    
        }    
    }             
}        