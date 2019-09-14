/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicmail.publics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author wallahd
 * @author greundzo
 */
public class Email extends java.util.Observable implements Serializable {
    
    private String id;
    private final String from;
    private final String to;
    private final String subject;
    private final String text;
    private String date;
    private boolean read;
    
    /**
     *
     * @param fromWho
     * @param toWho
     * @param theSubject
     * @param txt
     */
    public Email(String fromWho, String toWho, String theSubject, String txt) {
        id = null;
        from = fromWho;
        to = toWho;
        subject = theSubject;
        text = txt;
        date = java.time.LocalDateTime.now() + "";
        read = false;
    }
    
    public String id() {
        return id;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String from() {
        return from;
    }
    
    public String getFrom() {
        return from;
    }
    
    public String to() {
        return to;
    }
    
    public String getTo() {
        return to;
    }
    
    public String subject() {
        return subject;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public String txt() {
        return text;
    }
    
    public String getTxt() {
        return text;
    }
    
    public String getDate() {
        return date;
    }
    
    public String date() {
        return date;
    }
    
    public boolean getwasRead() {
        return read;
    }
    
    public boolean wasRead() {
        return read;
    }
    
    public void hasBeenRead() {
        read = !read;
    }
    
    public String[] getAll() {
        String all[] = {id, from, to, subject, text, date};
        return all;
    }
    
    public void setDate(String d) {
        date = d;
    }
}
