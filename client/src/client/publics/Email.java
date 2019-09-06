/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.publics;

import java.io.Serializable;

/**
 *
 * @author greundzo
 */
public class Email implements Serializable {
    
    private final String id;
    private final String from;
    private final String to;
    private final String subject;
    private final String text;
    private final String date;
    
    /**
     *
     * @param fromWho
     * @param toWho
     * @param theSubject
     * @param txt
     */
    public Email(String fromWho, String toWho, String theSubject, String txt) {
        id = java.time.LocalTime.now() + "";
        from = fromWho;
        to = toWho;
        subject = theSubject;
        text = txt;
        date = java.time.LocalDateTime.now() + "";
    }
    
    public String id() {
        return id;
    }
    
    public String from() {
        return from;
    }
    
    public String to() {
        return to;
    }
    
    public String subject() {
        return subject;
    }
    
    public String txt() {
        return text;
    }
    
    public String date() {
        return date;
    }
}
