package application.viewmodel;

import application.model.Model;
import application.model.ModelManager;
import application.model.Reservation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.List;

public class ApprovedReservationViewModel implements PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<Reservation>> listObjectProperty;


    public ApprovedReservationViewModel(Model model) {
        this.model = model;
        listObjectProperty = new SimpleObjectProperty<>();
        model.addListener(ModelManager.RESERVATION_LIST_CHANGED, this);
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

    public void showApprovedReservations() {
        try {
            listObjectProperty.setValue(FXCollections.observableList(model.getApprovedReservationList()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAllReservations() {
        try {
            listObjectProperty.setValue(FXCollections.observableList(model.getReservationList()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.RESERVATION_LIST_CHANGED -> {
                listObjectProperty.setValue(FXCollections.observableList((List<Reservation>) evt.getNewValue()));
            }
        }
    }
}
