/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicmail.client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import electronicmail.publics.Email;


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
    
    public void request(String rqs, Email obj) throws IOException {
        socket = new Socket("localhost", 8189);
        new Thread(() -> {                    
            CreateRequest cr = new CreateRequest(this, userName, rqs, obj, socket);
            new Thread(cr).start();
        }).start();
    }
    
    /**
     * Crea uno stream e comunica al server che il client sta eseguendo il login.
     * @throws java.io.IOException
     */
    public void logRequest() throws IOException {
        request("in", null); 
    }
    
    public void refreshRequest() throws IOException {
        request("refresh", null);
    }
    
    /**
     * Comunica al server che il client sta eseguendo il logout, poi chiude lo stream.
     * @throws IOException se qualcosa è andato storto
     */
    public void outRequest() throws IOException {
        request("out", null);
    }
    
    public void sendRequest(Email obj) throws IOException {   
        request("newmail", obj);        
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
    
}