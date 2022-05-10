package application.view;

import application.model.Equipment;
import application.viewmodel.EquipmentViewModel;
import application.viewmodel.ManagerEquipmentViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

public class ManagerEquipmentViewController {
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField model;
    @FXML
    public TextField category;
    private ViewHandler viewHandler;
    private ManagerEquipmentViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Equipment> equipmentTable;
    @FXML
    private TableColumn<Equipment, String> modelColumn;
    @FXML
    private TableColumn<Equipment, String> categoryColumn;
    @FXML
    private TableColumn<Equipment, String> availabilityColumn;
    @FXML
    private TableColumn<Equipment, String> changeAvailabilityColumn;
    @FXML
    private TableColumn<Equipment, String> editColumn;

    public void init(ViewHandler viewHandler, ManagerEquipmentViewModel managerEquipmentViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = managerEquipmentViewModel;
        this.root = root;
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

        changeAvailabilityColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Equipment, String> call(final TableColumn<Equipment, String> param) {
                final TableCell<Equipment, String> cell = new TableCell<>() {
                    private final Button btn = new Button("Change");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Equipment data = getTableView().getItems().get(getIndex());
                            viewModel.toggleAvailability(data);
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

        editColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Equipment, String> call(final TableColumn<Equipment, String> param) {
                final TableCell<Equipment, String> cell = new TableCell<>() {
                    private final Button btn = new Button("Edit");

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
    }

    public void reset() {

    }

    public Region getRoot() {
        return root;
    }

    public void addButtonPressed() {
        viewHandler.openView(ViewHandler.ADD_EQUIPMENT_VIEW);
    }

    public void onViewReservations() {
        viewHandler.openView(ViewHandler.RESERVATION_LIST_VIEW);
    }

    public void closeButtonPressed() {
        viewHandler.closeView();
    }

    public void OnReserve() {
        viewModel.bindReservationEndDate(new SimpleObjectProperty<>(datePicker.getValue().atTime(14, 0)));
        viewModel.reserveEquipment();
        model.clear();
        category.clear();
        datePicker.setValue(null);
    }

}
