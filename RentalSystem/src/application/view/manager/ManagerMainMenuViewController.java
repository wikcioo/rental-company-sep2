package application.view.manager;

import application.view.ViewHandler;
import application.viewmodel.manager.ManagerMainMenuViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class ManagerMainMenuViewController {
    @FXML
    private Label loggedUser;
    private ViewHandler viewHandler;
    private ManagerMainMenuViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, ManagerMainMenuViewModel managerMainMenuViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = managerMainMenuViewModel;
        this.root = root;
        viewModel.bindLoggedUser(loggedUser.textProperty());
    }

    public void reset() {
        viewModel.displayLoggedUser();
    }

    public Region getRoot() {
        return root;
    }

    public void onViewReservations() {
        viewHandler.openView(ViewHandler.RESERVATION_LIST_VIEW);
    }

    public void onViewApprovedReservations() {
        viewHandler.openView(ViewHandler.APPROVED_RESERVATION_LIST_VIEW);
    }

    public void onViewRejectedReservations() {
        viewHandler.openView(ViewHandler.REJECTED_RESERVATION_LIST_VIEW);
    }

    public void onViewExpiredReservations() {
        viewHandler.openView(ViewHandler.EXPIRED_RESERVATION_LIST_VIEW);
    }

    @FXML
    public void onRegisteredUsersButtonClick() {
        viewHandler.openView(ViewHandler.REGISTERED_USER_VIEW);
    }

    @FXML
    public void onLogOutButtonClick() {
        viewHandler.openView(ViewHandler.LOG_IN_VIEW);
    }

    public void onViewAllEquipment() {
        viewHandler.openView(ViewHandler.MANAGER_EQUIPMENT_LIST_VIEW);
    }

    public void onViewReturnedReservations() {
        viewHandler.openView(ViewHandler.RETURNED_RESERVATION_LIST_VIEW);
    }
}
