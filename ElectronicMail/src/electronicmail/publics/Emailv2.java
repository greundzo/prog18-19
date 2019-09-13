

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
        this.idEmail = id;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.date = date;
    }

    public String getId() {
        return idEmail;
    }

    public void setIdEmail(String id) {
        this.id = id;
    }

    public ArrayList<String> getfrom() {
        return from;
    }

    public void setfrom(ArrayList<String> from) {
        this.from = from;
    }

    public String getDestinatari() {
        return to;
    }

    public void setDestinatarii(String to) {
        this.to = to;
    }

    public String getsubject() {
        return subject;
    }

    public void setsubject(String subject) {
        this.subject = subject;
    }

    public String gettext() {
        return text;
    }

    public void settext(String text) {
        this.text = text;
    }

    public Date getdate() {
        return date;
    }

    public void setdate(Date date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return(getfrom().get(0)+"\t" + getsubject()+"\t\t" + getdate());
    }

}
