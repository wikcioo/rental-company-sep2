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
    private ShowEquipmentViewController showEquipmentViewController;
    private LogInViewController logInViewController;

    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        this.addEquipmentViewController = null;
        this.showEquipmentViewController = null;
        this.logInViewController = null;
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

    public Region loadShowEquipmentView() {
        if (showEquipmentViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/ShowEquipmentView.fxml"));
            try {
                Region root = loader.load();
                showEquipmentViewController = loader.getController();
                showEquipmentViewController.init(viewHandler, viewModelFactory.getShowEquipmentViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        showEquipmentViewController.reset();
        return showEquipmentViewController.getRoot();
    }

    public Region loadLogInView() {
        if (logInViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/LogInView.fxml"));
            try {
                Region root = loader.load();
                logInViewController = loader.getController();
                logInViewController.init(viewHandler, viewModelFactory.getLogInViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        logInViewController.reset();
        return logInViewController.getRoot();
    }
}
