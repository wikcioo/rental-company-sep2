package application.view;

import application.view.manager.*;
import application.view.rentee.EquipmentViewController;
import application.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOException;

public class ViewFactory {
    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;
    private AddEquipmentViewController addEquipmentViewController;
    private EquipmentViewController equipmentViewController;
    private LogInViewController logInViewController;
    private ReservationViewController reservationViewController;
    private ManagerEquipmentViewController managerEquipmentViewController;
    private AddUserViewController addUserViewController;
    private ApprovedReservationViewController approvedReservationViewController;
    private RegisteredUserViewController registeredUserViewController;

    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
    }

    public Region loadAddEquipmentView() {
        if (addEquipmentViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/manager/AddEquipmentView.fxml"));
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
            loader.setLocation(getClass().getResource("gui/rentee/EquipmentView.fxml"));
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
                    getClass().getResource("gui/manager/ManagerEquipmentView.fxml"));
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
            loader.setLocation(getClass().getResource("gui/manager/ReservationView.fxml"));
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
            loader.setLocation(getClass().getResource("gui/manager/AddUserView.fxml"));
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
            loader.setLocation(getClass().getResource("gui/manager/ApprovedReservationView.fxml"));
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

    public Region loadRegisteredUserView() {
        if (registeredUserViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/manager/RegisteredUserView.fxml"));
            try {
                Region root = loader.load();
                registeredUserViewController = loader.getController();
                registeredUserViewController.init(viewHandler,
                        viewModelFactory.getRegisteredUserViewModel(), root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        registeredUserViewController.reset();
        return registeredUserViewController.getRoot();
    }
}
