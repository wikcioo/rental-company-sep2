package application.viewmodel.manager.reservations;

import application.client.FakeRentalSystemClient;
import application.model.models.ManagerModel;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.reservations.Reservation;
import application.model.reservations.Unapproved;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RejectedReservationViewModelTest {
    private RejectedReservationViewModel viewModel;

    private Model model;

    @BeforeEach
    void setUp() throws RemoteException {
        model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new RejectedReservationViewModel((ManagerModel) model);
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));

    }

}
