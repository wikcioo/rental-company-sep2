package application.dao;

import application.model.Equipment;
import application.model.Manager;
import application.model.Rentee;
import application.model.Reservation;

import java.util.ArrayList;

public interface ReservationDao {
    void reserve(Equipment equipment, Rentee rentee);
    ArrayList<Reservation> getAllAwaitingApproval();
    ArrayList<Reservation> getAllApproved();
    ArrayList<Reservation> getAllReturned();
    ArrayList<Reservation> getAllExpired();
    ArrayList<Reservation> getAllOverdue();
    void approve(Reservation reservation, Manager approvedBy);
    void reject(Reservation reservation, Manager rejectedBy);
    void expire(Reservation reservation);

}
