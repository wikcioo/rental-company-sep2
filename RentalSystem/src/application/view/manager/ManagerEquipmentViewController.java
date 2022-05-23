package application.view.manager;

import application.model.equipment.Equipment;
import application.view.ViewHandler;
import application.viewmodel.manager.ManagerEquipmentViewModel;
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
    private Label error;
    @FXML
    private Label loggedUser;
    private ViewHandler viewHandler;
    private ManagerEquipmentViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Equipment> equipmentTable;
    @FXML
    public TableColumn<Equipment, String> equipmentIdColumn;
    @FXML
    private TableColumn<Equipment, String> modelColumn;
    @FXML
    private TableColumn<Equipment, String> categoryColumn;
    @FXML
    private TableColumn<Equipment, String> availabilityColumn;
    @FXML
    private TableColumn<Equipment, String> changeAvailabilityColumn;

    public void init(ViewHandler viewHandler,
                     ManagerEquipmentViewModel managerEquipmentViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = managerEquipmentViewModel;
        this.root = root;

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

        availabilityColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(
                        Boolean.toString(p.getValue().isAvailable()));
            } else {
                return new SimpleStringProperty("<no category>");
            }
        });

        changeAvailabilityColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Equipment, String> call(
                    final TableColumn<Equipment, String> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Change");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Equipment equipment = getTableView().getItems().get(getIndex());
                            viewModel.bindSelectedEquipment(new SimpleObjectProperty<>(equipment));
                            viewModel.toggleAvailability();
                            equipmentTable.refresh();
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
        viewModel.bindErrorLabel(error.textProperty());
        viewModel.bindLoggedUser(loggedUser.textProperty());
        viewModel.retrieveAllEquipment();
    }

    public void reset() {
        viewModel.retrieveAllEquipment();
        viewModel.displayLoggedUser();
    }

    public Region getRoot() {
        return root;
    }

    public void onViewReservations() {
        viewHandler.openView(ViewHandler.RESERVATION_LIST_VIEW);
    }

    @FXML
    public void onAddNewUserButtonClick() {
        viewHandler.openView(ViewHandler.ADD_USER_VIEW);
    }

    public void backButtonPressed() {
        viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW);
    }

    public void onViewApprovedReservations() {
        viewHandler.openView(ViewHandler.APPROVED_RESERVATION_LIST_VIEW);
    }

    @FXML
    public void onLogOutButtonClick() {
        viewHandler.openView(ViewHandler.LOG_IN);
    }

    public void onRefreshButtonClick() {
        viewModel.retrieveAllEquipment();
    }

    public void addButtonPressed() {
        viewHandler.openView(ViewHandler.ADD_EQUIPMENT_VIEW);
    }
}
