/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
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
    private ServerSocket srv;
    private Socket incoming;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    boolean done = false;
    
    public ServerListener(ServerSocket in) {
        srv = in;
    }
    
    public void run() {
        try {
            while(!done) {
                incoming = srv.accept();
                in = new ObjectInputStream(incoming.getInputStream());
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}