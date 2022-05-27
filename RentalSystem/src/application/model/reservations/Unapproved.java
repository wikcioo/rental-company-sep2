package application.model.reservations;

import application.model.equipment.Equipment;
import application.model.users.User;

import java.time.LocalDateTime;

public class Unapproved extends Reservation {
    public static final String type = "Unapproved";

    public Unapproved(int id, User rentee, LocalDateTime reservationDate, LocalDateTime rentedFor, Equipment equipment) {
        super(id, rentee, reservationDate, rentedFor, equipment);
    }

    public Unapproved(int id, User rentee, Equipment equipment, LocalDateTime rentedFor) {
        super(id, rentee, equipment, rentedFor);
    }

    @Override
    public String status() {
        return type;
    }
}
