package application.view;

import application.viewmodel.AddEquipmentViewModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class AddEquipmentViewController {
    public TextField name;
    public TextField equipmentModel;
    public TextField category;
    public TextField price;
    ViewHandler viewHandler;
    AddEquipmentViewModel viewModel;
    Region root;

    public void init(ViewHandler viewHandler, AddEquipmentViewModel addEquipmentViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = addEquipmentViewModel;
        this.root = root;
        viewModel.bindName(name.textProperty());
        viewModel.bindEquipmentModel(equipmentModel.textProperty());
        viewModel.bindCategory(category.textProperty());
        viewModel.bindPrice(price.textProperty());
    }

    public void reset() {

    }

    public Region getRoot() {
        return root;
    }

    public void onAddEquipment() {
        viewModel.addEquipment();
        viewHandler.openView(ViewHandler.SHOW_VIEW);
    }
}
