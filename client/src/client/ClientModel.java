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
    private static String userName = null;
    private final static String users[] = {"user1@di.unito.it", "user2@di.unito.it", "user3@di.unito.it"};
    
    public static synchronized ClientModel getInstance( ) {
      if (model == null)
          model = new ClientModel();
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
    
    public static boolean checkUser(String user) {
        for(int i = 0; i < 3; i++) {
            if(user.equals(users[i])){
                return true;
            }
        }
        return false;
    }
}