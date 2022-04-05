package application.model;

import application.util.NamedPropertyChangeSubject;

public interface Model extends NamedPropertyChangeSubject
{
    void addEquipment(Equipment equipment);
}
