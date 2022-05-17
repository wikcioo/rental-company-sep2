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

import java.time.LocalDate;

public class ManagerEquipmentViewController {
    @FXML
    public TextField model;
    @FXML
    public TextField category;
    @FXML
    public Label error;
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
//    @FXML
//    private TableColumn<Equipment, String> editColumn;

    public void init(ViewHandler viewHandler,
                     ManagerEquipmentViewModel managerEquipmentViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = managerEquipmentViewModel;
        this.root = root;

        modelColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Equipment, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getModel());
                } else {
                    return new SimpleStringProperty("<no model>");
                }
            }
        });

        categoryColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Equipment, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getCategory());
                } else {
                    return new SimpleStringProperty("<no category>");
                }
            }
        });
        availabilityColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Equipment, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(
                            Boolean.toString(p.getValue().isAvailable()));
                } else {
                    return new SimpleStringProperty("<no category>");
                }
            }
        });
        changeAvailabilityColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Equipment, String> call(
                    final TableColumn<Equipment, String> param) {
                final TableCell<Equipment, String> cell = new TableCell<>() {
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
                return cell;
            }
        });

//        editColumn.setCellFactory(new Callback<>() {
//            @Override
//            public TableCell<Equipment, String> call(
//                    final TableColumn<Equipment, String> param) {
//                final TableCell<Equipment, String> cell = new TableCell<>() {
//                    private final Button btn = new Button("Edit");
//
//                    {
//                        btn.setOnAction((ActionEvent event) -> {
//                            Equipment data = getTableView().getItems().get(getIndex());
//                            model.setText(data.getModel());
//                            category.setText(data.getCategory());
//                            viewModel.bindSelectedEquipment(new SimpleObjectProperty<>(data));
//                        });
//                    }
//
//                    @Override
//                    public void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            setGraphic(btn);
//                        }
//                    }
//                };
//                return cell;
//            }
//        });
        viewModel.bindEquipmentList(equipmentTable.itemsProperty());
        viewModel.bindErrorLabel(error.textProperty());
        viewModel.retrieveAllEquipment();
    }

    public void reset() {
        viewModel.retrieveAllEquipment();
    }

    public Region getRoot() {
        return root;
    }

//    public void editButtonPressed() {
//
//    }

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
}
