package application.model;

import application.util.NamedPropertyChangeSubject;

public interface Model extends NamedPropertyChangeSubject
{
    void addEquipment(Equipment equipment);
    void addReservation(User rentee, Equipment equipment);
    boolean logIn(String name, String password);
}
