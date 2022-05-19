package application.viewmodel.manager;

import application.model.*;
import application.model.reservations.Reservation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.List;

public class ReservationViewModel implements PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<Reservation>> listObjectProperty;


    public ReservationViewModel(Model model) {
        this.model = model;
        listObjectProperty = new SimpleObjectProperty<>();
        model.addListener(ModelManager.RESERVATION_LIST_CHANGED, this);
        listObjectProperty.setValue(FXCollections.observableList((List<Reservation>) model.getUnapprovedReservations()));
    }

    public void bindReservationList(ObjectProperty<ObservableList<Reservation>> property) {
        property.bind(listObjectProperty);
    }

    public void approveReservation(Reservation reservation) {
        try {
            model.approveReservation(reservation);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO I dont think passing the list in a event is a good idea ; better to just call a function to get a list
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.RESERVATION_LIST_CHANGED -> {
                listObjectProperty.setValue(FXCollections.observableList((List<Reservation>) model.getUnapprovedReservations()));
            }
        }
    }
}
