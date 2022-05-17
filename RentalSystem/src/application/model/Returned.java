package application.model;

import java.time.LocalDateTime;

public class Returned extends Approved{
    private final LocalDateTime returnDate;

    public Returned(User rentee, Equipment equipment, LocalDateTime reservationEndDate, LocalDateTime approvedDate, Manager approvedBy, LocalDateTime returnDate) {
        super(rentee, equipment, reservationEndDate, approvedDate, approvedBy);
        this.returnDate = returnDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }
}

