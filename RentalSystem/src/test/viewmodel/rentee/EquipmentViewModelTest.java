package test.viewmodel.rentee;

import application.model.Model;
import application.model.ModelManager;
import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import application.viewmodel.rentee.EquipmentViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.client.FakeRentalSystemClient;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentViewModelTest {
    private EquipmentViewModel viewModel;
    private StringProperty loggedUserProperty;
    private StringProperty error;
    private Model model;

    @BeforeEach
    void setUp() throws RemoteException {
        model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new EquipmentViewModel(model);
        loggedUserProperty = new SimpleStringProperty();
        error = new SimpleStringProperty();
        viewModel.bindErrorLabel(error);
        viewModel.bindLoggedUser(loggedUserProperty);
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));

    }

    @Test
    void when_logged_in_displays_user_email() {
        viewModel.displayLoggedUser();
        assertEquals("Logged as: john@gmail.com", loggedUserProperty.get());
    }

    //TODO: the String Property should probably be renamed from error to result
    @Test
    void when_successfully_received_all_equipment_sets_success_label() {
        viewModel.retrieveAllEquipment();
        assertEquals("Successfully retrieved equipment from DB", error.get());
    }

    @Test
    void when_successfully_received_unreserved_equipment_sets_success_label() {
        viewModel.retrieveAllUnreservedEquipment();
        assertEquals("Successfully retrieved equipment from DB", error.get());
    }

    @Test
    void reserving_equipment_adds_to_reservations_list() {
        viewModel.bindSelectedEquipment(new SimpleObjectProperty<>(new Equipment(0, "a", "b", false)));
        viewModel.bindReservationEndDate(new SimpleObjectProperty<>(LocalDateTime.now().plusDays(7)));
        viewModel.reserveEquipment();
        model.refreshReservations();
        ArrayList<Reservation> reservations = model.getUnapprovedReservations();
        assertEquals(1, reservations.size());
    }

    @Test
    void added_reservation_has_equipment_id() {
        viewModel.bindSelectedEquipment(new SimpleObjectProperty<>(new Equipment(7, "a", "b", true)));
        viewModel.bindReservationEndDate(new SimpleObjectProperty<>(LocalDateTime.now().plusDays(7)));
        viewModel.reserveEquipment();
        model.refreshReservations();
        Equipment equipment = model.getUnapprovedReservations().get(0).getEquipment();
        assertEquals(7, equipment.getEquipmentId());
    }

}