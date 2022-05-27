package application.server.timer;

import application.model.reservations.Reservation;
import application.util.NamedPropertyChangeSubject;

public interface ExpiringReservationTimer extends NamedPropertyChangeSubject {
    String RESERVATION_EXPIRED = "reservation_expired";

    /**Adds a reservation that will expire after amount of time given in the constructor
     *
     * @param reservation
     * @throws IllegalArgumentException if given reservation is not unapproved
     */
    void addReservationToExpire(Reservation reservation) throws IllegalArgumentException;

    /**Cancels the expiration of the reservation with given id
     * @param id id of the reservation
     * @throws IllegalArgumentException if there is no reservation that is currently set to expire with given id
     */
    void cancelExpiration(int id) throws IllegalArgumentException;

    /**Cancels the expiration of the reservation
     * @param reservation reservation that is set to expire
     * @throws IllegalArgumentException if given reservation is not currently set to expire
     */
    void cancelExpiration(Reservation reservation) throws IllegalArgumentException;

    /**Checks whether the given reservation is currently set to expire
     * @param reservation
     * @return true if given reservation is set to expire, otherwise false
     */
    boolean isExpiring(Reservation reservation);

    /**Cancels the entire timer  and creates a new one subsequently canceling all scheduled expirations
     *
     */
    void cancelAll();
}
