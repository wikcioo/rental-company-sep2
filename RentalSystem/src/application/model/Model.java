package application.model;

import application.util.NamedPropertyChangeSubject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Model extends NamedPropertyChangeSubject
{
    void addEquipment(Equipment equipment);
    ArrayList<Equipment> getAllEquipment();
    void addReservation(User rentee, Equipment equipment, LocalDateTime reservationEndDate);
    boolean logIn(String name, String password);
    ArrayList<Reservation> getReservations();
}
