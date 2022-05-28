package application.viewmodel.manager.reservations;

import application.model.models.ManagerModel;
import application.model.models.ModelManager;
import application.model.reservations.Reservation;
import application.model.reservations.Unapproved;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class UnapprovedReservationViewModel implements PropertyChangeListener {
    private final ManagerModel model;
    private final ObjectProperty<ObservableList<Unapproved>> listObjectProperty;
    private final StringProperty errorProperty;

    public UnapprovedReservationViewModel(ManagerModel model) {
        this.model = model;
        listObjectProperty = new SimpleObjectProperty<>();
        model.addListener(ModelManager.RESERVATION_LIST_CHANGED, this);
        listObjectProperty.setValue(FXCollections.observableList(model.getUnapprovedReservations()));
        this.errorProperty = new SimpleStringProperty();
    }

    public void bindReservationList(ObjectProperty<ObservableList<Unapproved>> property) {
        property.bind(listObjectProperty);
    }

    public void approveReservation(Reservation reservation) {
        try {
            model.approveReservation(reservation.getId(), model.getCurrentlyLoggedInUser().getEmail());
        } catch (RemoteException e) {
            errorProperty.set("Server communication error");
        }
    }

    public void rejectReservation(Reservation reservation, String reason) {
        try {
            model.rejectReservation(reservation.getId(),model.getCurrentlyLoggedInUser().getEmail(), reason);
        } catch (RemoteException e) {
            errorProperty.set("Server communication error");
        }
    }

    public void bindErrorLabel(StringProperty property) {
        property.bindBidirectional(errorProperty);
    }

    //TODO I dont think passing the list in a event is a good idea ; better to just call a function to get a list
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.RESERVATION_LIST_CHANGED -> {
                listObjectProperty.setValue(FXCollections.observableList(model.getUnapprovedReservations()));
            }
        }
    }
}
