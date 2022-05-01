package application.viewmodel;

import application.model.Equipment;
import application.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddEquipmentViewModel {
    private final StringProperty name;
    private final StringProperty equipmentModel;
    private final StringProperty category;
    private final StringProperty price;
    private final Model model;

    public AddEquipmentViewModel(Model model) {
        this.model = model;
        name = new SimpleStringProperty("");
        category = new SimpleStringProperty("");
        equipmentModel = new SimpleStringProperty("");
        price = new SimpleStringProperty("");
    }

    public void bindName(StringProperty property) {
        name.bind(property);
    }

    public void bindEquipmentModel(StringProperty property) {
        equipmentModel.bind(property);
    }

    public void bindCategory(StringProperty property) {
        category.bind(property);
    }

    public void bindPrice(StringProperty property) {
        price.bind(property);
    }

    public void addEquipment() {
        double priceValue = Double.parseDouble(price.get());
        model.addEquipment(new Equipment(equipmentModel.get(), category.get(), priceValue));
    }
}
