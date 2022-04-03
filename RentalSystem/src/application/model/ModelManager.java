package application.model;

public class ModelManager implements Model {
    private final EquipmentList equipmentList;

    public ModelManager() {
        equipmentList = new EquipmentList();
    }

    @Override
    public void addEquipment(Equipment equipment) {
        equipmentList.addEquipment(equipment);
        System.out.println(equipmentList.getAllEquipments().toString());
    }
}
