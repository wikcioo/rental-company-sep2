package application.view.manager;

import application.view.ViewHandler;
import application.viewmodel.manager.AddEquipmentViewModel;
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
        equipmentModel.clear();
        category.clear();
    }

    public Region getRoot() {
        return root;
    }

    public void onAddEquipment() {
        if (!highlightEmptyFields()) {
            viewModel.addEquipment();
            viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW);
        }
    }

    private boolean highlightEmptyFields() {
        resetFieldsStyle();
        String emptyInputFieldBorderColor = "#FF0000";
        boolean emptyFieldOccurred = false;
        if (equipmentModel.getText().isEmpty()) {
            equipmentModel.setStyle("-fx-text-box-border: " + emptyInputFieldBorderColor + ";");
            emptyFieldOccurred = true;
        }
        if (category.getText().isEmpty()) {
            category.setStyle("-fx-text-box-border: " + emptyInputFieldBorderColor + ";");
            emptyFieldOccurred = true;
        }

        return emptyFieldOccurred;
    }

    private void resetFieldsStyle() {
        equipmentModel.setStyle(null);
        category.setStyle(null);
    }

    public void onCancel() {
        viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW);
    }
}
