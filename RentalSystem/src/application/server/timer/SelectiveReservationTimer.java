package application.server.timer;

import application.model.reservations.Reservation;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class SelectiveReservationTimer implements ExpiringReservationTimer {
    private final ExpiringReservationTimer expiringReservationTimer;

    public SelectiveReservationTimer(int expirationTimeout) {
        expiringReservationTimer = new ExpiringReservationTimerImplementation(expirationTimeout);
    }

    @Override
    public void addReservationToExpire(Reservation reservation) {
        expiringReservationTimer.addReservationToExpire(reservation);
    }

    @Override
    public void cancelExpiration(int id) {
        expiringReservationTimer.cancelExpiration(id);
    }

    @Override
    public void cancelExpiration(Reservation reservation) {
        expiringReservationTimer.cancelExpiration(reservation);
    }

    @Override
    public boolean isExpiring(Reservation reservation) {
        return expiringReservationTimer.isExpiring(reservation);
    }

    @Override
    public void cancelAll() {
        expiringReservationTimer.cancelAll();
    }

    @Override
    public void addListener(String propertyName, PropertyChangeListener listener) {
        expiringReservationTimer.addListener(propertyName, listener);
    }

    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener) {
        expiringReservationTimer.removeListener(propertyName, listener);
    }

    public void setAllUnapprovedReservations(ArrayList<Reservation> reservations) {
        cancelAll();
        for(Reservation reservation : reservations) {
            if(!reservation.status().equals(Reservation.type))
                continue;
            addReservationToExpire(reservation);
        }
    }

}
