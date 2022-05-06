package application.view;

import application.viewmodel.AddEquipmentViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class AddEquipmentViewController {
    @FXML
    public TextField equipmentModel;
    @FXML
    public TextField category;
    private ViewHandler viewHandler;
    private AddEquipmentViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, AddEquipmentViewModel addEquipmentViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = addEquipmentViewModel;
        this.root = root;
        viewModel.bindEquipmentModel(equipmentModel.textProperty());
        viewModel.bindCategory(category.textProperty());
    }

    public void reset() {

    }

    public Region getRoot() {
        return root;
    }

    public void onAddEquipment() {
        viewModel.addEquipment();
        viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW);
    }

    public void onCancel() {
        viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW);
    }
}
