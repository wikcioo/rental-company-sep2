package application.view.manager;

import application.model.reservations.Reservation;
import application.model.users.Rentee;
import application.model.users.User;
import application.view.ViewHandler;
import application.viewmodel.manager.ReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ReservationViewController {
    private ViewHandler viewHandler;
    private ReservationViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, String> renteeColumn;
    @FXML
    private TableColumn<Reservation, String> equipmentColumn;
    @FXML
    private TableColumn<Reservation, String> startDateColumn;
    @FXML
    private TableColumn<Reservation, String> endDateColumn;
    @FXML
    private TableColumn<Reservation, String> approveButtonColumn;
    @FXML
    private TableColumn<Reservation, String> rejectButtonColumn;


    public void init(ViewHandler viewHandler, ReservationViewModel reservationViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = reservationViewModel;
        this.root = root;
        renteeColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> p) {
                if (p.getValue() != null) {
                    User r = p.getValue().getRentee();
                    return new SimpleStringProperty( r.getFirstName() + " " + r.getLastName() + " - " + r.getEmail());
                } else {
                    return new SimpleStringProperty("<no rentee>");
                }
            }
        });

        equipmentColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getEquipment().toString());
                } else {
                    return new SimpleStringProperty("<no equipment>");
                }
            }
        });

        startDateColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getReservationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                } else {
                    return new SimpleStringProperty("<no start date>");
                }
            }
        });
        endDateColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> p) {
                if (p.getValue() != null && p.getValue().getRentedFor() != null) {
                    return new SimpleStringProperty(p.getValue().getRentedFor().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                } else {
                    return new SimpleStringProperty("<no end date>");
                }
            }
        });
        approveButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Reservation, String> call(final TableColumn<Reservation, String> param) {
                final TableCell<Reservation, String> cell = new TableCell<>() {
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
                return cell;
            }
        });
        rejectButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Reservation, String> call(final TableColumn<Reservation, String> param) {
                final TableCell<Reservation, String> cell = new TableCell<>() {
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
                return cell;
            }
        });

        viewModel.bindReservationList(reservationTable.itemsProperty());
    }

    public void reset() {

    }

    public Region getRoot() {
        return root;
    }

    public void backButtonPressed() {
        viewHandler.openView(ViewHandler.MANAGER_EQUIPMENT_LIST_VIEW);
    }

    private void confirmRejection(Reservation reservation) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reason for rejection");
        dialog.setHeaderText("Reason for rejection");
        dialog.setContentText("Give a reason for rejection:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(reason -> viewModel.rejectReservation(reservation));
    }

}
