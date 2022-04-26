package test.model;

import application.model.Equipment;
import application.model.Reservation;
import application.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @BeforeAll
    void setUp() {
        Reservation reservation = new Reservation(new User("Selina", "Ceban"), new Equipment("Vacuum cleaner", "Arizona", "Vacuum Clenaer", 50));
    }

    @Test
    void isApproved() {

    }

    @Test
    void getApprovedBy() {
    }

    @Test
    void getRentee() {
    }

    @Test
    void getReservationDate() {
    }

    @Test
    void getEquipment() {
    }
}