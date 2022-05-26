package application.view.rentee;

import application.model.reservations.Reservation;
import application.view.ViewHandler;
import application.viewmodel.rentee.RenteeReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;

public class RenteeReservationViewController {
    @FXML
    public Label error;
    private ViewHandler viewHandler;
    private RenteeReservationViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, String> reservationIdColumn;
    @FXML
    private TableColumn<Reservation, String> equipmentColumn;
    @FXML
    public TableColumn<Reservation, String> reservationTypeColumn;
    @FXML
    private TableColumn<Reservation, String> startDateColumn;
    @FXML
    private TableColumn<Reservation, String> endDateColumn;
    @FXML
    public TableColumn<Reservation, String> detailsColumn;

    public void init(ViewHandler viewHandler, RenteeReservationViewModel renteeReservationViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = renteeReservationViewModel;
        this.root = root;

        reservationIdColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(Integer.toString(p.getValue().getId()));
            } else {
                return new SimpleStringProperty("<no reservation id>");
            }
        });

        equipmentColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getEquipment().toString());
            } else {
                return new SimpleStringProperty("<no equipment>");
            }
        });

        reservationTypeColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().status());
            } else {
                return new SimpleStringProperty("<no equipment>");
            }
        });

        startDateColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getReservationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no start date>");
            }
        });

        endDateColumn.setCellValueFactory(p -> {
            if (p.getValue() != null && p.getValue().getRentedFor() != null) {
                return new SimpleStringProperty(p.getValue().getRentedFor().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no end date>");
            }
        });

        detailsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Reservation, String> call(final TableColumn<Reservation, String> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Reservation reservation = getTableView().getItems().get(getIndex());
                            showDetails(reservation);
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
        viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW);
    }


    private void showDetails(Reservation reservation) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Details");
        alert.setHeaderText("Reservation details");
        alert.setContentText("Status - " + reservation.status() + "\n" + reservation);
        alert.showAndWait();
    }
}
