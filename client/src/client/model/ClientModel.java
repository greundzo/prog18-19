/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author greundzo
 */
public class ClientModel {
    public static ClientModel model;
    private static String userName;
    
    
    public ClientModel(String usr) {
        userName = usr;
    }
    
    
    public static synchronized ClientModel getInstance() {
      if (model == null)
          model = new ClientModel(null);
      return model;
    }

    
    public static String getUser() {
        return userName;
    }
    
    public static boolean setUser(String user) {
        if(userName == null) {
            userName = user;
            return true;
        }
        return false;
    }
    
    public void resetUser() {
        userName = null;
    }
    /*
    public static boolean checkUser(String user) {
        for(int i = 0; i < 3; i++) {
            if(user.equals(users[i])){
                return true;
            }
        }
        return false;
    }*/
}