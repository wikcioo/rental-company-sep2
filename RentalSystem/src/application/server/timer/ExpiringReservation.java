package application.server.timer;


import application.model.reservations.Reservation;

import java.beans.PropertyChangeSupport;
import java.util.TimerTask;

public class ExpiringReservation extends TimerTask {
    private final Reservation unapprovedReservation;
    private final PCSExpiringReservation pcs;

    public ExpiringReservation(Reservation unapprovedReservation) {
        this.unapprovedReservation = unapprovedReservation;
        this.pcs = PCSExpiringReservation.getInstance();
    }

    @Override
    public void run() {
        pcs.alertAboutExpiration(unapprovedReservation.getId());
    }
}
