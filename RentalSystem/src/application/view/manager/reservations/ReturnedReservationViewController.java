package application.view.manager.reservations;

import application.model.reservations.Returned;
import application.model.users.User;
import application.view.ViewHandler;
import application.viewmodel.manager.reservations.ReturnedReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import java.time.format.DateTimeFormatter;

public class ReturnedReservationViewController {
    @FXML
    public Label error;
    private ViewHandler viewHandler;
    private ReturnedReservationViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Returned> reservationTable;
    @FXML
    private TableColumn<Returned, String> reservationIdColumn;
    @FXML
    private TableColumn<Returned, String> renteeColumn;
    @FXML
    private TableColumn<Returned, String> equipmentColumn;
    @FXML
    private TableColumn<Returned, String> reservationStartDateColumn;
    @FXML
    private TableColumn<Returned, String> reservationEndDateColumn;
    @FXML
    private TableColumn<Returned, String> returnDateColumn;

    public void init(ViewHandler viewHandler, ReturnedReservationViewModel returnedReservationViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = returnedReservationViewModel;
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

        returnDateColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getReturnDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no return date>");
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
