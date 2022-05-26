package application.server.timer;

import application.model.reservations.Reservation;

import java.util.ArrayList;

public interface SelectiveReservationTimer extends ExpiringReservationTimer{
    void setAllUnapprovedReservations(ArrayList<Reservation> reservations);
}
