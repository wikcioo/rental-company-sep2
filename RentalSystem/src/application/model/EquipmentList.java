package application.model;

import java.util.ArrayList;
//30.03.2022 all that needs to be implemented as of now is a simple
//constructor, an add and remove method, and a method that returns
//the entire list of equipment.

public class EquipmentList
{
  private final ArrayList<Equipment> equipmentList;

  public EquipmentList() {
    equipmentList = new ArrayList<>();
  }

  public void addEquipment(Equipment equipment) {
    equipmentList.add(equipment);
  }

  public void addEquipmentList(ArrayList<Equipment> list) {
    equipmentList.addAll(list);
  }

  public ArrayList<Equipment> getAllEquipment() {
    return equipmentList;
  }

  public void removeEquipment(Equipment equipment) {
    equipmentList.remove(equipment);
  }

  public ArrayList<Equipment> getAllEquipments() {
    return equipmentList;
  }

}
