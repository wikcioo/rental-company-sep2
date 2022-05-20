package test.viewmodel;

import application.model.Model;
import application.model.ModelManager;
import application.viewmodel.LogInViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.client.FailingRentalSystemClient;
import test.client.FakeRentalSystemClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogInViewModelServerFailureTest {
    private LogInViewModel viewModel;
    private StringProperty email;
    private StringProperty password;

    @BeforeEach
    void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new LogInViewModel(model);
        email = new SimpleStringProperty();
        password = new SimpleStringProperty();
        viewModel.bindEmail(email);
        viewModel.bindPassword(password);
    }

    @Test
    public void server_failure_during_reserving_equipment(){
        assertThrows(RuntimeException.class,()-> viewModel.logIn());
    }

}
