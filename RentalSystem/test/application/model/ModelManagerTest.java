package application.model;

import application.client.FakeRentalSystemClient;
import application.model.equipment.Equipment;
import application.model.reservations.*;
import application.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelManagerTest {
    private Model model;

    @BeforeEach
    void setUp() throws RemoteException {
        model = new ModelManager(new FakeRentalSystemClient());
        User user = new User("d", "e", "f", "abc@gmail.com", "def");
        model.setCurrentlyLoggedInUser(user);
        Equipment equipment = new Equipment(0, "model", "category", true);
        //2 Approved
        model.reserveEquipment(equipment.getEquipmentId(), user.getEmail(), LocalDateTime.now());
        model.reserveEquipment(equipment.getEquipmentId(), user.getEmail(), LocalDateTime.now());
        model.approveReservation(0, "john@gmail.com");
        model.approveReservation(1, "john@gmail.com");
        //2 Returned
        model.reserveEquipment(equipment.getEquipmentId(), user.getEmail(), LocalDateTime.now());
        model.reserveEquipment(equipment.getEquipmentId(), user.getEmail(), LocalDateTime.now());
        model.approveReservation(2, "john@gmail.com");
        model.returnReservation(2);
        model.approveReservation(3, "john@gmail.com");
        model.returnReservation(3);
        //1 Rejected
        model.reserveEquipment(equipment.getEquipmentId(), user.getEmail(), LocalDateTime.now());
        model.rejectReservation(4, "john@gmail.com", "No reason");
        //3 Unapproved
        model.reserveEquipment(equipment.getEquipmentId(), user.getEmail(), LocalDateTime.now());
        model.reserveEquipment(equipment.getEquipmentId(), user.getEmail(), LocalDateTime.now());
        model.reserveEquipment(equipment.getEquipmentId(), user.getEmail(), LocalDateTime.now());


        model.refreshReservations();
    }

    //Equipment
    @Test
    void adding_equipment_increases_equipment_list_size() throws RemoteException {
        model.addEquipment(null, null, true);
        assertEquals(1, model.getAllEquipment().size());
    }

    @Test
    void toggling_availability_of_equipment_changes_the_availability() throws RemoteException {
        model.addEquipment(null, null, true);
        model.getAllEquipment().get(0).toggleAvailability();
        assertEquals(false, model.getAllEquipment().get(0).isAvailable());
    }

    @Test
    void get_all_available_equipment_returns_only_available_equipment() throws RemoteException {
        model.addEquipment(null, null, true);
        model.addEquipment(null, null, false);
        model.addEquipment(null, null, false);
        assertEquals(1, model.getAllAvailableEquipment().size());
    }

    //Users

    @Test
    void setting_current_logged_in_user_saves_the_user() {
        User user = new User("d", "e", "f", "abc@gmail.com", "def");
        model.setCurrentlyLoggedInUser(user);
        assertEquals("d", model.getCurrentlyLoggedInUser().getFirstName());
    }

    @Test
    void adding_a_new_user_saves_that_user() throws RemoteException {
        model.addUser("d", "e", "f", "abc@gmail.com", "def", true);
        assertEquals("d", model.getUser("abc@gmail.com").getFirstName());
    }

    @Test
    void logging_in_as_a_manager_user_returns_manager() throws RemoteException {
        model.addUser("d", "e", "f", "abc@gmail.com", "def", true);
        assertEquals("Manager", model.logIn("abc@gmail.com", "def"));
    }

    //Reservations
    @Test
    public void reservation_list_correctly_returns_unapproved_reservations() {
        ArrayList<Unapproved> unapprovedReservations = model.getUnapprovedReservations();
        assertEquals(3, unapprovedReservations.size());
    }

    @Test
    public void reservation_list_correctly_returns_approved_reservations() {
        ArrayList<Approved> approvedReservations = model.getApprovedReservations();
        assertEquals(2, approvedReservations.size());
    }

    @Test
    public void reservation_list_correctly_returns_rejected_reservations() {
        ArrayList<Rejected> rejectedReservations = model.getRejectedReservations();
        assertEquals(1, rejectedReservations.size());
    }

    @Test
    public void reservation_list_correctly_returns_returned_reservations() {
        ArrayList<Returned> returnedReservations = model.getReturnedReservations();
        assertEquals(2, returnedReservations.size());
    }

    @Test
    public void approving_a_reservation_removes_it_from_unapproved_reservation_list() throws RemoteException {
        model.approveReservation(7, "john@gmail.com");
        model.refreshReservations();
        ArrayList<Unapproved> unapprovedReservations = model.getUnapprovedReservations();
        assertEquals(2, unapprovedReservations.size());
    }

    @Test
    public void rejecting_a_reservation_removes_it_from_unapproved_reservation_list() throws RemoteException {
        model.rejectReservation(7, "john@gmail.com", "");
        model.refreshReservations();
        ArrayList<Unapproved> unapprovedReservations = model.getUnapprovedReservations();
        assertEquals(2, unapprovedReservations.size());
    }

    @Test
    public void expiring_a_reservation_removes_it_from_unapproved_reservation_list() throws RemoteException {
        model.rejectReservation(7, "john@gmail.com", "dsafsf");
        model.refreshReservations();
        ArrayList<Unapproved> unapprovedReservations = model.getUnapprovedReservations();
        assertEquals(2, unapprovedReservations.size());
    }

    @Test
    public void returning_a_reservation_removes_it_from_approved_reservation_list() throws RemoteException {
        model.returnReservation(0);
        model.refreshReservations();
        ArrayList<Approved> approvedReservations = model.getApprovedReservations();
        assertEquals(1, approvedReservations.size());
    }

    @Test
    void reserving_equipment_add_a_new_unapproved_reservation() throws RemoteException {
        model.reserveEquipment(0,"bob@gmail.com",LocalDateTime.now());
        model.refreshReservations();
        ArrayList<Unapproved> unapprovedReservations = model.getUnapprovedReservations();
        assertEquals(4, unapprovedReservations.size());
    }
}
