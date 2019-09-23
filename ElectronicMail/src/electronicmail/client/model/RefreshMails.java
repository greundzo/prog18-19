package electronicmail.client.model;

public class RefreshMails implements Runnable {

	private ClientModel LOCK;
	private ClientModel model;
	private ListView eList;
	private ObservableList<Email> refreshed;
	private ObservableList<Email> oldMails;

	public RefreshMails(ClientModel mod, ListView list) {
		model = mod;
		eList = list;
	}

	@Override
	public void run() {
		try {
			oldMails = new ObservableList<>();
			refreshed = new ObservableList<>();

			while(true)  {
				Thread.sleep(1000);
				synchronized (LOCK) {
					model.refreshRequest();
					oldMails = refreshed;
					refreshed = model.getObMails();
					if(refreshed.toString().length > oldMails.toString().length) {
						eList.setItems(refreshed);
						eList.refresh();
						eList.setVisible(true);
						model.alert("New mail received!");
					} else {
						readArea.clear();
            			eList.setItems(refreshed);
            			eList.refresh();
					}
				}
			}
		} catch (InterruptedException e) {}
	}
}
