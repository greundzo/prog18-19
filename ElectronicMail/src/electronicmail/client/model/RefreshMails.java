package electronicmail.client.model;

import electronicmail.publics.Email;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class RefreshMails implements Runnable {

    private final Object LOCK;
    private final ClientModel model;
    private boolean newMail;
    @FXML
    private final ListView eList;
    @FXML
    private final TextArea readArea;

    public RefreshMails(ClientModel mod, ListView list, TextArea rAr) {
        LOCK = new Object();
        model = mod;
        eList = list;
        readArea = rAr;
    }

    public void frameRefresh(ObservableList<Email> obs) {
        eList.setItems(obs);
        eList.refresh();
        eList.setVisible(true);
    }
    
    @Override
    public void run() {
        try {
            synchronized(LOCK) {
                model.refreshRequest();
                Thread.sleep(100);
                ObservableList<Email> oldMails = model.getObMails();
                eList.setItems(oldMails);
                eList.refresh();
                eList.setVisible(true); 

                while(true)  {
                    Thread.sleep(1000);
                    synchronized (LOCK) {
                        model.refreshRequest();
                        ObservableList<Email> refreshed = model.getObMails();

                        if(refreshed.size() > oldMails.size()) {
                            Platform.runLater( () -> {
                                newMail = true;
                                eList.setItems(refreshed);
                                eList.refresh();
                                eList.setVisible(true);
                                if (newMail == true) {
                                    model.alert("New mail received!");
                                    newMail = !newMail; 
                                }    
                                oldMails.clear();
                                oldMails.addAll(refreshed);
                                //System.out.println(refreshed.size() + "AND" + oldMails.size());
                            });
                        } else 
                            if (refreshed.size() < oldMails.size()) {
                            Platform.runLater( () -> {
                                readArea.clear();
                                eList.setItems(refreshed);
                                eList.refresh();
                                eList.setVisible(true);
                                oldMails.clear();
                                oldMails.addAll(refreshed);
                            });
                        }                       
                    }
                }
            }
        } catch (IOException | IllegalStateException | InterruptedException e) {}
    }
}
