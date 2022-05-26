package application.view.rentee;

import application.view.ViewHandler;
import application.viewmodel.rentee.RenteeMainMenuViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class RenteeMainMenuViewController {
    @FXML
    private Label loggedUser;
    private ViewHandler viewHandler;
    private RenteeMainMenuViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, RenteeMainMenuViewModel renteeMainMenuViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = renteeMainMenuViewModel;
        this.root = root;
        viewModel.bindLoggedUser(loggedUser.textProperty());
    }

    public void reset() {
        viewModel.displayLoggedUser();
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    public void onLogOutButtonClick() {
        viewHandler.openView(ViewHandler.LOG_IN_VIEW);
    }

    public void onViewEquipment() {
        viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW);
    }

    public void onViewReservations() {
        viewHandler.openView(ViewHandler.RENTEE_RESERVATION_LIST_VIEW);
    }
}
