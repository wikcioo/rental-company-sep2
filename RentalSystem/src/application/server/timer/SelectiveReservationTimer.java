package application.server.timer;

import application.model.reservations.Reservation;

import java.util.ArrayList;

public interface SelectiveReservationTimer extends ExpiringReservationTimer{

    /**Cancels all current timers and iterates through the arraylist. Adds all unapproved reservations, the rest is ignored
     * @param reservations reservations with different current statuses
     */
    void setAllUnapprovedReservations(ArrayList<Reservation> reservations);
}
