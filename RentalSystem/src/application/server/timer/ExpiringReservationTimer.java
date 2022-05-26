package application.server.timer;

import application.model.reservations.Reservation;
import application.util.NamedPropertyChangeSubject;

public interface ExpiringReservationTimer extends NamedPropertyChangeSubject {
    void addReservationToExpire(Reservation reservation);
    void cancelExpiration(int id);
    void cancelExpiration(Reservation reservation);
    boolean isExpiring(Reservation reservation);
    void cancelAll();
}
