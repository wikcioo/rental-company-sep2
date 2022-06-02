package application.model.equipment;

import java.io.Serializable;

public class Equipment implements Serializable {
    private final int equipmentId;
    private final String model;
    private final String category;
    private boolean isAvailable;

    public Equipment(int equipmentId, String model, String category, boolean isAvailable) {
        this.equipmentId = equipmentId;
        this.model = model;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void toggleAvailability() {
        isAvailable = !isAvailable;
    }

    public String toString() {
        return model + "-" + category;
    }

}
