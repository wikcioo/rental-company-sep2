package application.viewmodel.manager;

import application.client.FakeRentalSystemClient;
import application.model.Model;
import application.model.ModelManager;
import application.model.reservations.Reservation;
import application.model.reservations.Unapproved;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationViewModelTest {
    private ReservationViewModel viewModel;
    private Model model;

    @BeforeEach
    void setUp() throws RemoteException {
        model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new ReservationViewModel(model);
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));
    }

    @Test
    void approving_a_reservation_adds_a_reservation_to_approved_list() throws RemoteException {
        Reservation r = new Unapproved(0, null, null, null);
        model.reserveEquipment(0, model.getCurrentlyLoggedInUser().getEmail(), LocalDateTime.now());
        viewModel.approveReservation(r);
        model.refreshReservations();
        assertEquals(1,model.getApprovedReservations().size());
    }

    @Test
    void rejecting_a_reservation_adds_a_reservation_to_rejected_list() throws RemoteException {
        Reservation r = new Unapproved(0, null, null, null);
        model.reserveEquipment(0, model.getCurrentlyLoggedInUser().getEmail(), LocalDateTime.now());
        viewModel.rejectReservation(r, "rejected");
        model.refreshReservations();
        assertEquals(1,model.getRejectedReservations().size());
    }
}
