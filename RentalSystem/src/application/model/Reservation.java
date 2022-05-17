package application.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

public class Reservation implements Serializable {
    private Boolean approved;
    private User rentee;
    private LocalDateTime reservationDate;
    private LocalDateTime rentedFor;
    private Equipment equipment;

    @Override
    public String toString() {
        return "Reservation{" +
                "approved=" + approved +
                ", rentee=" + rentee +
                ", reservationDate=" + reservationDate +
                ", equipment=" + equipment +
                '}';
    }

    public Reservation(User rentee, Equipment equipment, LocalDateTime rentedFor) {
        this.rentee = rentee;
        this.equipment = equipment;
        reservationDate = LocalDateTime.now();
        this.rentedFor = rentedFor;
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

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public LocalDateTime getRentedFor() {
        return rentedFor;
    }

    public Long getDaysOverdue(){
        return DAYS.between(rentedFor, LocalDateTime.now());
    }

    public Equipment getEquipment() {
        return equipment;
    }
}
