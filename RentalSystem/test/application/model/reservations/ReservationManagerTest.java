package application.model.reservations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationManagerTest {
    private ReservationManager reservationManager;
    private ArrayList<Reservation> reservations;

    @BeforeEach
    public void setUp() {
        reservationManager = new ReservationManager();
        reservations = new ArrayList<>();
        // parameters of all inserted reservation objects are irrelevant
        reservations.add(new Returned(0, null, null, null, null, null, null));
        reservations.add(new Unapproved(1, null, null, null, null));
        reservations.add(new Unapproved(2, null, null, null, null));
        reservations.add(new Approved(3, null, null, null, null, null));
        reservations.add(new Unapproved(4, null, null, null, null));
        reservations.add(new Rejected(5, null, null, null, null, null, null));
        reservations.add(new Expired(6, null, null, null, null));
        reservations.add(new Returned(7, null, null, null, null, null, null));
        reservations.add(new Expired(8, null, null, null, null));
        reservationManager.setReservationList(reservations);
    }

    @Test
    public void new_reservation_list_has_size_0() {
        reservationManager = new ReservationManager();
        assertEquals(0, reservationManager.getAll().size());
    }

    @Test
    public void setting_reservation_list_puts_all_elements_into_reservation_list() {
        assertEquals(reservations, reservationManager.getAll());
    }

    @Test
    public void reservation_list_correctly_returns_unapproved_reservations() {
        ArrayList<Unapproved> unapprovedReservations = reservationManager.getUnapprovedReservations();
        assertEquals(3, unapprovedReservations.size());
    }

    @Test
    public void reservation_list_correctly_returns_approved_reservations() {
        ArrayList<Approved> approvedReservations = reservationManager.getApprovedReservations();
        assertEquals(1, approvedReservations.size());
    }

    @Test
    public void reservation_list_correctly_returns_rejected_reservations() {
        ArrayList<Rejected> rejectedReservations = reservationManager.getRejectedReservations();
        assertEquals(1, rejectedReservations.size());
    }

    @Test
    public void reservation_list_correctly_returns_returned_reservations() {
        ArrayList<Returned> returnedReservations = reservationManager.getReturnedReservations();
        assertEquals(2, returnedReservations.size());
    }

    @Test
    public void reservation_list_correctly_returns_expired_reservations() {
        ArrayList<Expired> expiredReservations = reservationManager.getExpiredReservations();
        assertEquals(2, expiredReservations.size());
    }
}
