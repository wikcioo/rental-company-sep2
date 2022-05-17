package application.model;

import java.time.LocalDateTime;

public class Expired  extends Reservation{
    private final LocalDateTime dateTime;

    public Expired(User rentee, Equipment equipment, LocalDateTime reservationEndDate, LocalDateTime dateTime) {
        super(rentee, equipment, reservationEndDate);
        this.dateTime = dateTime;
    }

    public LocalDateTime getExpirationDate() {
        return dateTime;
    }
}
