package application.viewmodel.rentee;

import application.client.FailingRentalSystemClient;
import application.model.equipment.Equipment;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.models.RenteeModel;
import application.model.users.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RenteeReservationViewModelServerFailureTest {
    private RenteeReservationViewModel viewModel;

    @BeforeEach
    void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        User user = new User("d", "e", "f", "abc@gmail.com", "def");
        model.setCurrentlyLoggedInUser(user);
        viewModel = new RenteeReservationViewModel((RenteeModel) model);
    }

}
