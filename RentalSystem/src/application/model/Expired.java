package application.model;

import java.time.LocalDateTime;

public class Expired  extends Reservation{
    private final LocalDateTime dateTime;
    public static final String type = "Expired";
    @Override
    public String status() {
        return type;
    }

    public Expired(int id, User rentee, Equipment equipment, LocalDateTime reservationEndDate, LocalDateTime dateTime) {
        super(id, rentee, equipment, reservationEndDate);
        this.dateTime = dateTime;
    }

    public Expired(Reservation reservation, LocalDateTime expiredDate) {
        super(reservation.getId(), reservation.getRentee(),reservation.getReservationDate(), reservation.getRentedFor(), reservation.getEquipment());
        this.dateTime = expiredDate;
    }

    public LocalDateTime getExpirationDate() {
        return dateTime;
    }
}
