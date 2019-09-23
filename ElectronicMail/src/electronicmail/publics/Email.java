package electronicmail.publics;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author greundzo
 * @author beduinofbarriera
 */

public class Email extends java.util.Observable implements Serializable{
    
    private String id;
    private ArrayList<String> to;
    private String from;
    private String subject;
    private String text;
    private String date;

    public Email(String from, ArrayList<String> to, String subject, String text) {
        this.id = null;
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.text = text;
        this.date = java.time.LocalDateTime.now() + "";
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
    
    @Override
    public String toString() {
        return getFrom() + "§" + getSubject() + "§" + getText() + "§" + getDate();
    }
      
}