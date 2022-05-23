package application.viewmodel.manager;

import application.client.FailingRentalSystemClient;
import application.model.Model;
import application.model.ModelManager;
import application.model.reservations.Reservation;
import application.model.users.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//TODO: Class does not have error label
public class ReservationViewModelServerFailureTest {

    private ReservationViewModel viewModel;
    private StringProperty error;

    @BeforeEach
    public void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        User user = new User("d", "e", "f", "abc@gmail.com", "def");
        model.setCurrentlyLoggedInUser(user);
        viewModel = new ReservationViewModel(model);
        error = new SimpleStringProperty();
        viewModel.bindErrorLabel(error);
    }

    @Test
    public void server_failure_during_approving_reservation_throws_RuntimeException() {
        viewModel.approveReservation(new Reservation(1, null, null, null, null));
        assertEquals("Server communication error", error.get());
    }

    @Test
    public void server_failure_during_rejecting_reservation_throws_RuntimeException() {
        viewModel.rejectReservation(new Reservation(1,null, null, null, null));
        assertEquals("Server communication error", error.get());
    }
}
