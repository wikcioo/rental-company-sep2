package application.model.equipment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentListTest {
    private EquipmentList equipmentList;

    @BeforeEach
    void setUp() {
        equipmentList = new EquipmentList();
    }

    @Test
    void new_equipment_list_has_size_zero() {
        assertEquals(0, equipmentList.getAllEquipment().size());
    }


    @Test
    void adding_equipment_to_list_increases_its_size() {
        equipmentList.addEquipment(new Equipment(0, "a", "b", true));
        assertEquals(1, equipmentList.getAllEquipment().size());
    }

    @Test
    void adding_an_equipment_list_adds_all_equipment() {
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment(0, "a", "b", false));
        equipment.add(new Equipment(1, "a", "b", false));
        equipment.add(new Equipment(2, "a", "b", true));
        equipment.add(new Equipment(3, "a", "b", false));
        equipment.add(new Equipment(4, "a", "b", true));
        equipmentList.addEquipmentList(equipment);
        assertEquals(5, equipmentList.getAllEquipment().size());
    }

    @Test
    void adding_unavailable_equipment_returns_only_available_equipment_from_available_list() {
        equipmentList.addEquipment(new Equipment(0, "a", "b", false));
        assertEquals(0, equipmentList.getAllAvailableEquipment().size());
    }

    @Test
    void adding_an_equipment_list_returns_only_available_equipment_from_available_list() {
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment(0, "a", "b", false));
        equipment.add(new Equipment(1, "a", "b", false));
        equipment.add(new Equipment(3, "a", "b", false));
        equipment.add(new Equipment(2, "a", "b", true));
        equipment.add(new Equipment(4, "a", "b", true));
        equipmentList.addEquipmentList(equipment);
        assertEquals(2, equipmentList.getAllAvailableEquipment().size());
    }

    @Test
    void cleared_equipment_list_has_size_0() {
        equipmentList.addEquipment(new Equipment(0, "a", "b", false));
        equipmentList.addEquipment(new Equipment(1, "a", "b", false));
        equipmentList.addEquipment(new Equipment(2, "a", "b", false));
        equipmentList.clear();
        assertEquals(0, equipmentList.getAllEquipment().size());
    }

    @Test
    void removing_equipment_removes_them_from_list() {
        Equipment e = new Equipment(0, "a", "b", false);
        equipmentList.addEquipment(e);
        equipmentList.removeEquipment(e);
        assertEquals(0, equipmentList.getAllEquipment().size());
    }
}
