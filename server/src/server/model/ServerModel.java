package server.model;

import server.controller.*;
import java.util.ArrayList;

public class ServerModel {
    
    private ServerController control;
    private ArrayList<String> users = new ArrayList<>();
            
    public ServerModel() {
        control = null;
    }
    /**
     * Permette al model di comunicare con il controller.
     * @param s è il riferimento del controller
     */
    public void setControl(ServerController s) {
        control = s;
    }
    
    public ServerModel getInstance() {
        return this;
    }
    
    /**
     * Tiene conto di quali utenti sono loggati.
     * @param usr è il nome utente
     * @return true se l'utente non aveva già effettuato il login
     */
    public boolean checkUserLogged(String usr) {
        if (users.contains(usr)) {
            return false;
        } else {
            users.add(usr);
            return true;
        }
    }
    
    /**
     * In base all'istanza del parametro decide quale metodo eseguire.
     * @param obj 
     */
    public void selectAction(Object obj) {
        
        if(obj instanceof String) {
            String action = (String) obj; 
            logAction(action);
        }    
    }
    
    /**
     * Comunica al controller se il client sta eseguendo il login o il logout.
     * @param action viene diviso in due da split, la seconda parte determina se
     * si parla di login o logout.
     */
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




