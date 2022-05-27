package application.view.manager;

import application.model.reservations.Reservation;
import application.view.ViewHandler;
import application.viewmodel.manager.ManagerMainMenuViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Region;

import java.util.Optional;

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
    public void onSetTimeoutButtonClick() {
        Alert serverError = new Alert(Alert.AlertType.ERROR);
        serverError.setTitle("Server communication error");
        serverError.setHeaderText("Failed to connect with the server :(");

        int currentExpirationTime = viewModel.getCurrentExpirationTimeout();
        if (currentExpirationTime == -1) {
            serverError.showAndWait();
        } else {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setResizable(false);
            dialog.setTitle("Reservations timeout");
            dialog.setHeaderText("Current timeout in seconds: " + currentExpirationTime);
            dialog.setContentText("New timeout in seconds: ");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    if (!viewModel.setCurrentExpirationTimeout(Integer.parseInt(result.get()))) {
                        serverError.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    Alert invalidInput = new Alert(Alert.AlertType.ERROR);
                    invalidInput.setTitle("Error");
                    invalidInput.setHeaderText("Invalid input");
                    invalidInput.showAndWait();
                }
            }
        }
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
