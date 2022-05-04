package application.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation implements Serializable {
    private Boolean approved;
    private User rentee;
    private LocalDateTime reservationStartDate;
    private LocalDateTime reservationEndDate;
    private Equipment equipment;

    @Override
    public String toString() {
        return "Reservation{" +
                "approved=" + approved +
                ", rentee=" + rentee +
                ", reservationDate=" + reservationStartDate +
                ", equipment=" + equipment +
                '}';
    }

    public Reservation(User rentee, Equipment equipment, LocalDateTime reservationEndDate) {
        this.rentee = rentee;
        this.equipment = equipment;
        reservationStartDate = LocalDateTime.now();
        this.reservationEndDate = reservationEndDate;
        approved = false;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void approve() {
        approved = true;
    }

    public User getRentee() {
        return rentee;
    }

    public LocalDateTime getReservationStartDate() {
        return reservationStartDate;
    }

    public LocalDateTime getReservationEndDate() {
        return reservationEndDate;
    }

    public Equipment getEquipment() {
        return equipment;
    }
}
