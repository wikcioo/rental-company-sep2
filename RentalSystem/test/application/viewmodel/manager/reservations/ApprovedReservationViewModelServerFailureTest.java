package application.viewmodel.manager.reservations;

import application.client.FailingRentalSystemClient;
import application.model.models.ManagerModel;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.reservations.Unapproved;
import application.viewmodel.manager.reservations.ApprovedReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApprovedReservationViewModelServerFailureTest {

    private ApprovedReservationViewModel viewModel;
    private SimpleStringProperty error;

    @BeforeEach
    public void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new ApprovedReservationViewModel((ManagerModel) model);
        error = new SimpleStringProperty();
        viewModel.bindErrorLabel(error);
    }

    @Test
    public void server_failure_during_rejecting_reservation_sets_label() {
        viewModel.removeReservation(new Unapproved(1, null, null, null, null));
        assertEquals("Server communication error", error.get());
    }
}
