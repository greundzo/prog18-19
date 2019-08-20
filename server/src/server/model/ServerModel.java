package server.model;

import server.controller.*;
import java.util.ArrayList;

public class ServerModel {
    private boolean working = false;
    private ServerController control;
    private ArrayList<String> users;
            
    public ServerModel(){
        control = null;
    }
    
    public void setControl(ServerController s) {
        control = s;
    }
    
    public ServerModel getInstance() {
        return this;
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
                control.update(line[0] + "has logged in");
                this.checkUserLogged(line[0]);
                break;   
            case "out":
                control.update(line[0] + "has logged out");
                break;
            case "send":
                //scrivi nei file
                //control.refresh();
        }
    }
}




