package application.model.reservations;

import java.util.ArrayList;

public class ReservationList {
    private ArrayList<Reservation> reservations = new ArrayList<>();

    public ArrayList<Reservation> getAll() {
        ArrayList<Reservation> reservationList = new ArrayList<>();
        for(Reservation reservation : reservations) {
            reservationList.add((Reservation) reservation);
        }
        return reservationList;
    }

    public ArrayList<Reservation> getUnapprovedReservations() {
        ArrayList<Reservation> result = new ArrayList<>();
        for(Reservation reservation :  reservations) {
            if(reservation.status().equals(Reservation.type)) {
                result.add((Reservation) reservation);
            }
        }
        return result;
    }

    public ArrayList<Approved> getApprovedReservations() {
        ArrayList<Approved> result = new ArrayList<>();
        for(Reservation reservation :  reservations) {
            if(reservation.status().equals(Approved.type)) {
                result.add((Approved) reservation);
            }
        }
        return result;
    }

    public ArrayList<Rejected> getRejectedReservations() {
        ArrayList<Rejected> result = new ArrayList<>();
        for(Reservation reservation :  reservations) {
            if(reservation.status().equals(Rejected.type)) {
                result.add((Rejected) reservation);
            }
        }
        return result;
    }

    public ArrayList<Returned> getReturnedReservations() {
        ArrayList<Returned> result = new ArrayList<>();
        for(Reservation reservation :  reservations) {
            if(reservation.status().equals(Returned.type)) {
                result.add((Returned) reservation);
            }
        }
        return result;
    }

    public ArrayList<Expired> getExpiredReservations() {
        ArrayList<Expired> result = new ArrayList<>();
        for(Reservation reservation :  reservations) {
            if(reservation.status().equals(Expired.type)) {
                result.add((Expired) reservation);
            }
        }
        return result;
    }

    public void setReservationList(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }
}
