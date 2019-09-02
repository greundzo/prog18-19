package server.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import javafx.scene.control.TextArea;

/**
 * 
 * @author greundzo
 */

public class ServerModel extends Observable {
    
    private final HashMap<String, ArrayList<String>> emails;
    private final HashMap<String, Boolean> accountRefresh;
    private HandleRequest connected;
            
    public ServerModel() {
        emails = new HashMap<>();
        accountRefresh = new HashMap<>();
    }
    
    public void launchHandle(Socket s, ServerSocket srv, TextArea area, ServerModel m) {
        connected = new HandleRequest(s, srv, area, this);
        new Thread(connected).start();
    }
    
    /**
     * Comunica al controller se il client sta eseguendo il login o il logout.
     * @param usr nome utente
     * @param rqs tipo di richiesta
     * @throws java.io.IOException
     */
    public synchronized void logAction(String usr, String rqs) throws IOException {
        
        switch(rqs) {
            case "in": 
                Object login = usr + " has logged in";
                setChanged();
                notifyObservers(login);
                break;   
            case "out":
                Object logout = usr + " has logged out";
                setChanged();
                notifyObservers(logout);
                break;
            case "new":
            case "ans":
            case "ansall":
            case "forward":
            case "delete":
        }
        
    }
}




