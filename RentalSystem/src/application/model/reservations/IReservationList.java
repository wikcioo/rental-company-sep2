package application.model.reservations;

import java.util.ArrayList;

public interface IReservationList {
    ArrayList<Reservation> getUnapprovedReservations();
    ArrayList<Approved> getApprovedReservations();
    ArrayList<Rejected> getRejectedReservations();
    ArrayList<Returned> getReturnedReservations();
    ArrayList<Expired> getExpiredReservations();
    ArrayList<Reservation> getAll();

    void setReservationList(ArrayList<IReservation> reservations);
}
