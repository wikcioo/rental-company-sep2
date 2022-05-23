package application.viewmodel.manager;

import application.client.FakeRentalSystemClient;
import application.model.Model;
import application.model.ModelManager;
import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApprovedReservationViewModelTest {
    private ApprovedReservationViewModel viewModel;

    private Model model;

    @BeforeEach
    void setUp() throws RemoteException {
        model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new ApprovedReservationViewModel(model);
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));

    }

    @Test
    void returning_a_reservation_adds_a_reservation_to_returned_list() throws RemoteException {
        Reservation r = new Reservation(0, null, null, null);
        model.addReservation(model.getCurrentlyLoggedInUser(),new Equipment(0,null,null,true), LocalDateTime.now());
        model.approveReservation(r);
        viewModel.removeReservation(r);
        model.refreshReservations();
        assertEquals(1,model.getReturnedReservations().size());
    }

}
