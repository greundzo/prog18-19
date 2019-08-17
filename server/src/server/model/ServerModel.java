package server.model;

import server.view.*;
import java.util.ArrayList;

public class ServerModel {
    private boolean working = false;
    private final ServerView view = new ServerView();
    private ArrayList<String> users;
            
    public ServerModel(){
    /*COMPLETARE COSTRUTTORE SE NECESSARIO*/    
    }
    
    public ServerModel getInstance() {
        if(this==null) {
            return new ServerModel();
        } else {
            return this;
        }
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
    
    public void invokeMethod(String toInvoke) {
        String line[] = toInvoke.split(",");
        
        switch(line[1]) {
            case "in": 
                view.update(line[0] + "has logged in");
                this.checkUserLogged(line[0]);
                break;   
            case "out":
                view.update(line[0] + "has logged out");
                break;
            case "send":
                //scrivi nei file
                //view.refresh();
        }
    }
}




