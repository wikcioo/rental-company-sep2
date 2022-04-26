package application.model;

import application.util.NamedPropertyChangeSubject;

import java.util.ArrayList;

public interface Model extends NamedPropertyChangeSubject
{
    void addEquipment(Equipment equipment);
    ArrayList<Equipment> getAllEquipment();
    void addReservation(User rentee, Equipment equipment);
    boolean logIn(String name, String password);
}
