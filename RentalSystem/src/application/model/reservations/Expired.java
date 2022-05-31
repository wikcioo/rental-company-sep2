package application.model.reservations;

import application.model.users.User;
import application.model.equipment.Equipment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Expired extends Reservation {
    private final LocalDateTime dateTime;
    public static final String status = "Expired";

    public Expired(int id, User rentee, Equipment equipment, LocalDateTime reservationEndDate, LocalDateTime dateTime) {
        super(id, rentee, equipment, reservationEndDate);
        this.dateTime = dateTime;
    }

    public Expired(Reservation reservation, LocalDateTime expiredDate) {
        super(reservation.getId(), reservation.getRentee(), reservation.getReservationDate(), reservation.getRentedFor(), reservation.getEquipment());
        this.dateTime = expiredDate;
    }

    @Override
    public String status() {
        return status;
    }

    public LocalDateTime getExpirationDate() {
        return dateTime;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nExpired on: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Expired expired = (Expired) o;
        return Objects.equals(dateTime, expired.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateTime);
    }
}
