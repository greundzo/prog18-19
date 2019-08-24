/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author greundzo
 */
public class ThreadServer extends Thread {
    private final Socket socket;
    private final ServerModel model;
    private ObjectInputStream in;
    
    public ThreadServer(Socket s, ServerModel m) {
        socket = s;
        model = m;
    }
    
    public void cleanAll() {
        try {
            in.close();
            socket.close();
            this.join();
        } catch (IOException | InterruptedException e) {
            System.out.println("Sono un serverThread e mi sono chiuso");
        }
    }
    
    @Override
    public void start() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            while (true) {
                Object obj = in.readObject();
                model.selectAction(obj);
            }    
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


