package test.viewmodel.manager;

import application.model.Model;
import application.model.ModelManager;
import application.viewmodel.manager.AddUserViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.client.FakeRentalSystemClient;

import static org.junit.jupiter.api.Assertions.*;

public class AddUserViewModelTest {
    private AddUserViewModel viewModel;
    private StringProperty error;

    @BeforeEach
    public void setUp() {
        Model model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new AddUserViewModel(model);
        error = new SimpleStringProperty();
        viewModel.bindError(error);
    }

    @Test
    public void returns_true_and_empty_label_when_successfully_added_the_manager() {
        boolean result = viewModel.addUser("Manager");
        assertTrue(result);
        assertTrue(error.isEmpty().get());
    }

    @Test
    public void returns_true_and_empty_label_when_successfully_added_the_rentee() {
        boolean result = viewModel.addUser("Rentee");
        assertTrue(result);
        assertTrue(error.isEmpty().get());
    }
}
