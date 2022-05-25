package application.view.manager;
import application.model.reservations.Rejected;
import application.model.reservations.Reservation;
import application.model.users.User;
import application.view.ViewHandler;
import application.viewmodel.manager.RejectedReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;


public class RejectedReservationViewController {
    @FXML
    public Label error;
    private ViewHandler viewHandler;
    private RejectedReservationViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Rejected> reservationTable;
    @FXML
    private TableColumn<Rejected, String> reservationIdColumn;
    @FXML
    private TableColumn<Rejected, String> renteeColumn;
    @FXML
    private TableColumn<Rejected, String> equipmentColumn;
    @FXML
    private TableColumn<Rejected, String> reservationDateColumn;
    @FXML
    private TableColumn<Rejected, String> rejectionDateColumn;
    @FXML
    public TableColumn<Rejected, String> reasonColumn;

    public void init(ViewHandler viewHandler, RejectedReservationViewModel rejectedReservationViewModel, Region root) {
      this.viewHandler = viewHandler;
      this.viewModel = rejectedReservationViewModel;
      this.root = root;

      reservationIdColumn.setCellValueFactory(p -> {
        if (p.getValue() != null) {
          return new SimpleStringProperty(Integer.toString(p.getValue().getId()));
        } else {
          return new SimpleStringProperty("<no equipment>");
        }
      });

      renteeColumn.setCellValueFactory(p -> {
        if (p.getValue() != null) {
          User u = p.getValue().getRentee();
          return new SimpleStringProperty(u.getFirstName() + " " + u.getLastName() + " - " + u.getEmail());
        } else {
          return new SimpleStringProperty("<no rentee>");
        }
      });

      equipmentColumn.setCellValueFactory(p -> {
        if (p.getValue() != null) {
          return new SimpleStringProperty(p.getValue().getEquipment().toString());
        } else {
          return new SimpleStringProperty("<no equipment>");
        }
      });

      reservationDateColumn.setCellValueFactory(p -> {
        if (p.getValue() != null) {
          return new SimpleStringProperty(p.getValue().getReservationDate().format(
              DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
          return new SimpleStringProperty("<no reservation date>");
        }
      });

      rejectionDateColumn.setCellValueFactory(p -> {
        if (p.getValue() != null && p.getValue().getRentedFor() != null) {
          return new SimpleStringProperty(p.getValue().getRentedFor().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
          return new SimpleStringProperty("<no rejection date>");
        }
      });

      reasonColumn.setCellValueFactory(p -> {
        if (p.getValue() != null) {
          return new SimpleStringProperty(p.getValue().getReason());
        } else {
          return new SimpleStringProperty("<no reason>");
        }
      });


      viewModel.bindReservationList(reservationTable.itemsProperty());
      viewModel.bindErrorLabel(error.textProperty());
    }

    public void reset() {
      error.setText("");
    }

    public Region getRoot() {
      return root;
    }

    public void backButtonPressed() {
      viewHandler.openView(ViewHandler.MANAGER_EQUIPMENT_LIST_VIEW);
    }

  }

