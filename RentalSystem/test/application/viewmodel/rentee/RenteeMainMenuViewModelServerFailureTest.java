package application.viewmodel.rentee;

import application.client.FailingRentalSystemClient;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.models.RenteeModel;
import application.model.users.User;
import org.junit.jupiter.api.BeforeEach;

public class RenteeMainMenuViewModelServerFailureTest {
    private RenteeMainMenuViewModel viewModel;

    @BeforeEach
    void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        User user = new User("d", "e", "f", "abc@gmail.com", "def");
        model.setCurrentlyLoggedInUser(user);
        viewModel = new RenteeMainMenuViewModel((RenteeModel) model);
    }

}
