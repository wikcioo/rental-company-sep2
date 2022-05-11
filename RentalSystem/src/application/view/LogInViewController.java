package application.view;

import application.viewmodel.LogInViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;

public class LogInViewController {
    @FXML
    public TextField email;
    @FXML
    public TextField password;
    @FXML
    public Label error;
    private ViewHandler viewHandler;
    private LogInViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, LogInViewModel logInViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = logInViewModel;
        this.root = root;
        viewModel.bindEmail(email.textProperty());
        viewModel.bindPassword(password.textProperty());
    }

    public void reset() {

    }

    public Region getRoot() {
        return root;
    }

    public void onLogIn() {
        error.setTextFill(Paint.valueOf("BLUE"));
        error.setText("Processing...");
        String result = viewModel.logIn();
        if (result.equals("Manager")) {
            viewHandler.openView(ViewHandler.MANAGER_EQUIPMENT_LIST_VIEW);
        } else if (result.equals("Rentee")) {
            viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW);
        } else {
            error.setTextFill(Paint.valueOf("RED"));
            error.setText("Wrong credentials");
        }
    }
}