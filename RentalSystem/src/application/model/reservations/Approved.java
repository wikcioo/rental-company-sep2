package application.model.reservations;

import application.model.users.User;
import application.model.equipment.Equipment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

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
    public Long getDaysOverdue() {
        return DAYS.between(super.getRentedFor(), LocalDateTime.now());
//        TODO: [Aivaras] remove testing for overdue
//        return DAYS.between(super.getRentedFor().minusDays(2), LocalDateTime.now());
    }

    public String getDaysOverdueString(){
        String daysOverDue;
        int days = Math.toIntExact(getDaysOverdue());
        if (days <= 0) {
            daysOverDue = Math.abs(days) + " days left";
        } else {
            daysOverDue = days + " days overdue";
        }
        return daysOverDue;
    }
    @Override
    public String toString() {
        return super.toString() +
                "\nDays overdue: " + getDaysOverdueString() +
                "\nApproved on: " + approvedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                "\nApproved by: " + approvedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Approved approved = (Approved) o;
        return Objects.equals(getApprovedDate(), approved.getApprovedDate()) && Objects.equals(getApprovedBy(), approved.getApprovedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getApprovedDate(), getApprovedBy());
    }
}
