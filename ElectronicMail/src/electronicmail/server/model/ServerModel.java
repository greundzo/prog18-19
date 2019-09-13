package electronicmail.server.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import javafx.scene.control.TextArea;
import static jdk.nashorn.internal.parser.TokenType.EOF;
import electronicmail.publics.Email;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 * 
 * @author greundzo
 */

public class ServerModel extends Observable {
    
    private final HashMap<String, ArrayList<String>> emails;
    private final HashMap<String, Boolean> accountRefresh;
    private HandleRequest connected;
    private final String PATH = "./src/electronicmail/publics/db/";
            
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
     * @param email
     * @throws java.io.IOException
     */
    public synchronized void logAction(String usr, String rqs, Email email) throws IOException {
        
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
        }
        
    }
    
    public synchronized void writeEmail(Email em, String usr) throws IOException {
       
        /*
        
        PARTE SCRITTA DA NICK 
        
        try {    
            
            File sentMails = new File(PATH + "sent/" + usr + ".txt");
            
            if (!sentMails.exists()){
                sentMails.createNewFile();
            }
                                    
            File recMails = new File(PATH + "received/" + toWho + ".txt");
            
            if (!recMails.exists()) {
                recMails.createNewFile();
            }
            
            FileWriter sent = new FileWriter(sentMails);
            FileWriter received = new FileWriter(recMails);
            PrintWriter from = new PrintWriter(new BufferedWriter(sent));
            PrintWriter toWhere = new PrintWriter(new BufferedWriter(received));
            
            String info[] = em.getAll();
            
            for (String i1 : info) {
                from.write(i1 + "§§");
                toWhere.write(i1 + "§§");
            }
            
            from.write("false" + "§§" + EOF);
            from.println();
            from.close();
            
            toWhere.write("false" + "§§" + EOF);
            toWhere.println();
            toWhere.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }*/    
    }
    
    // PARTE AGGIUNTA DA WALLY 
    
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

    }


 
}




