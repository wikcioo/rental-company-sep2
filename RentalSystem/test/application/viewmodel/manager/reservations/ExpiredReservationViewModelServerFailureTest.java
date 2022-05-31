package application.viewmodel.manager.reservations;

import application.client.FailingRentalSystemClient;
import application.model.models.ManagerModel;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.reservations.Unapproved;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiredReservationViewModelServerFailureTest {

    private ExpiredReservationViewModel viewModel;
    private SimpleStringProperty error;

    @BeforeEach
    public void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new ExpiredReservationViewModel((ManagerModel) model);
        error = new SimpleStringProperty();
        viewModel.bindErrorLabel(error);
    }

}
