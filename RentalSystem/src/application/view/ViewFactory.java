package application.view;

import application.util.ConsoleLogger;
import application.util.Logger;
import application.view.manager.*;
import application.view.manager.equipment.AddEquipmentViewController;
import application.view.manager.equipment.ManagerEquipmentViewController;
import application.view.manager.reservations.*;
import application.view.manager.users.AddUserViewController;
import application.view.manager.users.RegisteredUserViewController;
import application.view.rentee.RenteeEquipmentViewController;
import application.view.rentee.RenteeMainMenuViewController;
import application.view.rentee.RenteeReservationViewController;
import application.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class ViewFactory {
    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;
    private final Logger logger;
    private AddEquipmentViewController addEquipmentViewController;
    private RenteeEquipmentViewController renteeEquipmentViewController;
    private LogInViewController logInViewController;
    private UnapprovedReservationViewController unapprovedReservationViewController;
    private ManagerEquipmentViewController managerEquipmentViewController;
    private AddUserViewController addUserViewController;
    private ApprovedReservationViewController approvedReservationViewController;
    private RegisteredUserViewController registeredUserViewController;
    private RejectedReservationViewController rejectedReservationViewController;
    private ExpiredReservationViewController expiredReservationViewController;
    private RenteeReservationViewController renteeReservationViewController;
    private ManagerMainMenuViewController managerMainMenuViewController;
    private ReturnedReservationViewController returnedReservationViewController;
    private RenteeMainMenuViewController renteeMainMenuViewController;

    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        this.logger = ConsoleLogger.getInstance();
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
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        addEquipmentViewController.reset();
        return addEquipmentViewController.getRoot();
    }

    public Region loadShowEquipmentView() {
        if (renteeEquipmentViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/rentee/RenteeEquipmentView.fxml"));
            try {
                Region root = loader.load();
                renteeEquipmentViewController = loader.getController();
                renteeEquipmentViewController.init(viewHandler,
                        viewModelFactory.getEquipmentViewModel(), root);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        renteeEquipmentViewController.reset();
        return renteeEquipmentViewController.getRoot();
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
            } catch (Exception e) {
                logger.error(e.getMessage());
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
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        managerEquipmentViewController.reset();
        return managerEquipmentViewController.getRoot();
    }

    public Region loadReservationListView() {
        if (unapprovedReservationViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/manager/UnapprovedReservationView.fxml"));
            try {
                Region root = loader.load();
                unapprovedReservationViewController = loader.getController();
                unapprovedReservationViewController.init(viewHandler,
                        viewModelFactory.getReservationViewModel(), root);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        unapprovedReservationViewController.reset();
        return unapprovedReservationViewController.getRoot();
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
            } catch (Exception e) {
                logger.error(e.getMessage());
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
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        approvedReservationViewController.reset();
        return approvedReservationViewController.getRoot();
    }

    public Region loadRejectedReservationView() {
        if (rejectedReservationViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/manager/RejectedReservationView.fxml"));
            try {
                Region root = loader.load();
                rejectedReservationViewController = loader.getController();
                rejectedReservationViewController.init(viewHandler,
                    viewModelFactory.getRejectedReservationViewModel(), root);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        rejectedReservationViewController.reset();
        return rejectedReservationViewController.getRoot();
    }
    public Region loadExpiredReservationView() {
        if (expiredReservationViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/manager/ExpiredReservationView.fxml"));
            try {
                Region root = loader.load();
                expiredReservationViewController = loader.getController();
                expiredReservationViewController.init(viewHandler,
                    viewModelFactory.getExpiredReservationViewModel(), root);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        expiredReservationViewController.reset();
        return expiredReservationViewController.getRoot();
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
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        registeredUserViewController.reset();
        return registeredUserViewController.getRoot();
    }

    public Region loadRenteeReservationView() {
        if (renteeReservationViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/rentee/RenteeReservationView.fxml"));
            try {
                Region root = loader.load();
                renteeReservationViewController = loader.getController();
                renteeReservationViewController.init(viewHandler,
                        viewModelFactory.getRenteeReservationViewModel(), root);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        renteeReservationViewController.reset();
        return renteeReservationViewController.getRoot();
    }

    public Region loadManagerMainMenuView() {
        if (managerMainMenuViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/manager/ManagerMainMenuView.fxml"));
            try {
                Region root = loader.load();
                managerMainMenuViewController = loader.getController();
                managerMainMenuViewController.init(viewHandler,
                        viewModelFactory.getManagerMainMenuViewModel(), root);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        managerMainMenuViewController.reset();
        return managerMainMenuViewController.getRoot();
    }

    public Region loadReturnedReservationView() {
        if (returnedReservationViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/manager/ReturnedReservationView.fxml"));
            try {
                Region root = loader.load();
                returnedReservationViewController = loader.getController();
                returnedReservationViewController.init(viewHandler,
                        viewModelFactory.getReturnedReservationViewModel(), root);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        returnedReservationViewController.reset();
        return returnedReservationViewController.getRoot();
    }

    public Region loadRenteeMainMenuView() {
        if (renteeMainMenuViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("gui/rentee/RenteeMainMenuView.fxml"));
            try {
                Region root = loader.load();
                renteeMainMenuViewController = loader.getController();
                renteeMainMenuViewController.init(viewHandler,
                        viewModelFactory.getRenteeMainMenuViewModel(), root);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        renteeMainMenuViewController.reset();
        return renteeMainMenuViewController.getRoot();
    }
}
