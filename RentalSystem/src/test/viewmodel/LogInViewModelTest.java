package test.viewmodel;

import application.model.Model;
import application.model.ModelManager;
import application.viewmodel.LogInViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.client.FakeRentalSystemClient;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogInViewModelTest {
    private LogInViewModel viewModel;
    private StringProperty email;
    private StringProperty password;

    @BeforeEach
    void setUp() {
        Model model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new LogInViewModel(model);
        email = new SimpleStringProperty();
        password = new SimpleStringProperty();
        viewModel.bindEmail(email);
        viewModel.bindPassword(password);
    }

    @Test
    void wrong_credentials_return_invalid() {
        email.set("bla");
        password.set("blah");
        assertEquals("Invalid", viewModel.logIn());
    }

    @Test
    void correct_manager_credentials_return_manager() {
        email.set("john@gmail.com");
        password.set("123");
        assertEquals("Manager", viewModel.logIn());
    }

    @Test
    void correct_rentee_credentials_return_rentee() {
        email.set("tomas@gmail.com");
        password.set("abc");
        assertEquals("Rentee", viewModel.logIn());
    }
}
