package application.view;

import application.model.reservations.Approved;
import application.model.reservations.Reservation;
import application.viewmodel.ApprovedReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;

public class ApprovedReservationViewController {
    private ViewHandler viewHandler;
    private ApprovedReservationViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Approved> reservationTable;
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
        renteeColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getRentee().toString());
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
                    return new SimpleStringProperty("<no end date>");
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

        daysOverdueColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> p) {
                if (p.getValue() != null && p.getValue().getRentedFor() != null) {
                    return new SimpleStringProperty(p.getValue().getDaysOverdue().toString());
                } else {
                    return new SimpleStringProperty("<no end date>");
                }
            }
        });

        returnButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Reservation, String> call(final TableColumn<Reservation, String> param) {
                final TableCell<Reservation, String> cell = new TableCell<>() {
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

}
