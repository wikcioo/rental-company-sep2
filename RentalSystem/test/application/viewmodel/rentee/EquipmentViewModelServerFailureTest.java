package application.viewmodel.rentee;

import application.model.ModelManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.client.FailingRentalSystemClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EquipmentViewModelServerFailureTest {
   private EquipmentViewModel viewModel;
   private StringProperty loggedUserProperty;
   private StringProperty error;


   @BeforeEach
   void setUp() {
      viewModel = new EquipmentViewModel(new ModelManager(new FailingRentalSystemClient()));
      loggedUserProperty = new SimpleStringProperty();
      error = new SimpleStringProperty();
      viewModel.bindErrorLabel(error);
   }

   @Test
   public void server_failure_during_retrieving_unreserved_equipment_sets_error() {
      viewModel.retrieveAllUnreservedEquipment();
      assertEquals("Failed to retrieve equipment list", error.get());
   }

   //TODO: The method tested here might no longer be necessary
   @Test
   public void server_failure_during_retrieving_all_equipment_sets_error() {
      viewModel.retrieveAllEquipment();
      assertEquals("Failed to retrieve equipment list", error.get());
   }

   //TODO: This method should set some error label
   @Test
   public void server_failure_during_reserving_equipment(){
      assertThrows(RuntimeException.class,()-> viewModel.reserveEquipment());
   }
}
