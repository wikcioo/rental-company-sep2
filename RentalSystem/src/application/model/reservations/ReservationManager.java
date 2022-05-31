package application.model.reservations;

import java.util.ArrayList;

public class ReservationManager {
    private ArrayList<Reservation> reservations = new ArrayList<>();

    public ArrayList<Reservation> getAll() {
        ArrayList<Reservation> reservationList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationList.add((Reservation) reservation);
        }
        return reservationList;
    }

    public ArrayList<Reservation> getReservationsByType(String type) {
        ArrayList<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.status().equals(type)) {
                result.add(reservation);
            }
        }
        return result;
    }

    public ArrayList<Unapproved> getUnapprovedReservations() {
        ArrayList<Unapproved> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.status().equals(Unapproved.status)) {
                result.add((Unapproved) reservation);
            }
        }
        return result;
    }

    public ArrayList<Approved> getApprovedReservations() {
        ArrayList<Approved> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.status().equals(Approved.status)) {
                result.add((Approved) reservation);
            }
        }
        return result;
    }

    public ArrayList<Rejected> getRejectedReservations() {
        ArrayList<Rejected> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.status().equals(Rejected.status)) {
                result.add((Rejected) reservation);
            }
        }
        return result;
    }

    public ArrayList<Returned> getReturnedReservations() {
        ArrayList<Returned> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.status().equals(Returned.status)) {
                result.add((Returned) reservation);
            }
        }
        return result;
    }

    public ArrayList<Expired> getExpiredReservations() {
        ArrayList<Expired> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.status().equals(Expired.status)) {
                result.add((Expired) reservation);
            }
        }
        return result;
    }

    public void setReservationList(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }
}
