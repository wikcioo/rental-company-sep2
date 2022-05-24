package application.view.rentee;

import application.model.equipment.Equipment;
import application.view.ViewHandler;
import application.viewmodel.rentee.EquipmentViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.time.LocalDate;

public class EquipmentViewController {
    @FXML
    public DatePicker datePicker;
    @FXML
    public Label equipmentModel;
    @FXML
    public Label category;
    private ViewHandler viewHandler;
    private EquipmentViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Equipment> equipmentTable;
    @FXML
    private TableColumn<Equipment, String> equipmentIdColumn;
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

    private static final LocalDate MIN_DATE = LocalDate.now();
    private static final LocalDate MAX_DATE = MIN_DATE.plusWeeks(4);

    public void init(ViewHandler viewHandler, EquipmentViewModel equipmentViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = equipmentViewModel;
        this.root = root;

        //Only allows user to pick a date from today to 4 weeks from today.
        datePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(MAX_DATE) || item.isBefore(MIN_DATE));
                    }
                });
        datePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                datePicker.setValue(datePicker.getValue());
            }
        });

        equipmentIdColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(Integer.toString(p.getValue().getEquipmentId()));
            } else {
                return new SimpleStringProperty("<no model>");
            }
        });

        modelColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getModel());
            } else {
                return new SimpleStringProperty("<no model>");
            }
        });

        categoryColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getCategory());
            } else {
                return new SimpleStringProperty("<no category>");
            }
        });

        reserveColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Equipment, String> call(final TableColumn<Equipment, String> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Select");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Equipment data = getTableView().getItems().get(getIndex());
                            equipmentModel.setText(data.getModel());
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
            }
        });

        viewModel.bindEquipmentList(equipmentTable.itemsProperty());
        viewModel.bindEquipmentErrorLabel(equipmentError.textProperty());
        viewModel.bindReservationErrorLabel(reservationError.textProperty());
        viewModel.bindLoggedUser(loggedUser.textProperty());
    }

    public void reset() {
        equipmentTable.refresh();
        viewModel.bindSelectedEquipment(new SimpleObjectProperty<>());
        equipmentModel.setText("Unselected");
        category.setText("Unselected");
        datePicker.setValue(null);
        reservationError.setText(null);
        viewModel.displayLoggedUser();
        equipmentError.setText("");
        reservationError.setText("");
    }

    public Region getRoot() {
        return root;
    }

    public void OnReserve() {
        if (equipmentModel.getText().equals("Unselected") && category.getText().equals("Unselected")) {
            reservationError.setText("You must select an item to reserve");
        } else if (datePicker.getValue() == null) {
            reservationError.setText("You must choose the date");
        } else if (datePicker.getValue().isAfter(MAX_DATE) || datePicker.getValue().isBefore(MIN_DATE)) {
            reservationError.setText("You must choose a date in the correct interval");
        } else {
            viewModel.bindReservationEndDate(new SimpleObjectProperty<>(datePicker.getValue().atTime(14, 0)));
            viewModel.reserveEquipment();
            equipmentModel.setText("Unselected");
            category.setText("Unselected");
            datePicker.setValue(null);
            viewModel.bindSelectedEquipment(new SimpleObjectProperty<>());
        }
    }

    public void onViewManagerEquipment() {
        viewHandler.openView(ViewHandler.MANAGER_EQUIPMENT_LIST_VIEW);
    }

    @FXML
    public void onLogOutButtonClick() {
        viewHandler.openView(ViewHandler.LOG_IN_VIEW);
    }

}
