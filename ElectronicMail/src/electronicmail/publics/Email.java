package electronicmail.publics;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Bollattino Matteo (mat 847576)
 * @author Boggio Gianluca (mat 765042)
 * @author Calo' Ramiro (mat 835678)
 * @author Carena Alessandro (mat 817103)
 */

public class Email extends java.util.Observable implements Serializable{
    
    private String id;
    private ArrayList<String> to;
    private String from;
    private String subject;
    private String text;
    private String date;
    private boolean read;

    public Email(String from, ArrayList<String> to, String subject, String text) {
        this.id = null;
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.text = text;
        this.date = java.time.LocalDateTime.now() + "";
        this.read = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public boolean hasBeenRead() {
        return read;
    }
    
    public void setRead() {
        read = !read;
    }
    
    @Override
    public String toString() {
        return getFrom() + "," + getSubject() + "," + getText() + "," + getDate();
    }
    
}