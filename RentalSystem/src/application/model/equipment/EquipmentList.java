package application.model.equipment;

import java.util.ArrayList;

public class EquipmentList {
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

    public ArrayList<Equipment> getAllAvailableEquipment() {
        ArrayList<Equipment> equipment = new ArrayList<>();
        for (Equipment e : equipmentList) {
            if (e.isAvailable()) {
                equipment.add(e);
            }
        }
        return equipment;
    }

    public void removeEquipment(Equipment equipment) {
        equipmentList.remove(equipment);
    }

    public void clear() {
        equipmentList.clear();
    }
}
