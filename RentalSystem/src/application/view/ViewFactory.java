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
    private EquipmentViewController equipmentViewController;
    private LogInViewController logInViewController;
    private ReservationViewController reservationViewController;
    private ManagerEquipmentViewController managerEquipmentViewController;
    private AddUserViewController addUserViewController;
    private ApprovedReservationViewController approvedReservationViewController;

    public ViewFactory(ViewHandler viewHandler,
                       ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
    }

    public Region loadDummyView() {
        if (dummyViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/DummyUI.fxml"));
            try {
                Region root = loader.load();
                dummyViewController = loader.getController();
                dummyViewController.init(viewHandler,
                        viewModelFactory.getDummyViewModel(), root);
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
                addEquipmentViewController.init(viewHandler,
                        viewModelFactory.getAddEquipmentViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        addEquipmentViewController.reset();
        return addEquipmentViewController.getRoot();
    }

    public Region loadShowEquipmentView() {
        if (equipmentViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/EquipmentView.fxml"));
            try {
                Region root = loader.load();
                equipmentViewController = loader.getController();
                equipmentViewController.init(viewHandler,
                        viewModelFactory.getEquipmentViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        equipmentViewController.reset();
        return equipmentViewController.getRoot();
    }

    public Region loadLogInView() {
        if (logInViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/LogInView.fxml"));
            try {
                Region root = loader.load();
                logInViewController = loader.getController();
                logInViewController.init(viewHandler,
                        viewModelFactory.getLogInViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        logInViewController.reset();
        return logInViewController.getRoot();
    }

    public Region loadManagerEquipmentView() {
        if (managerEquipmentViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    getClass().getResource("gui/ManagerEquipmentView.fxml"));
            try {
                Region root = loader.load();
                managerEquipmentViewController = loader.getController();
                managerEquipmentViewController.init(viewHandler,
                        viewModelFactory.getManagerEquipmentViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        managerEquipmentViewController.reset();
        return managerEquipmentViewController.getRoot();
    }

    public Region loadReservationListView() {
        if (reservationViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/ReservationView.fxml"));
            try {
                Region root = loader.load();
                reservationViewController = loader.getController();
                reservationViewController.init(viewHandler,
                        viewModelFactory.getReservationViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        reservationViewController.reset();
        return reservationViewController.getRoot();
    }

    public Region loadAddUserView() {
        if (addUserViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/AddUserView.fxml"));
            try {
                Region root = loader.load();
                addUserViewController = loader.getController();
                addUserViewController.init(viewHandler,
                        viewModelFactory.getAddUserViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        addUserViewController.reset();
        return addUserViewController.getRoot();
    }

    public Region loadApprovedReservationView() {
        if (approvedReservationViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/ApprovedReservationView.fxml"));
            try {
                Region root = loader.load();
                approvedReservationViewController = loader.getController();
                approvedReservationViewController.init(viewHandler,
                        viewModelFactory.getApprovedReservationViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        approvedReservationViewController.reset();
        return approvedReservationViewController.getRoot();
    }

}
