package application.view;

import application.viewmodel.LogInViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class LogInViewController {
    @FXML
    public TextField name;
    @FXML
    public TextField password;
    private ViewHandler viewHandler;
    private LogInViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, LogInViewModel logInViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = logInViewModel;
        this.root = root;
        viewModel.bindName(name.textProperty());
        viewModel.bindPassword(password.textProperty());
    }

    public void reset() {

    }

    public Region getRoot() {
        return root;
    }

    public void onLogIn() {
        if (viewModel.logIn()) {
            viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW);
        }
    }
}