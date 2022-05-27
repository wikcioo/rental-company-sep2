package application.server.timer;

import application.model.reservations.Approved;
import application.model.reservations.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpiringReservationTimerTest {
    private ExpiringReservationTimerImplementation implementation;
    private ArrayList<Reservation> reservations;
    private int expirationTimeout = 1800;
    private FakeListener listener;
    @BeforeEach
    void setUp() {
        this.implementation = new ExpiringReservationTimerImplementation(expirationTimeout);
        this.reservations = new ArrayList<>();
        this.listener = new FakeListener();

        Reservation reservation1 = new Reservation(1,null,null, LocalDateTime.now().plusDays(7));
        Reservation reservation2 = new Reservation(2,null,null, LocalDateTime.now().plusDays(7));

        Reservation reservation3 = new Reservation(3,null,null, LocalDateTime.now().plusDays(7));
        Reservation overdue = new Reservation(4,null,null, LocalDateTime.now().minusDays(7));
        Approved approved = new Approved(reservation3,LocalDateTime.now(),null);
        reservations.addAll(List.of(new Reservation[]{reservation1,reservation2,approved,overdue}));
    }

    @Test
    void can_be_created() {

    }

    @Test
    void is_empty_on_creation() {
        implementation.isEmpty();
    }

    @Test
    void reservation_can_be_added_to_expire() {
        implementation.addReservationToExpire(reservations.get(0));
        assertTrue(implementation.isExpiring(reservations.get(0)));
    }

    @Test
    void listener_can_be_added() {
        implementation.addListener(ExpiringReservationTimer.RESERVATION_EXPIRED,listener);
    }

    @Test
    void listener_can_be_removed() {
        implementation.addListener(ExpiringReservationTimer.RESERVATION_EXPIRED,listener);
        implementation.removeListener(ExpiringReservationTimer.RESERVATION_EXPIRED,listener);
    }

    @Test
    void get_timeout() {
        assertEquals(expirationTimeout,implementation.getExpirationTimeout());
    }


    @Test
    void set_timeout() {
        implementation.setExpirationTimeout(80);
        assertEquals(80,implementation.getExpirationTimeout());
    }

    @Nested
    class NoDelayTest  {
        @BeforeEach
        void setUp() {
            implementation.setExpirationTimeout(0);
            implementation.addListener(ExpiringReservationTimer.RESERVATION_EXPIRED,listener);
            implementation.addReservationToExpire(reservations.get(0));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Test
        void reservation_not_in_timer_after_timeout() {
            assertFalse(implementation.isExpiring(reservations.get(0)));
        }

        @Test
        void timer_notifies_about_expiration_after_timeout() {
            assertEquals(reservations.get(0).getId(), listener.getLastEvt().getNewValue());
        }

        @Test
        void correct_notification_name_notifies_about_expiration_after_timeout() {
            assertEquals(ExpiringReservationTimer.RESERVATION_EXPIRED, listener.getLastEvt().getPropertyName());
        }

    }

    @Test
    void overdue_reservation_expires_immediately() {
        implementation.setExpirationTimeout(0);
        implementation.addReservationToExpire(reservations.get(3));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(implementation.isExpiring(reservations.get(3)));
    }


    @Test
    void reservation_expiration_can_be_cancelled() {
        int smallTimeout = 1;
        implementation.setExpirationTimeout(smallTimeout);
        implementation.addListener(ExpiringReservationTimer.RESERVATION_EXPIRED,listener);
        implementation.addReservationToExpire(reservations.get(0));
        implementation.cancelExpiration(reservations.get(0));
        try {
            Thread.sleep(smallTimeout*1000 + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        implementation.addReservationToExpire(reservations.get(0));
        implementation.cancelExpiration(reservations.get(0).getId());
        assertEquals(null, listener.getLastEvt());
    }


    @Test
    void reservation_not_expiring_after_cancellation() {
        implementation.addReservationToExpire(reservations.get(0));
        implementation.cancelExpiration(reservations.get(0).getId());
        assertFalse(implementation.isExpiring(reservations.get(0)));
    }

    @Test
    void two_reservations_can_be_added_to_expire() {
        implementation.addReservationToExpire(reservations.get(0));
        implementation.addReservationToExpire(reservations.get(1));
        assertTrue(implementation.isExpiring(reservations.get(0)));
        assertTrue(implementation.isExpiring(reservations.get(1)));
    }

    @Test
    void two_reservations_can_be_added_to_expire_and_one_cancelled() {
        implementation.addReservationToExpire(reservations.get(0));
        implementation.addReservationToExpire(reservations.get(1));
        implementation.cancelExpiration(reservations.get(0));
        assertFalse(implementation.isExpiring(reservations.get(0)));
        assertTrue(implementation.isExpiring(reservations.get(1)));
    }

    @Test
    void two_reservations_can_be_added_to_expire_and_both_cancelled() {
        implementation.addReservationToExpire(reservations.get(0));
        implementation.addReservationToExpire(reservations.get(1));
        implementation.cancelAll();
        assertFalse(implementation.isExpiring(reservations.get(0)));
        assertFalse(implementation.isExpiring(reservations.get(1)));
    }

    @Test
    void reservation_not_expiring_cannot_be_cancelled() {
        assertThrows(IllegalArgumentException.class,() -> implementation.cancelExpiration(reservations.get(0)));
    }

    @Test
    void different_type_cannot_be_set_to_expire() {
        assertThrows(IllegalArgumentException.class,() -> implementation.addReservationToExpire(reservations.get(2)));
    }

}
