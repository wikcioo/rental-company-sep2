package application.viewmodel.rentee;

import application.client.FakeRentalSystemClient;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.models.RenteeModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RenteeMainMenuViewModelTest {
    private RenteeMainMenuViewModel viewModel;
    private StringProperty loggedUserProperty;

    @BeforeEach
    void setUp() throws RemoteException {
        Model model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new RenteeMainMenuViewModel((RenteeModel) model);
        loggedUserProperty = new SimpleStringProperty();
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));
        viewModel.bindLoggedUser(loggedUserProperty);
    }

    @Test
    void when_logged_in_displays_user_email() {
        viewModel.displayLoggedUser();
        assertEquals("Logged as: john@gmail.com", loggedUserProperty.get());
    }

}