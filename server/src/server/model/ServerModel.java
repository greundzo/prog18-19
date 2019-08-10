package server.model;

import java.io.IOException;
import java.net.*;

public class ServerModel {
    private boolean working = false;
    private final static String users[] = {"user1", "user2", "user3"};
            
    public ServerModel(){
    /*COMPLETARE COSTRUTTORE SE NECESSARIO*/    
    }
    
    public void startStop() {
        working = !working;
    }
    
    public boolean checkConnection(){
       return working;
    }
    
}



