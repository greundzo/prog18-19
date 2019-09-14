package electronicmail.server.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import javafx.scene.control.TextArea;
import static jdk.nashorn.internal.parser.TokenType.EOF;
import electronicmail.publics.Email;
import java.io.File;

/**
 * 
 * @author greundzo
 */

public class ServerModel extends Observable {
    
    
    public static int maxId = 0; // Id massimo email presente nel server
    public static final Object LOCKID = new Object(); // lock usato per accedere in mutua esclusione alla variabile maxId
    //2 righe superiori aggiunte da wally
    
    private final ArrayList<Email> emails;
    private final HashMap<String, Boolean> accountRefresh;
    private HandleRequest connected;
    private final String PATH = "./src/electronicmail/publics/db/";
            
    public ServerModel() {
        emails = new ArrayList<>();
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
     * @param email
     * @throws java.io.IOException
     */
    public void logAction(String usr, String rqs, Email email) throws IOException {
        
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
            case "newmail":
                writeEmail(email, usr);
                Object wrote = usr + " has sent an email";
                setChanged();
                notifyObservers(wrote);
                break;
            case "ans":
            case "ansall":
            case "forward":
            case "delete":
            case "refresh":
                break;
        }
        
    }
    
    public synchronized void writeEmail(Email em, String usr) throws IOException {
              
        //PARTE SCRITTA DA NICK 
        
        try {    
            String toWho = em.to();
            
            File sentMails = new File(PATH + "sent/" + usr + ".txt");
            
            if (!sentMails.exists()){
                sentMails.createNewFile();
            }
                                    
            File recMails = new File(PATH + "received/" + toWho + ".txt");
            
            if (!recMails.exists()) {
                recMails.createNewFile();
            }
            
            FileWriter sent = new FileWriter(sentMails, true);
            FileWriter received = new FileWriter(recMails, true);
            BufferedWriter from = new BufferedWriter(sent);
            BufferedWriter toWhere = new BufferedWriter(received);
            
            String info[] = em.getAll();
            
            synchronized (LOCKID) {
                writeflush(info, from, toWhere);
            }
            
            from.close();
            toWhere.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
    
    public void writeflush(String a[], BufferedWriter from, BufferedWriter to) throws IOException {
        from.append(a[0] + "§§" + a[1] + "§§" + a[2] + "§§" + a[3] + "§§" + a[4] + "§§" + a[5] + "§§" + "false" + "§§" + EOF + "\n");
        from.flush();
        
        to.append(a[0] + "§§" + a[1] + "§§" + a[2] + "§§" + a[3] + "§§" + a[4] + "§§" + a[5] + "§§" + "false" + "§§" + EOF + "\n");
        to.flush();
    }
    
    // PARTE AGGIUNTA DA WALLY 
    /*
    public synchronized boolean sendMail(Email obj) throws FileNotFoundException, IOException {

        if (!obj.getDestinatari().equals("user1@email.com") && !obj.getDestinatari().equals("user2@email.com") && !obj.getDestinatari().equals("user3@email.com")) {
            return false;
        }
        
        String[] splitted = obj.getDestinatari().split("@", 2);
        String pathFile = "/electronicmail/publics/db/" + splitted[0] + ".txt";

        //Scrive lista con mittente e altri destinatari
        String mittenti = obj.getFrom().get(0);
        for (int i = 1; i < obj.getFrom().size(); i++) {
            mittenti += "," + obj.getFrom().get(i);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathFile, true))) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            String formattedDate = dateFormat.format(obj.getDate());
            synchronized (LOCKID) {
                maxId++;
                obj.setIdEmail(String.valueOf(maxId));

                bw.append(obj.getId() + "§§" + mittenti + "§§" + obj.getSubject() + "§§" + obj.getText().replace("\n", "©") + "§§"
                        + formattedDate + "§§" + obj.getDestinatari() + "\n");
            }

            bw.flush();
            bw.close();
        }
        return true;

    }*/


 
}




