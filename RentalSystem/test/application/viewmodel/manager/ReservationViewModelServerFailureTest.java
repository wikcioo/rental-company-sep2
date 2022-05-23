package application.viewmodel.manager;

import application.client.FailingRentalSystemClient;
import application.model.Model;
import application.model.ModelManager;
import application.model.reservations.Reservation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//TODO: Class does not have error label
public class ReservationViewModelServerFailureTest {

    private ReservationViewModel viewModel;

    @BeforeEach
    public void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new ReservationViewModel(model);
    }

    @Test
    public void server_failure_during_approving_reservation_throws_RuntimeException() {
        assertThrows(RuntimeException.class, () -> viewModel.approveReservation(new Reservation(1, null, null, null, null)));
    }

    @Test
    public void server_failure_during_rejecting_reservation_throws_RuntimeException() {
        assertThrows(RuntimeException.class, () -> viewModel.rejectReservation(new Reservation(1, null, null, null, null),"") );
    }
}
