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
    private String username;
    
    public boolean checkUser(String user) throws FileNotFoundException, IOException {
        BufferedReader fileInputReader = new BufferedReader(new FileReader("users.txt"));
        String line = null;
        while((line = fileInputReader.readLine()) != null) {
            if(user==line) {
                return true;
            }
        }
        return false;
    }
}