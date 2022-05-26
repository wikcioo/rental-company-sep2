package application.model.reservations;

import application.model.users.User;
import application.model.equipment.Equipment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Returned extends Approved {
    private final LocalDateTime returnDate;
    public static final String type = "Returned";

    @Override
    public String status() {
        return type;
    }

    public Returned(int id, User rentee, Equipment equipment, LocalDateTime reservationEndDate, LocalDateTime approvedDate, String approvedBy, LocalDateTime returnDate) {
        super(id, rentee, equipment, reservationEndDate, approvedDate, approvedBy);
        this.returnDate = returnDate;
    }

    public Returned(Approved reservation, LocalDateTime returnDate) {
        super(reservation, reservation.getApprovedDate(), reservation.getApprovedBy());
        this.returnDate = returnDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return super.toString() + "\nReturned on: " + returnDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

