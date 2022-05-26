package application.viewmodel.rentee;

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

public class RenteeMainMenuViewModel {
    private final Model model;
    private final StringProperty errorProperty;
    private final StringProperty loggedUserProperty;

    public RenteeMainMenuViewModel(Model model) {
        this.model = model;
        this.errorProperty = new SimpleStringProperty();
        this.loggedUserProperty = new SimpleStringProperty();
    }

    public void bindLoggedUser(StringProperty property) {
        loggedUserProperty.bindBidirectional(property);
    }

    public void displayLoggedUser() {
        loggedUserProperty.set("Logged as: " + model.getCurrentlyLoggedInUser().getEmail());
    }
}
