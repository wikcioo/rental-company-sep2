package application.server.timer;

import application.model.reservations.Reservation;
import application.util.NamedPropertyChangeSubject;

public interface ExpiringReservationTimer extends NamedPropertyChangeSubject {
    String RESERVATION_EXPIRED = "reservation_expired";
    void addReservationToExpire(Reservation reservation);
    void cancelExpiration(int id);
    void cancelExpiration(Reservation reservation);
    boolean isExpiring(Reservation reservation);
    void cancelAll();
}
