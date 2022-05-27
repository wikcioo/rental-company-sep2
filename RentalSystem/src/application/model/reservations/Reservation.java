package application.model.reservations;

import application.model.users.User;
import application.model.equipment.Equipment;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Reservation implements Serializable {
    private final int id;
    private final User rentee;
    private final LocalDateTime reservationDate;
    private final LocalDateTime rentedFor;
    private final Equipment equipment;

    public Reservation(int id, User rentee, LocalDateTime reservationDate, LocalDateTime rentedFor, Equipment equipment) {
        this.id = id;
        this.rentee = rentee;
        this.reservationDate = reservationDate;
        this.rentedFor = rentedFor;
        this.equipment = equipment;
    }

    public Reservation(int id, User rentee, Equipment equipment, LocalDateTime rentedFor) {
        this(id,rentee,LocalDateTime.now(),rentedFor,equipment);
    }

    public abstract String status();

    public User getRentee() {
        return rentee;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public LocalDateTime getRentedFor() {
        return rentedFor;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " \nRented from " + reservationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                " until " + rentedFor.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                "\nRented equipment: " + equipment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return getId() == that.getId() && Objects.equals(getRentee(), that.getRentee()) && Objects.equals(getReservationDate(), that.getReservationDate()) && Objects.equals(getRentedFor(), that.getRentedFor()) && Objects.equals(getEquipment(), that.getEquipment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRentee(), getReservationDate(), getRentedFor(), getEquipment());
    }
}
