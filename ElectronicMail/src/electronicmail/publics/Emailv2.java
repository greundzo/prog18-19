

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Emailv2 extends java.util.Observable implements Serializable{

    private String id;
    private ArrayList<String> from;
    private String to;
    private String subject;
    private String text;
    private Date date;

    public Emailv2(String id, ArrayList<String> from, String to, String subject, String text, Date date) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setIdEmail(String id) {
        this.id = id;
    }

    public ArrayList<String> getFrom() {
        return from;
    }

    public void setFrom(ArrayList<String> from) {
        this.from = from;
    }

    public String getDestinatari() {
        return to;
    }

    public void setDestinatari(String to) {
        this.to = to;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return(getFrom().get(0)+"\t" + getSubject()+"\t\t" + getDate());
    }

}
