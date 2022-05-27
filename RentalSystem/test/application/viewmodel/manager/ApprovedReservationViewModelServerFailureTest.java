package application.viewmodel.manager;

import application.client.FailingRentalSystemClient;
import application.model.Model;
import application.model.ModelManager;
import application.model.reservations.Unapproved;
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
        viewModel = new ApprovedReservationViewModel(model);
        error = new SimpleStringProperty();
        viewModel.bindErrorLabel(error);
    }

    @Test
    public void server_failure_during_rejecting_reservation_sets_label() {
        viewModel.removeReservation(new Unapproved(1, null, null, null, null));
        assertEquals("Server communication error", error.get());
    }
}
