package application.view.manager.reservations;

import application.model.reservations.Rejected;
import application.model.users.User;
import application.view.ViewHandler;
import application.viewmodel.manager.reservations.RejectedReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

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
    private TableColumn<Rejected, String> reservationStartDateColumn;
    @FXML
    private TableColumn<Rejected, String> reservationEndDateColumn;
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
                return new SimpleStringProperty("<no id>");
            }
        });

        renteeColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getRentee().toString());
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

        reservationStartDateColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getReservationDate().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no start date>");
            }
        });

        reservationEndDateColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getRentedFor().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no end date>");
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
        viewHandler.openView(ViewHandler.MANAGER_MAIN_MENU_VIEW);
    }

}

