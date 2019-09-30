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
    
    @Override
    public void run() {
        try {
            if (model.getUser() != null) {
                synchronized(LOCK) {
                    model.refreshRequest();
                    Thread.sleep(300);
                    ObservableList<Email> oldMails = model.getObMails();
                    eList.setItems(oldMails);
                    eList.refresh();
                    eList.setVisible(true); 

                    while(true)  {

                        Thread.sleep(1500);
                        synchronized (LOCK) {
                            model.refreshRequest();
                            ObservableList<Email> refreshed = model.getObMails();

                            if(refreshed.size() > oldMails.size()) {
                                Platform.runLater( () -> {
                                    eList.setItems(refreshed);
                                    eList.refresh();
                                    eList.setVisible(true);
                                    if (model.getUser() != null) {
                                        if (model.getConfirmed() == false) {
                                            model.confirm();
                                            model.alert("New mail received!");                                     
                                        }   
                                    }    
                                    oldMails.clear();
                                    oldMails.addAll(refreshed);
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
            }    
        } catch (NullPointerException | IOException | IllegalStateException | InterruptedException e) {}
    }
}
