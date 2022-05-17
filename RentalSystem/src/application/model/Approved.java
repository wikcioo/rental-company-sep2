package application.model;

import java.time.LocalDateTime;

public class Approved extends Reservation {
    private final LocalDateTime approvedDate;
    private final Manager approvedBy;

    public Approved(User rentee, Equipment equipment, LocalDateTime reservationEndDate, LocalDateTime approvedDate, Manager approvedBy) {
        super(rentee, equipment, reservationEndDate);
        this.approvedDate = approvedDate;
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    public Manager getApprovedBy() {
        return approvedBy;
    }
}
