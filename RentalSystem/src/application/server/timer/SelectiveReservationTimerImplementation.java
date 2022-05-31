package application.server.timer;

import application.model.reservations.Reservation;
import application.model.reservations.Unapproved;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class SelectiveReservationTimerImplementation implements SelectiveReservationTimer {
    private final ExpiringReservationTimer expiringReservationTimer;

    public SelectiveReservationTimerImplementation(int expirationTimeout) {
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

    @Override
    public void setAllUnapprovedReservations(ArrayList<Reservation> reservations) {
        cancelAll();
        for(Reservation reservation : reservations) {
            if(!reservation.status().equals(Unapproved.status))
                continue;
            addReservationToExpire(reservation);
        }
    }

    @Override
    public boolean isEmpty() {
        return expiringReservationTimer.isEmpty();
    }

    @Override
    public void setExpirationTimeout(int timeout) {
        expiringReservationTimer.setExpirationTimeout(timeout);
    }

    @Override
    public int getExpirationTimeout() {
        return expiringReservationTimer.getExpirationTimeout();
    }
}
