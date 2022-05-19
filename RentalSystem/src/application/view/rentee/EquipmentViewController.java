package application.view.rentee;

import application.model.equipment.Equipment;
import application.view.ViewHandler;
import application.viewmodel.rentee.EquipmentViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.time.LocalDate;

public class EquipmentViewController {
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField model;
    @FXML
    public TextField category;
    private ViewHandler viewHandler;
    private EquipmentViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Equipment> equipmentTable;
    @FXML
    private TableColumn<Equipment, String> modelColumn;
    @FXML
    private TableColumn<Equipment, String> categoryColumn;
    @FXML
    private TableColumn<Equipment, String> reserveColumn;
    @FXML
    private Label equipmentError;
    @FXML
    private Label reservationError;
    @FXML
    private Label loggedUser;

    public void init(ViewHandler viewHandler, EquipmentViewModel equipmentViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = equipmentViewModel;
        this.root = root;

        //Only allows user to pick a date from today to 4 weeks from today.
        LocalDate minDate = LocalDate.now();
        LocalDate maxDate = minDate.plusWeeks(4);
        datePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }
                });

        modelColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Equipment, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getModel());
                } else {
                    return new SimpleStringProperty("<no model>");
                }
            }
        });

        categoryColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Equipment, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getCategory());
                } else {
                    return new SimpleStringProperty("<no category>");
                }
            }
        });

        reserveColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Equipment, String> call(final TableColumn<Equipment, String> param) {
                final TableCell<Equipment, String> cell = new TableCell<>() {
                    private final Button btn = new Button("Reserve");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Equipment data = getTableView().getItems().get(getIndex());
                            model.setText(data.getModel());
                            category.setText(data.getCategory());
                            viewModel.bindSelectedEquipment(new SimpleObjectProperty<>(data));
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

        viewModel.bindEquipmentList(equipmentTable.itemsProperty());
        viewModel.bindErrorLabel(equipmentError.textProperty());
        viewModel.bindLoggedUser(loggedUser.textProperty());
        viewModel.retrieveAllUnreservedEquipment();
    }

    public void reset() {
        equipmentTable.refresh();
        viewModel.bindSelectedEquipment(new SimpleObjectProperty<>());
        model.clear();
        category.clear();
        datePicker.setValue(null);
        reservationError.setText(null);
        viewModel.retrieveAllUnreservedEquipment();
        viewModel.displayLoggedUser();
    }

    public Region getRoot() {
        return root;
    }

    public void onViewReservations() {
        viewHandler.openView(ViewHandler.RESERVATION_LIST_VIEW);
    }

    public void closeButtonPressed() {
        viewHandler.closeView();
    }

    public void OnReserve() {
        reservationError.setTextFill(Paint.valueOf("RED"));
        if (model.getText().isEmpty() && category.getText().isEmpty()) {
            reservationError.setText("You must select an item to reserve");
        } else if (datePicker.getValue() == null) {
            reservationError.setText("You must choose the date");
        } else {
            reservationError.setTextFill(Paint.valueOf("GREEN"));
            reservationError.setText("Success");
            viewModel.bindReservationEndDate(new SimpleObjectProperty<>(datePicker.getValue().atTime(14, 0)));
            viewModel.reserveEquipment();
            model.clear();
            category.clear();
            datePicker.setValue(null);
            viewModel.bindSelectedEquipment(new SimpleObjectProperty<>());
        }
    }

    public void onViewManagerEquipment() {
        viewHandler.openView(ViewHandler.MANAGER_EQUIPMENT_LIST_VIEW);
    }

    @FXML
    public void onLogOutButtonClick() {
        viewHandler.openView(ViewHandler.LOG_IN);
    }

    public void onRefreshButtonClick() {
        viewModel.retrieveAllUnreservedEquipment();
    }
}
