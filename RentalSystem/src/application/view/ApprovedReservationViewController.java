package application.view;

import application.model.Reservation;
import application.viewmodel.ApprovedReservationViewModel;
import application.viewmodel.ReservationViewModel;
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
    private TableColumn<Reservation, String> approvalColumn;
    @FXML
    private TableColumn<Reservation, String> approveButtonColumn;


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

        //Make column "Return By"
        endDateColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getReservationEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                } else {
                    return new SimpleStringProperty("<no end date>");
                }
            }
        });

        //Make column "How long overdue, or how much left"
        approvalColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().isApproved().toString());
                } else {
                    return new SimpleStringProperty("<no end date>");
                }
            }
        });

        //Make column "Return Equipment"
        approveButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Reservation, String> call(final TableColumn<Reservation, String> param) {
                final TableCell<Reservation, String> cell = new TableCell<>() {
                    private final Button btn = new Button("Approve");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Reservation data = getTableView().getItems().get(getIndex());
                            viewModel.approveReservation(data);
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
        viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW);
    }

    public void approvedButtonPressed() {
        viewModel.showApprovedReservations();
    }

    public void showAllReservations() {
        viewModel.showAllReservations();
    }
}
