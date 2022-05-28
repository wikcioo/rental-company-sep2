package application.viewmodel;

import application.model.models.ModelManager;
import application.model.models.UserModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.client.FailingRentalSystemClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogInViewModelServerFailureTest {
    private LogInViewModel viewModel;
    private StringProperty email;
    private StringProperty password;

    @BeforeEach
    void setUp() {
        UserModel model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new LogInViewModel(model);
        email = new SimpleStringProperty();
        password = new SimpleStringProperty();
        viewModel.bindEmail(email);
        viewModel.bindPassword(password);
    }

    @Test
    public void server_failure_during_reserving_equipment(){
        assertEquals("ServerConnectionFailure", viewModel.logIn());
    }

}
