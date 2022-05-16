package test.model;

import application.model.Equipment;
import application.model.Reservation;
import application.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    private final User rentee = new User("Selina", "Ceban", "11223344", "selina@gmail.com", "zxc");
    private final Equipment equipment = new Equipment(69, "Arizona", "Vacuum Cleaner", true);
    private Reservation reservation = new Reservation(rentee, equipment, LocalDateTime.now().plusDays(14));

    @Test
    void canBeApproved() {
        reservation.approve();
        assertTrue(reservation.isApproved());
    }

    @Test
    void getRentee() {
        assertEquals(rentee, reservation.getRentee());
    }

    @Test
    void getReservationDate() {
        assertTrue(reservation.getReservationStartDate() instanceof LocalDateTime);
    }

    @Test
    void getEquipment() {
        assertEquals(reservation.getEquipment(), equipment);
    }
}