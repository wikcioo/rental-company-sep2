package application.view;

import application.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOException;

public class ViewFactory {
    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;
    private DummyViewController dummyViewController;
    private AddEquipmentViewController addEquipmentViewController;

    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        this.addEquipmentViewController = null;
    }

    public Region loadDummyView() {
        if (dummyViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/DummyUI.fxml"));
            try {
                Region root = loader.load();
                dummyViewController = loader.getController();
                dummyViewController.init(viewHandler, viewModelFactory.getDummyViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        dummyViewController.reset();
        return dummyViewController.getRoot();
    }
    public Region loadAddEquipmentView() {
        if (addEquipmentViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/AddEquipmentView.fxml"));
            try {
                Region root = loader.load();
                addEquipmentViewController = loader.getController();
                addEquipmentViewController.init(viewHandler, viewModelFactory.getAddEquipmentViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        addEquipmentViewController.reset();
        return addEquipmentViewController.getRoot();
    }
}
