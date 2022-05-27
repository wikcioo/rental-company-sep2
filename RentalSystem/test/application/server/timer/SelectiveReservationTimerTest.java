package application.server.timer;

import application.model.reservations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SelectiveReservationTimerTest {
    private SelectiveReservationTimerImplementation implementation;
    private ArrayList<Reservation> reservations;
    @BeforeEach 
    void setUp()
    {
        implementation = new SelectiveReservationTimerImplementation(2000);
        reservations = new ArrayList<>();

        Reservation reservation1 = new Unapproved(1,null,null, LocalDateTime.now().plusDays(7));
        Reservation reservation2 = new Unapproved(2,null,null, LocalDateTime.now().plusDays(7));
        Reservation reservation3 = new Unapproved(3,null,null, LocalDateTime.now().plusDays(7));
        Reservation reservation4 = new Unapproved(4,null,null, LocalDateTime.now().plusDays(7));

        Reservation overdue = new Unapproved(2,null,null, LocalDateTime.now().minusDays(7));
        Reservation approved = new Approved(reservation3,LocalDateTime.now(),null);
        Reservation expired = new Expired(reservation2,LocalDateTime.now());
        Reservation rejected = new Rejected(reservation4,LocalDateTime.now(),null,null);

        reservations.addAll(List.of(new Reservation[]{reservation1,expired,approved,overdue,rejected}));
    }

    @Test
    void can_be_created() {

    }

    @Test
    void set_all_unapproved_can_be_invoked() {
        implementation.setAllUnapprovedReservations(reservations);
    }


    @Test
    void set_all_unapproved_sets_only_unapproved() {
        implementation.setAllUnapprovedReservations(reservations);
        assertTrue(implementation.isExpiring(reservations.get(0)));
        assertFalse(implementation.isExpiring(reservations.get(1)));
        assertFalse(implementation.isExpiring(reservations.get(2)));
        assertTrue(implementation.isExpiring(reservations.get(3)));
        assertFalse(implementation.isExpiring(reservations.get(4)));
    }

    @Test
    void set_all_unapproved_clears_timer() {
        implementation.setAllUnapprovedReservations(new ArrayList<>());
        assertTrue(implementation.isEmpty());
    }

}
