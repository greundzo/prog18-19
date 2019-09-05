/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import static jdk.nashorn.internal.parser.TokenType.EOF;
import publics.Email;

/**
 *
 * @author greundzo
 */
public class ClientModel extends Observable {
    private static String userName;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ArrayList<Email> emails;
    private ObservableList<Email> emList;
    
    public ClientModel() {
        userName = null;     
    }
    
    public String getUser() {
        return userName;
    }
    
    /**
     * @param user
     */
    public void setUser(String user) {
        userName = user;
    }
    
     public void alert(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(Alert.AlertType.WARNING.toString());
        alert.setHeaderText(text);
        alert.setContentText("");
        alert.showAndWait();
    }
    
    public void request(Object rqs, Object obj) throws IOException {       
        socket = new Socket("localhost", 8189);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(this.getUser());
        out.flush();           
        out.writeObject(rqs);
        out.flush();
        out.writeObject(obj);
        out.flush();
        out.close();
        socket.close();           
    }
    
    /**
     * Crea uno stream e comunica al server che il client sta eseguendo il login.
     * @throws java.io.IOException
     */
    public void logRequest() throws IOException {
        request("in", null); 
    }
    
    /**
     * Comunica al server che il client sta eseguendo il logout, poi chiude lo stream.
     * @throws IOException se qualcosa è andato storto
     */
    public void outRequest() throws IOException {
        request("out", null);
    }
    
    public void sendRequest(Object obj) throws IOException {
        FileWriter wr = new FileWriter("../publics/db/" + userName + "Sent.txt");
        Email email = (Email) obj;
        String mail[] = {email.id(), email.from(), email.to(), email.subject(), email.txt(), email.date()};
        writeMail(wr, mail);
        request("new", obj);
        setChanged();
        notifyObservers();
    }
    
    public void setEmails(ArrayList<Email> ems) {
        emails = ems;
        if (emList == null) {
            emList = FXCollections.observableArrayList(emails);
        }    
    }
    
    public ArrayList<Email> getMails() {
        return emails;
    }
    
    public ObservableList<Email> getObMails() {
        return emList;
    }
    
    public void writeMail(FileWriter wr, String a[]) throws IOException {
        for (String a1 : a) {
            wr.write(a1 + "§§");
        }
        wr.write(EOF + "\n");
        wr.close();
    }
}