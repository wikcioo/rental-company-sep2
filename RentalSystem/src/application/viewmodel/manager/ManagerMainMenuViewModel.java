package application.viewmodel.manager;

import application.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.rmi.RemoteException;

public class ManagerMainMenuViewModel {
    private final Model model;
    private final StringProperty loggedUserProperty;

    public ManagerMainMenuViewModel(Model model) {
        this.model = model;
        this.loggedUserProperty = new SimpleStringProperty();
    }

    public void bindLoggedUser(StringProperty property) {
        loggedUserProperty.bindBidirectional(property);
    }

    public int getCurrentExpirationTimeout() {
        try {
            return model.getExpirationTimeout();
        } catch (RemoteException e) {
            return -1;
        }
    }

    public boolean setCurrentExpirationTimeout(int expirationTimeout) {
        try {
            model.setExpirationTimeout(expirationTimeout);
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    public void displayLoggedUser() {
        loggedUserProperty.set("Logged as: " + model.getCurrentlyLoggedInUser().getEmail());
    }
}
