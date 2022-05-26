package application.model.reservations;

import application.model.users.User;
import application.model.equipment.Equipment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rejected extends Reservation {
    private final LocalDateTime rejectionDate;
    private final String reason;
    private final String rejectedBy;
    public static final String type = "Rejected";

    @Override
    public String status() {
        return type;
    }

    public Rejected(int id, User rentee, Equipment equipment, LocalDateTime reservationEndDate, LocalDateTime rejectionDate, String reason, String rejectedBy) {
        super(id, rentee, equipment, reservationEndDate);
        this.rejectionDate = rejectionDate;
        this.reason = reason;
        this.rejectedBy = rejectedBy;
    }

    public Rejected(Reservation reservation, LocalDateTime rejectionDate, String reason, String rejectedBy) {
        super(reservation.getId(), reservation.getRentee(), reservation.getReservationDate(), reservation.getRentedFor(), reservation.getEquipment());
        this.rejectionDate = rejectionDate;
        this.reason = reason;
        this.rejectedBy = rejectedBy;
    }

    public LocalDateTime getRejectionDate() {
        return rejectionDate;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nRejected on: " + rejectionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                "\nRejected by: " + rejectedBy +
                "\nRejection reason: " + reason;
    }
}
