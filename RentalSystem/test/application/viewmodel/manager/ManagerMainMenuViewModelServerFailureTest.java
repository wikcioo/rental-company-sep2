package application.viewmodel.manager;

import application.client.FailingRentalSystemClient;
import application.model.equipment.Equipment;
import application.model.models.ManagerModel;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.viewmodel.manager.equipment.ManagerEquipmentViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerMainMenuViewModelServerFailureTest {
    private ManagerMainMenuViewModel viewModel;

    @BeforeEach
    public void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new ManagerMainMenuViewModel((ManagerModel) model);
    }

    @Test
    public void server_failure_during_getting_timeout_returns_minus_one() {
        assertEquals(-1, viewModel.getCurrentExpirationTimeout());
    }
}
