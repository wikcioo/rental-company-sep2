package application.server.timer;


import application.model.reservations.Reservation;
import application.model.reservations.Unapproved;

import java.util.TimerTask;

public class ExpiringReservation extends TimerTask {
    private final Unapproved unapprovedReservation;
    private final PCSExpiringReservation pcs;

    public ExpiringReservation(Unapproved unapprovedReservation) {
        this.unapprovedReservation = unapprovedReservation;
        this.pcs = PCSExpiringReservation.getInstance();
    }

    public Reservation getUnapprovedReservation() {
        return unapprovedReservation;
    }

    @Override
    public void run() {
        pcs.alertAboutExpiration(unapprovedReservation.getId());
    }
}
