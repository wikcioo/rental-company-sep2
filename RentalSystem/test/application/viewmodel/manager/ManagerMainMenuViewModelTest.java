package application.viewmodel.manager;

import application.client.FakeRentalSystemClient;
import application.model.models.ManagerModel;
import application.model.models.Model;
import application.model.models.ModelManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerMainMenuViewModelTest {
    private ManagerMainMenuViewModel viewModel;
    private StringProperty loggedUser;

    @BeforeEach
    public void setUp() throws RemoteException {
        Model model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new ManagerMainMenuViewModel((ManagerModel) model);
        loggedUser = new SimpleStringProperty();
        viewModel.bindLoggedUser(loggedUser);
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));
    }

    @Test
    public void when_logged_in_display_user_email() {
        viewModel.displayLoggedUser();
        assertEquals("Logged as: john@gmail.com", loggedUser.get());
    }

    @Test
    public void setting_expiration_timer_saves_it(){
        viewModel.setCurrentExpirationTimeout(400);
        assertEquals(400, viewModel.getCurrentExpirationTimeout());
    }


}
