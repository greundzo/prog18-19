package server;

public class ServerModel {
    private boolean working = false;
    private final static String users[] = {"user1@di.unito.it", "user2@di.unito.it", "user3@di.unito.it"};
            
    public ServerModel(){
    /*COMPLETARE COSTRUTTORE SE NECESSARIO*/    
    }
    
    public void startStop() {
        working = !working;
    }
    
    public boolean checkConnection(){
       return working;
    }
    
    public boolean checkUser(String usr) {
        for(int i = 0; i < 3; i++) {
            if(usr == users[i]) {
                return true;
            }
        }
        return false;
    }
}