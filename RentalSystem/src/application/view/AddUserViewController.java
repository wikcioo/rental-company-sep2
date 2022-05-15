package application.view;

import application.viewmodel.AddUserViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;

public class AddUserViewController {
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;
    @FXML
    private ToggleGroup userRole;
    @FXML
    private Label error;
    private ViewHandler viewHandler;
    private AddUserViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, AddUserViewModel addUserViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = addUserViewModel;
        this.root = root;
        viewModel.bindFirstName(firstName.textProperty());
        viewModel.bindLastName(lastName.textProperty());
        viewModel.bindPhoneNumber(phoneNumber.textProperty());
        viewModel.bindEmail(email.textProperty());
        viewModel.bindPassword(password1.textProperty());
        viewModel.bindError(error.textProperty());
    }

    @FXML
    public void onAddButtonClick() {
        if (!password1.getText().equals(password2.getText())) {
            error.setTextFill(Paint.valueOf("RED"));
            error.setText("Passwords do not match!");
        } else {
            viewModel.addUser(((RadioButton)userRole.getSelectedToggle()).getText());
            error.setTextFill(Paint.valueOf("GREEN"));
            error.setText("User added successfully!");
            firstName.clear();
            lastName.clear();
            phoneNumber.clear();
            email.clear();
            password1.clear();
            password2.clear();
        }
    }

    @FXML
    public void onBackButtonClick() {
        viewHandler.openView(ViewHandler.MANAGER_EQUIPMENT_LIST_VIEW);
    }

    public void reset() {

    }

    public Region getRoot() {
        return root;
    }
}
