package application.viewmodel.manager;

import application.model.Model;
import application.model.ModelManager;
import application.model.equipment.Equipment;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

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
