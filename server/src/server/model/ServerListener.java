/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

//import java.io.EOFException;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

/**
 *
 * @author greundzo
 */
public class ServerListener implements Runnable {
    
    
    private final ServerSocket srv;
    private final Thread current;
    private Socket incoming;
    private final ServerModel model;
    //private ObjectOutputStream out;
    //private ObjectInputStream in;
    //private InputStream input;
    //private OutputStream output;
    private final HashMap<ThreadServer, Integer> map = new HashMap<>();
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
    
    /**
     * Chiude tutti i ThreadServer invocando il loro metodo cleanAll, poi chiude i restanti socket e stream.
     */
    public void cleanUp() {
        try {
            map.keySet().forEach((t) -> {
                t.cleanAll();
            });            
            srv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
    
    @Override
    public void run() {
        int i = 1;
        
        try { 
            while (true) {
                incoming = srv.accept();
                ThreadServer thread = new ThreadServer(incoming, model);
                map.put(thread, i);
                thread.start();
                i++;
            }
        } catch (SocketException e) { 
            System.out.println("il socket si è chiuso");
        } catch (IOException e) {
            e.printStackTrace();
        }       
    }    
}        