package application.model;

import java.io.Serializable;

public class Equipment implements Serializable {
    private String model;
    private String category;
    private boolean isAvailable;

    public Equipment(String model, String category, boolean isAvailable) {
        this.model = model;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void toggleAvailability(){
        isAvailable = !isAvailable;
    }

    public String toString() {
        return model + "-" + category;
    }

}
