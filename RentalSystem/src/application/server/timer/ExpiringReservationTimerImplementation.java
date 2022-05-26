package application.server.timer;

import application.model.reservations.Reservation;
import application.util.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class ExpiringReservationTimerImplementation implements ExpiringReservationTimer, PropertyChangeListener {
    private Timer timer;
    private final HashMap<Integer,ExpiringReservation> reservationHashMap;
    private final PropertyChangeSupport pcs;
    private final static String RESERVATION_EXPIRED = "reservation_expired";
    private final int expirationTimeout;

    public ExpiringReservationTimerImplementation(int expirationTimeout) {
        this.expirationTimeout = expirationTimeout;
        timer = new Timer();
        reservationHashMap = new HashMap<>();
        pcs = new PropertyChangeSupport(this);
        PCSExpiringReservation.getInstance().addListener(PCSExpiringReservation.RESERVATION_EXPIRED,this);
    }

    @Override
    public void addReservationToExpire(Reservation reservation) {
        if(!reservation.status().equals(Reservation.type))
            throw new IllegalArgumentException("Only unapproved reservation can expire");

        if(!reservationHashMap.containsKey(reservation.getId())) {
            ExpiringReservation expiringReservation = new ExpiringReservation(reservation);
            reservationHashMap.put(reservation.getId(),expiringReservation);
            timer.schedule(expiringReservation,convertToDate(reservation.getReservationDate().plusSeconds(3)));
        }
    }

    private Date convertToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void cancelExpiration(int id) {
        if(!reservationHashMap.containsKey(id))
            throw new IllegalArgumentException("Id does not belong to any currently expiring reservation");

        reservationHashMap.get(id).cancel();
        reservationHashMap.remove(id);
        timer.purge();
    }

    @Override
    public void cancelExpiration(Reservation reservation) {
        cancelExpiration(reservation.getId());
    }

    @Override
    public boolean isExpiring(Reservation reservation) {
        if(reservationHashMap.containsValue(reservation))
            return true;
        return false;
    }

    @Override
    public void cancelAll() {
        timer.cancel();
        timer = new Timer();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        pcs.firePropertyChange(evt);
    }

    @Override
    public void addListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName,listener);
    }

    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName,listener);
    }
}
