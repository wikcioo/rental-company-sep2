package test.model.reservations;

import application.model.reservations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationListTest {
    private ReservationList reservationList;
    private ArrayList<Reservation> reservations;

    @BeforeEach
    public void setUp() {
        reservationList = new ReservationList();
        reservations = new ArrayList<>();
        // parameters of all inserted reservation objects are irrelevant
        reservations.add(new Returned(0, null, null, null, null, null, null));
        reservations.add(new Reservation(1, null, null, null, null));
        reservations.add(new Reservation(2, null, null, null, null));
        reservations.add(new Approved(3, null, null, null, null, null));
        reservations.add(new Reservation(4, null, null, null, null));
        reservations.add(new Rejected(5, null, null, null, null, null, null));
        reservations.add(new Expired(6, null, null, null, null));
        reservations.add(new Returned(7, null, null, null, null, null, null));
        reservations.add(new Expired(8, null, null, null, null));
        reservationList.setReservationList(reservations);
    }

    @Test
    public void new_reservation_list_has_size_0() {
        reservationList = new ReservationList();
        assertEquals(reservationList.getAll().size(), 0);
    }

    @Test
    public void setting_reservation_list_puts_all_elements_into_reservation_list() {
        assertEquals(reservationList.getAll(), reservations);
    }

    @Test
    public void reservation_list_correctly_returns_unapproved_reservations() {
        ArrayList<Reservation> unapprovedReservations = reservationList.getUnapprovedReservations();
        assertEquals(unapprovedReservations.size(), 3);
    }

    @Test
    public void reservation_list_correctly_returns_approved_reservations() {
        ArrayList<Approved> approvedReservations = reservationList.getApprovedReservations();
        assertEquals(approvedReservations.size(), 1);
    }

    @Test
    public void reservation_list_correctly_returns_rejected_reservations() {
        ArrayList<Rejected> rejectedReservations = reservationList.getRejectedReservations();
        assertEquals(rejectedReservations.size(), 1);
    }

    @Test
    public void reservation_list_correctly_returns_returned_reservations() {
        ArrayList<Returned> returnedReservations = reservationList.getReturnedReservations();
        assertEquals(returnedReservations.size(), 2);
    }

    @Test
    public void reservation_list_correctly_returns_expired_reservations() {
        ArrayList<Expired> expiredReservations = reservationList.getExpiredReservations();
        assertEquals(expiredReservations.size(), 2);
    }
}
