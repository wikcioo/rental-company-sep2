package application.view.manager;

import application.view.ViewHandler;
import application.viewmodel.manager.AddUserViewModel;
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
        if (!highlightEmptyFields()) {
            error.setTextFill(Paint.valueOf("RED"));
            if (!password1.getText().equals(password2.getText())) {
                error.setText("Passwords do not match!");
            } else {
                if (userRole.getSelectedToggle() == null) {
                    error.setText("Please select manager or rentee radio button");
                } else if (viewModel.addUser(((RadioButton) userRole.getSelectedToggle()).getText())) {
                    error.setTextFill(Paint.valueOf("GREEN"));
                    error.setText("User added successfully!");
                    resetInputFields();
                    userRole.selectToggle(null);
                }
            }
        }
    }

    @FXML
    public void onBackButtonClick() {
        viewHandler.openView(ViewHandler.REGISTERED_USER_VIEW);
    }

    private boolean highlightEmptyFields() {
        resetFieldsStyle();
        String emptyInputFieldBorderColor = "#FF0000";
        boolean emptyFieldOccurred = false;
        if (firstName.getText().isEmpty()) {
            firstName.setStyle("-fx-text-box-border: " + emptyInputFieldBorderColor + ";");
            emptyFieldOccurred = true;
        }
        if (lastName.getText().isEmpty()) {
            lastName.setStyle("-fx-text-box-border: " + emptyInputFieldBorderColor + ";");
            emptyFieldOccurred = true;
        }
        if (phoneNumber.getText().isEmpty()) {
            phoneNumber.setStyle("-fx-text-box-border: " + emptyInputFieldBorderColor + ";");
            emptyFieldOccurred = true;
        }
        if (email.getText().isEmpty()) {
            email.setStyle("-fx-text-box-border: " + emptyInputFieldBorderColor + ";");
            emptyFieldOccurred = true;
        }
        if (password1.getText().isEmpty()) {
            password1.setStyle("-fx-text-box-border: " + emptyInputFieldBorderColor + ";");
            emptyFieldOccurred = true;
        }
        if (password2.getText().isEmpty()) {
            password2.setStyle("-fx-text-box-border: " + emptyInputFieldBorderColor + ";");
            emptyFieldOccurred = true;
        }

        return emptyFieldOccurred;
    }

    private void resetFieldsStyle() {
        firstName.setStyle(null);
        lastName.setStyle(null);
        phoneNumber.setStyle(null);
        email.setStyle(null);
        password1.setStyle(null);
        password2.setStyle(null);
    }

    private void resetInputFields() {
        firstName.clear();
        lastName.clear();
        phoneNumber.clear();
        email.clear();
        password1.clear();
        password2.clear();
    }

    public void reset() {
        resetFieldsStyle();
        resetInputFields();
        userRole.selectToggle(null);
        error.setText(null);
    }

    public Region getRoot() {
        return root;
    }
}
