package application.model.reservations;

import application.model.users.User;
import application.model.equipment.Equipment;

import java.time.LocalDateTime;

public class Approved extends Reservation {
    private final LocalDateTime approvedDate;
    private final String approvedBy;

    public static final String type = "Approved";

    @Override
    public String status() {
        return type;
    }


    public Approved(int id, User rentee, Equipment equipment, LocalDateTime reservationEndDate, LocalDateTime approvedDate, String approvedBy) {
        super(id, rentee, equipment, reservationEndDate);
        this.approvedDate = approvedDate;
        this.approvedBy = approvedBy;
    }

    public Approved(Reservation reservation, LocalDateTime approvedDate, String approvedBy) {
        super(reservation.getId(), reservation.getRentee(), reservation.getReservationDate(), reservation.getRentedFor(), reservation.getEquipment());
        this.approvedBy = approvedBy;
        this.approvedDate = approvedDate;
    }


    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }
}
