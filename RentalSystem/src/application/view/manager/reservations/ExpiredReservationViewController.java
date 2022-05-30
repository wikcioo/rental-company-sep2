package application.view.manager.reservations;

import application.model.reservations.Expired;
import application.model.users.User;
import application.view.ViewHandler;
import application.viewmodel.manager.reservations.ExpiredReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.time.format.DateTimeFormatter;

public class ExpiredReservationViewController {
    @FXML
    public Label error;
    private ViewHandler viewHandler;
    private ExpiredReservationViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Expired> reservationTable;
    @FXML
    private TableColumn<Expired, String> reservationIdColumn;
    @FXML
    private TableColumn<Expired, String> renteeColumn;
    @FXML
    private TableColumn<Expired, String> equipmentColumn;
    @FXML
    private TableColumn<Expired, String> reservationStartDateColumn;
    @FXML
    private TableColumn<Expired, String> reservationEndDateColumn;
    @FXML
    private TableColumn<Expired, String> expirationDateColumn;


    public void init(ViewHandler viewHandler, ExpiredReservationViewModel expiredReservationViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = expiredReservationViewModel;
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
                User u = p.getValue().getRentee();
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
                return new SimpleStringProperty(p.getValue().getReservationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no start date>");
            }
        });

        reservationEndDateColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getRentedFor().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no end date>");
            }
        });

        expirationDateColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no expiration date>");
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
