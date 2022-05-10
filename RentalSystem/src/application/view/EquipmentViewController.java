package application.view;

import application.model.Equipment;
import application.model.Reservation;
import application.model.User;
import application.viewmodel.EquipmentViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

public class EquipmentViewController {
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField model;
    @FXML
    public TextField category;
    @FXML
    public TextField price;
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
    private Label error;

    public void init(ViewHandler viewHandler, EquipmentViewModel equipmentViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = equipmentViewModel;
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
                            price.setText(Double.toString(data.getPrice()));
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
        viewModel.bindErrorLabel(error.textProperty());
        viewModel.retrieveAllEquipment();
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
    }

}
