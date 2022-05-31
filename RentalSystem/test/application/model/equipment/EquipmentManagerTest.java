package application.model.equipment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentManagerTest {
    private EquipmentManager equipmentManager;

    @BeforeEach
    void setUp() {
        equipmentManager = new EquipmentManager();
    }

    @Test
    void new_equipment_list_has_size_zero() {
        assertEquals(0, equipmentManager.getAllEquipment().size());
    }


    @Test
    void adding_equipment_to_list_increases_its_size() {
        equipmentManager.addEquipment(new Equipment(0, "a", "b", true));
        assertEquals(1, equipmentManager.getAllEquipment().size());
    }

    @Test
    void adding_an_equipment_list_adds_all_equipment() {
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment(0, "a", "b", false));
        equipment.add(new Equipment(1, "a", "b", false));
        equipment.add(new Equipment(2, "a", "b", true));
        equipment.add(new Equipment(3, "a", "b", false));
        equipment.add(new Equipment(4, "a", "b", true));
        equipmentManager.addEquipmentList(equipment);
        assertEquals(5, equipmentManager.getAllEquipment().size());
    }

    @Test
    void adding_unavailable_equipment_returns_only_available_equipment_from_available_list() {
        equipmentManager.addEquipment(new Equipment(0, "a", "b", false));
        assertEquals(0, equipmentManager.getAllAvailableEquipment().size());
    }

    @Test
    void adding_an_equipment_list_returns_only_available_equipment_from_available_list() {
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment(0, "a", "b", false));
        equipment.add(new Equipment(1, "a", "b", false));
        equipment.add(new Equipment(3, "a", "b", false));
        equipment.add(new Equipment(2, "a", "b", true));
        equipment.add(new Equipment(4, "a", "b", true));
        equipmentManager.addEquipmentList(equipment);
        assertEquals(2, equipmentManager.getAllAvailableEquipment().size());
    }

    @Test
    void cleared_equipment_list_has_size_0() {
        equipmentManager.addEquipment(new Equipment(0, "a", "b", false));
        equipmentManager.addEquipment(new Equipment(1, "a", "b", false));
        equipmentManager.addEquipment(new Equipment(2, "a", "b", false));
        equipmentManager.clear();
        assertEquals(0, equipmentManager.getAllEquipment().size());
    }

    @Test
    void removing_equipment_removes_them_from_list() {
        Equipment e = new Equipment(0, "a", "b", false);
        equipmentManager.addEquipment(e);
        equipmentManager.removeEquipment(e);
        assertEquals(0, equipmentManager.getAllEquipment().size());
    }
}
