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
import java.util.Arrays;

/**
 * 
 * @author greundzo
 */

public class ServerModel extends Observable {
    
    
    public static int maxId = 0; // Id massimo email presente nel server
    public static final Object LOCKID = new Object(); // lock usato per accedere in mutua esclusione alla variabile maxId
    //2 righe superiori aggiunte da wally
    
    private ArrayList<Email> emails;
    private HashMap<String, ArrayList<Email>> usersMails; 
    private final HashMap<String, Boolean> accountRefresh;
    private HandleRequest connected;
    private final String PATH = "./src/electronicmail/publics/db/";
    private BufferedWriter from;
    private BufferedWriter toWhere;
            
    public ServerModel() {
        emails = new ArrayList<>();
        usersMails = new HashMap<>();
        accountRefresh = new HashMap<>();
    }
    
    /**
     * Lancia un thread ausiliario per gestire la connessione
     * @param s socket da collegare al client
     * @param srv serversocket
     * @param area textarea per l'update della vista
     * @param m il modello del server
     */
    public void launchHandle(Socket s, ServerSocket srv, TextArea area, ServerModel m) {
        connected = new HandleRequest(s, srv, area, this);
        new Thread(connected).start();
    }
    
    public ArrayList<Email> getEmails(String usr) {
        return usersMails.get(usr);
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
                refresh(usr);
                setChanged();
                notifyObservers(usr + " has refreshed");
                break;
        }
        
    }
    
    /**
     *
     * @param em email da scrivere nei file
     * @param usr nome utente di chi sta mandando l'email
     * @throws IOException se non riesce a scrivere
     */
    public synchronized void writeEmail(Email em, String usr) throws IOException {
        
        try {            
            ArrayList<String> whos = em.getTo();
            String[] a = {};
            
            for(int i = 0; i < whos.size(); i++) {
                a[i] = whos.get(i);
            }
            
            String inf = em.toString();
            String[] support = inf.split(",");
            String info[] = {support[0], Arrays.toString(a), support[1], support[2], support[3]};
            
            File sentMails = new File(PATH + "sent/" + usr + ".txt");
            
            if (!sentMails.exists()){
                sentMails.createNewFile();
            }
            
            FileWriter sent = new FileWriter(sentMails, true);
            from = new BufferedWriter(sent);
            
            synchronized(LOCKID) {
                writeflush(info, from);
            }
            
            for (String to : a) {
                File recMails = new File(PATH + "received/" + to + ".txt");
                
                if (!recMails.exists()) {
                    recMails.createNewFile();
                }
                
                FileWriter received = new FileWriter(recMails, true);
                       
                toWhere = new BufferedWriter(received);
                  
                synchronized (LOCKID) {
                    writeflush(info, toWhere);
                }
            }
                from.close();
                toWhere.close();
            
        } catch (IOException e) {
        }   
    }
    
    /**
     *
     * @param a campi dell'email
     * @param buff
     * @throws IOException
     */
    public void writeflush(String a[], BufferedWriter buff) throws IOException {
        buff.append(maxId++ + "§§" + a[1] + "§§" + a[2] + "§§" + a[3] + "§§" + a[4] + "§§" + a[5] + "§§" + "false" + "§§" + EOF + "\n");
        buff.flush();
    }

    public void refresh(String usr) {
             
        synchronized (LOCKID) {
            try {
                
                File received = new File(PATH + "received/" + usr + ".txt");
                
                if (!received.exists()) {
                    received.createNewFile();
                }
                
                FileReader fr = new FileReader(received);
                BufferedReader br = new BufferedReader(fr);
                
                String ln;
                
                while((ln = br.readLine()) != null) {
                    
                    String line[] = ln.split("§§");
                    ArrayList<String>to = new ArrayList<>();
                    to.add(line[2]);
             
                    Email em = new Email(line[1], to, line[3], line[4]);
                    
                    em.setId(line[0]);
                    em.setDate(line[5]);
                    
                    if ("true".equals(line[6])) {
                        em.hasBeenRead();
                    }
                    
                    emails.add(em);
                    
                    System.out.println("IO ho finito.");
                }
                
                usersMails.put(usr, emails);
                
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }    
                    }   
                }).start(); */
                        
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
 
}




