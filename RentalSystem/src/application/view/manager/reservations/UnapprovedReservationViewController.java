package application.view.manager.reservations;

import application.model.reservations.Reservation;
import application.model.reservations.Unapproved;
import application.model.users.User;
import application.view.ViewHandler;
import application.viewmodel.manager.reservations.UnapprovedReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class UnapprovedReservationViewController {
    @FXML
    public Label error;
    private ViewHandler viewHandler;
    private UnapprovedReservationViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Unapproved> reservationTable;
    @FXML
    private TableColumn<Reservation, String> reservationIdColumn;
    @FXML
    private TableColumn<Reservation, String> renteeColumn;
    @FXML
    private TableColumn<Reservation, String> equipmentColumn;
    @FXML
    private TableColumn<Reservation, String> reservedOnColumn;
    @FXML
    private TableColumn<Reservation, String> rentedUntilColumn;
    @FXML
    private TableColumn<Reservation, String> approveButtonColumn;
    @FXML
    private TableColumn<Reservation, String> rejectButtonColumn;

    public void init(ViewHandler viewHandler, UnapprovedReservationViewModel unapprovedReservationViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = unapprovedReservationViewModel;
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

        reservedOnColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getReservationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no start date>");
            }
        });
        rentedUntilColumn.setCellValueFactory(p -> {
            if (p.getValue() != null && p.getValue().getRentedFor() != null) {
                return new SimpleStringProperty(p.getValue().getRentedFor().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no end date>");
            }
        });
        approveButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Reservation, String> call(final TableColumn<Reservation, String> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Approve");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Reservation reservation = getTableView().getItems().get(getIndex());
                            viewModel.approveReservation(reservation);
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
        rejectButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Reservation, String> call(final TableColumn<Reservation, String> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Reject");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Reservation reservation = getTableView().getItems().get(getIndex());
                            confirmRejection(reservation);
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
        viewHandler.openView(ViewHandler.MANAGER_MAIN_MENU_VIEW);
    }

    private void confirmRejection(Reservation reservation) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reason for rejection");
        dialog.setHeaderText("Reason for rejection");
        dialog.setContentText("Give a reason for rejection:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(reason -> viewModel.rejectReservation(reservation, reason));
    }

}
