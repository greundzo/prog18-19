package electronicmail.client.model;

import electronicmail.publics.Email;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class RefreshMails implements Runnable {

    private final Object LOCK;
    private final ClientModel model;
    private final ListView eList;
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
            model.refreshRequest();
            Thread.sleep(500);
            ObservableList<Email> oldMails = model.getObMails();
            frameRefresh(oldMails);
            
            while(true)  {
                Thread.sleep(1000);
                synchronized (LOCK) {
                    model.refreshRequest();
                    ObservableList<Email> refreshed = model.getObMails();
                    if(refreshed.size() > oldMails.size()) {
                        frameRefresh(refreshed);
                        model.alert("New mail received!");
                        oldMails.addAll(refreshed);
                    } else {
                        readArea.clear();
                        frameRefresh(refreshed);
                        oldMails.addAll(refreshed);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {}
    }
}
