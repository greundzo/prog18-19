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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectOutputStream;

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
    
    public ArrayList<Email> getEmails() {
        return emails;
    }
    
    /**
     * Comunica al controller se il client sta eseguendo il login o il logout.
     * @param usr nome utente
     * @param rqs tipo di richiesta
     * @param email
     * @param s
     * @throws java.io.IOException
     */
    public void logAction(String usr, String rqs, Email email, Socket s) throws IOException {
        
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
                refresh(usr, s);
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

    public void refresh(String usr, Socket sock) {
             
        synchronized (LOCKID) {
            try {
                
                //ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
                
                File received = new File(PATH + "received/" + usr + ".txt");
                
                if (!received.exists()) {
                    received.createNewFile();
                }
                
                FileReader fr = new FileReader(received);
                BufferedReader br = new BufferedReader(fr);
                
                String ln;
                
                while((ln = br.readLine()) != null) {
                    
                    String line[] = ln.split("§§");
             
                    Email em = new Email(line[1], line[2], line[3], line[4]);
                    
                    em.setId(line[0]);
                    em.setDate(line[5]);
                    
                    if ("true".equals(line[6])) {
                        em.hasBeenRead();
                    }
                    
                    emails.add(em);
                }
                /*
                new Thread(() -> {
                    try {                        
                        out.writeObject(emails);
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            out.close();
                            sock.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }    
                    }   
                }).start();*/
                        
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
 
}




