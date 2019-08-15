/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author greundzo
 */
public class ServerListener implements Runnable {
    private ServerModel model;
    private final ServerSocket srv;
    private Socket incoming;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    boolean done = false;
    int i = 5;
    
    public ServerListener(ServerSocket in) {
        srv = in;
    }
    
    public void done() {
        done = !done;
    }
    
    public void run() {
        while(!done) {
            try {
                incoming = srv.accept();                
                //model.checkUserLogged(/*input nome dato dallo stream*/);
                //in = new ObjectInputStream(incoming.getInputStream());                
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