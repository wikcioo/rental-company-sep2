package application.viewmodel.manager;

import application.model.reservations.Approved;
import application.model.Model;
import application.model.ModelManager;
import application.model.reservations.Reservation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class ApprovedReservationViewModel implements PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<Approved>> listObjectProperty;
    private final StringProperty errorProperty;

    public ApprovedReservationViewModel(Model model) {
        this.model = model;
        listObjectProperty = new SimpleObjectProperty<>();
        listObjectProperty.setValue(FXCollections.observableList(model.getApprovedReservations()));
        model.addListener(ModelManager.RESERVATION_LIST_CHANGED, this);
        this.errorProperty = new SimpleStringProperty();
    }

    public void bindReservationList(ObjectProperty<ObservableList<Approved>> property) {
        property.bind(listObjectProperty);
    }

    public void bindErrorLabel(StringProperty property) {
        property.bindBidirectional(errorProperty);
    }

    //TODO: Check if it should be done like this. Didn't want to create 2 separate property events, but taking list this way might not be ideal.
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.RESERVATION_LIST_CHANGED -> {
                listObjectProperty.setValue(FXCollections.observableList(model.getApprovedReservations()));
            }
        }
    }

    public void removeReservation(Reservation reservation) {
        try {
            model.returnReservation(reservation.getId());
        } catch (RemoteException e) {
            errorProperty.set("Server communication error");
        }
    }
}
