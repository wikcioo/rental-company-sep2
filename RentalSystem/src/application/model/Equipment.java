package application.model;
//30.03.2022 _ for now, all that is included in the Equipment class is a list of
//setters ans getters, the constructor and toString
public class Equipment {
private String name;
private String model;
private String category;
private double price;

  public Equipment(String name, String model, String category, double price) {
    this.name = name;
    this.model = model;
    this.category = category;
    this.price = price;
}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
  return name + " " + model + " \nCategory:" + category + " \nPrice:" + price;
  }

}
