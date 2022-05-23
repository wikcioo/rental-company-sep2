package application.view.manager;

import application.model.reservations.Approved;
import application.model.reservations.Reservation;
import application.model.users.User;
import application.view.ViewHandler;
import application.viewmodel.manager.ApprovedReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;

public class ApprovedReservationViewController {
    @FXML
    public Label error;
    private ViewHandler viewHandler;
    private ApprovedReservationViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Approved> reservationTable;
    @FXML
    private TableColumn<Reservation, String> reservationIdColumn;
    @FXML
    private TableColumn<Reservation, String> renteeColumn;
    @FXML
    private TableColumn<Reservation, String> equipmentColumn;
    @FXML
    private TableColumn<Reservation, String> startDateColumn;
    @FXML
    private TableColumn<Reservation, String> endDateColumn;
    @FXML
    public TableColumn<Reservation, String> daysOverdueColumn;
    @FXML
    public TableColumn<Reservation, String> returnButtonColumn;

    public void init(ViewHandler viewHandler, ApprovedReservationViewModel approvedReservationViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = approvedReservationViewModel;
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

        startDateColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getReservationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no end date>");
            }
        });

        endDateColumn.setCellValueFactory(p -> {
            if (p.getValue() != null && p.getValue().getRentedFor() != null) {
                return new SimpleStringProperty(p.getValue().getRentedFor().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no end date>");
            }
        });

        daysOverdueColumn.setCellValueFactory(p -> {
            if (p.getValue() != null && p.getValue().getRentedFor() != null) {
                return new SimpleStringProperty(p.getValue().getDaysOverdue().toString());
            } else {
                return new SimpleStringProperty("<no end date>");
            }
        });

        returnButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Reservation, String> call(final TableColumn<Reservation, String> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Return");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Reservation reservation = getTableView().getItems().get(getIndex());
                            viewModel.removeReservation(reservation);
                            reservationTable.refresh();
                        });
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
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
