package application.viewmodel.rentee;

import application.client.FakeRentalSystemClient;
import application.model.equipment.Equipment;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.models.RenteeModel;
import application.model.reservations.Unapproved;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RenteeReservationViewModelTest {
    private RenteeReservationViewModel viewModel;
    private Model model;

    @BeforeEach
    void setUp() throws RemoteException {
        model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new RenteeReservationViewModel((RenteeModel) model);
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));
    }

}