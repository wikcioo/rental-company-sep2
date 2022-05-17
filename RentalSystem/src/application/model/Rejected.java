package application.model;

import java.time.LocalDateTime;

public class Rejected extends Reservation{
    private final LocalDateTime rejectionDate;
    private final String reason;
    private final Manager rejectedBy;

    public Rejected(User rentee, Equipment equipment, LocalDateTime reservationEndDate, LocalDateTime rejectionDate, String reason, Manager rejectedBy) {
        super(rentee, equipment, reservationEndDate);
        this.rejectionDate = rejectionDate;
        this.reason = reason;
        this.rejectedBy = rejectedBy;
    }

    public LocalDateTime getRejectionDate() {
        return rejectionDate;
    }

    public Manager getRejectedBy() {
        return rejectedBy;
    }
}
