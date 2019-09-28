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

/**
 * 
 * @author greundzo
 */

public class ServerModel extends Observable {
    
    
    public static int maxId = 0; // Id massimo email presente nel server
    public static final Object LOCKID = new Object(); // lock usato per accedere in mutua esclusione alla variabile maxId
    //2 righe superiori aggiunte da wally
    
    private ArrayList<Email> emails;
    private final HashMap<String, ArrayList<Email>> usersMails; 
    private final HashMap<String, Integer> accountMaxId;
    private HandleRequest connected;
    private final String PATH = "./src/electronicmail/publics/db/";
    private BufferedWriter from;
    private BufferedWriter toWhere;
            
    public ServerModel() {
        emails = new ArrayList<>();
        usersMails = new HashMap<>();
        accountMaxId = new HashMap<>();
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
                deleteEmail(usr, email);
                setChanged();
                notifyObservers(usr + " has deleted an email");
                break;
            case "refresh":
                refresh(usr);
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
            String a = whos.get(0);
            
            String toS[] = a.split(";"); // splitto e trovo tutti i destinatari
            
            String inf = em.toString();
            String[] support = inf.split("§"); //splitto e ho tutti i campi
            
            String info[] = {support[0], a, support[1], support[2], support[3]}; // unisco le stringhe
            
            File sentMails = new File(PATH + "sent/" + usr + ".txt");
            
            if (!sentMails.exists()){
                sentMails.createNewFile();
            }
            
            FileWriter sent = new FileWriter(sentMails, true);
            from = new BufferedWriter(sent);
            
            synchronized(LOCKID) {
                writeflush(info, from, usr);
            }
            
            for (String to : toS) {
                File recMails = new File(PATH + "received/" + to + ".txt");
                
                if (!recMails.exists()) {
                    recMails.createNewFile();
                }
                
                FileWriter received = new FileWriter(recMails, true);
                       
                toWhere = new BufferedWriter(received);
                  
                synchronized (LOCKID) {
                    writeflush(info, toWhere, to);
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
     * @param usr
     * @throws IOException
     */
    public void writeflush(String a[], BufferedWriter buff, String usr) throws IOException {
        if (accountMaxId.get(usr) != null) {
            maxId = accountMaxId.get(usr);
        } else {
            maxId = 0;
        }
        buff.append(++maxId + "§§" + a[0] + "§§" + a[1] + "§§" + a[2] + "§§" + a[3] + "§§" + a[4] + "§§" + EOF + "\n");
        accountMaxId.put(usr, maxId);
        //buff.flush();
    }

    public void refresh(String usr) {
             
        synchronized (LOCKID) {
            try {
                emails = new ArrayList<>();
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
             
                    if (Integer.parseInt(line[0]) > maxId) {
                        maxId = Integer.parseInt(line[0]);
                    }
                    
                    Email em = new Email(line[1], to, line[3], line[4]);
                    
                    em.setId(line[0]);
                    em.setDate(line[5]);
                    
                    
                    emails.add(em);
                    
                }                
                usersMails.put(usr, emails);    
                accountMaxId.put(usr, maxId);
                
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public synchronized void deleteEmail(String usr, Email em) {       
        
        try {
            File temp = new File(PATH + "received/" + "temp.txt");
            File received = new File(PATH + "received/" + usr + ".txt");
            temp.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(received));
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp, true));
            
            String ln;
            while ((ln = br.readLine()) != null) {
                String[] line = ln.split("§§");
                if (!line[0].equals(em.getId())) {
                    bw.append(ln + "\n");
                    //bw.flush();
                }
            }
            br.close();
            bw.close();           
            
            received.delete();
            boolean renameTo = temp.renameTo(received);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String maxId(String usr)throws IOException {
        
        
        FileReader fr = new FileReader(PATH + "received/" + usr + ".txt");
        BufferedReader br = new BufferedReader(fr);
        
        int lines = 0;
        while(br.readLine()!= null){
            lines++;    
        }
        return Integer.toString(lines);
        
        
        
        // questo funziona solo nel caso in cui ogni utenta ha una directory propria 
        // lo lascio solo perché non è male come metodo alternativo e potrebbe tornare utile
        //nella vita *io che faccio okay con la mano*  
        //Path path = Paths.get(PATH+"received/"+ usr );
        //long lines = usr.lines(path).count(); 
    }
}




