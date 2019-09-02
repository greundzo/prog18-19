/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

/**
 *
 * @author greundzo
 */
public class ClientModel extends Observable {
    private static String userName;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    public ClientModel() {
        userName = null;
    }
    
    public String getUser() {
        return userName;
    }
    
    /**
     *
     * @param user
     * @return true se riesce a settare il nome
     */
    public boolean setUser(String user) {
        if(userName == null) {
            userName = user;
            return true;
        }
        return false;
    }
    
    public void resetUser() {
        userName = null;
    }
    
    /**
     * Crea uno stream e comunica al server che il client sta eseguendo il login.
     * @param s è il socket generato dal controller durante il login.
     * @return true se tutto è riuscito, messaggio di errore in caso contrario.
     */
    public boolean logRequest(Socket s) {
        socket = s;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(this.getUser());
            out.writeObject("in");
            return true; // deve tornare conferma del log o errore perché utente già dentro
        } catch (IOException e){
            e.printStackTrace();
        }   
        return false;
    }
    
    /**
     * Comunica al server che il client sta eseguendo il logout, poi chiude lo stream.
     * @return true se tutto è andato bene
     * @throws IOException se qualcosa è andato storto
     */
    public boolean outRequest() throws IOException {
        try {
            Object obj = this.getUser() + ",out";
            out.writeObject(obj);
            out.close();
            socket.close();
            return true;
        } catch (IOException e) {
            throw new IOException();
        }
    }
}