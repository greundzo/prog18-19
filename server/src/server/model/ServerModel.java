package server.model;

import server.controller.*;
import java.util.ArrayList;

public class ServerModel {
    
    private ServerController control;
    private ArrayList<String> users = new ArrayList<>();
            
    public ServerModel() {
        control = null;
    }
    
    public void setControl(ServerController s) {
        control = s;
    }
    
    public ServerModel getInstance() {
        return this;
    }
    
    public boolean checkUserLogged(String usr) {
        if (users.contains(usr)) {
            return false;
        } else {
            users.add(usr);
            return true;
        }
    }
    
    public void selectAction(Object obj) {
        
        if(obj instanceof String) {
            String action = (String) obj; 
            logAction(action);
        }    
    }
    
    public void logAction(String action) {
        
        String line[] = action.split(",");
        
        switch(line[1]) {
            case "in": 
                if(this.checkUserLogged(line[0])) {
                    control.update(line[0] + " has logged in");
                } else {
                    //utente stampa che non può entrare
                    //deve rientrare un false o un eccezione al client
                }
                break;   
            case "out":
                System.out.println("SONO NEL LOGOUT");
                users.remove(line[0]);
                control.update(line[0] + " has logged out");
                break;
        }
    }
}




