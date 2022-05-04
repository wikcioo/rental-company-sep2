package application.model;

import java.io.Serializable;

//30.03.2022 _ for now, all that is included in the Equipment class is a list of
//setters ans getters, the constructor and toString
public class Equipment implements Serializable {
    private String model;
    private String category;
    private double price;

    public Equipment(String model, String category, double price) {
        this.model = model;
        this.category = category;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//  public boolean isAvailable() {
//  ///for this we need to be able to reserve and rent
//  }

    public String toString() {
        return model + "-" + category;
    }

}
