package server.model;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class ServerModel {
    private boolean working = false;
    private ArrayList<String> users;
            
    public ServerModel(){
    /*COMPLETARE COSTRUTTORE SE NECESSARIO*/    
    }
    
    public void startStop() {
        working = !working;
    }
    
    public boolean checkConnection(){
       return working;
    }
    
    public boolean checkUserLogged(String usr) {
        if (users.contains(usr)) {
            return false;
        } else {
            users.add(usr);
            return true;
        }
    }

}




