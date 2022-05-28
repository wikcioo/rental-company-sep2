package application.viewmodel.rentee;

import application.model.models.RenteeModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RenteeMainMenuViewModel {
    private final RenteeModel model;
    private final StringProperty errorProperty;
    private final StringProperty loggedUserProperty;

    public RenteeMainMenuViewModel(RenteeModel model) {
        this.model = model;
        this.errorProperty = new SimpleStringProperty();
        this.loggedUserProperty = new SimpleStringProperty();
    }

    public void bindLoggedUser(StringProperty property) {
        loggedUserProperty.bindBidirectional(property);
    }

    public void displayLoggedUser() {
        loggedUserProperty.set("Logged as: " + model.getCurrentlyLoggedInUser().getEmail());
    }

    public String getCurrentUserOverDueEquipmentAmount() {
        return Integer.toString(model.getCurrentUserOverDueEquipmentAmount());
    }
}
