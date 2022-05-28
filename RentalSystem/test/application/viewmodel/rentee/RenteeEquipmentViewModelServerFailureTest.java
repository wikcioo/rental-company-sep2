package application.viewmodel.rentee;

import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.models.RenteeModel;
import application.model.equipment.Equipment;
import application.model.users.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.client.FailingRentalSystemClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RenteeEquipmentViewModelServerFailureTest {
   private RenteeEquipmentViewModel viewModel;
   private StringProperty reservationError;
   private StringProperty equipmentError;


   @BeforeEach
   void setUp() {
      Model model = new ModelManager(new FailingRentalSystemClient());
      User user = new User("d", "e", "f", "abc@gmail.com", "def");
      model.setCurrentlyLoggedInUser(user);
      viewModel = new RenteeEquipmentViewModel((RenteeModel) model);
      reservationError = new SimpleStringProperty();
      equipmentError = new SimpleStringProperty();
      viewModel.bindEquipmentErrorLabel(equipmentError);
      viewModel.bindReservationErrorLabel(reservationError);
   }

   @Test
   public void server_failure_during_retrieving_unreserved_equipment_sets_error() {
      viewModel.retrieveAllUnreservedEquipment();
      assertEquals("Server communication error", equipmentError.get());
   }

   @Test
   public void server_failure_during_reserving_equipment(){
      viewModel.bindSelectedEquipment(new SimpleObjectProperty<>(new Equipment(0, null, null, true)));
      viewModel.reserveEquipment();
      assertEquals("Failed to reserve equipment", reservationError.get());
   }
}
