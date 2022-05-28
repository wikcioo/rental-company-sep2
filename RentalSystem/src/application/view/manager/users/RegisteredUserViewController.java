package application.view.manager.users;

import application.model.users.User;
import application.view.ViewHandler;
import application.viewmodel.manager.users.RegisteredUserViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.util.Optional;

public class RegisteredUserViewController {
    private ViewHandler viewHandler;
    private RegisteredUserViewModel viewModel;
    private Region root;

    @FXML
    private Label error;
    @FXML
    private TableView<User> registeredUserTable;
    @FXML
    private TableColumn<User, String> userFirstNameColumn;
    @FXML
    private TableColumn<User, String> userLastNameColumn;
    @FXML
    private TableColumn<User, String> userPhoneNumberColumn;
    @FXML
    private TableColumn<User, String> userEmailColumn;
    @FXML
    private TableColumn<User, String> deleteButtonColumn;

    public void init(ViewHandler viewHandler, RegisteredUserViewModel registeredUserViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = registeredUserViewModel;
        this.root = root;

        userFirstNameColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getFirstName());
            } else {
                return new SimpleStringProperty("<no first name>");
            }
        });

        userLastNameColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getLastName());
            } else {
                return new SimpleStringProperty("<no last name>");
            }
        });

        userPhoneNumberColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getPhoneNumber());
            } else {
                return new SimpleStringProperty("<no phone number>");
            }
        });

        userEmailColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getEmail());
            } else {
                return new SimpleStringProperty("<no email>");
            }
        });

        deleteButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<User, String> call(TableColumn<User, String> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            confirmDeletion(user);
                            registeredUserTable.refresh();
                        });
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });

        viewModel.bindRegisteredUserList(registeredUserTable.itemsProperty());
        viewModel.bindErrorLabel(error.textProperty());
    }

    @FXML
    public void backButtonPressed() {
        viewHandler.openView(ViewHandler.MANAGER_MAIN_MENU_VIEW);
    }

    private void confirmDeletion(User user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("User Deletion");
        alert.setContentText("Are you sure you want to delete " + user.getFirstName() + " " + user.getLastName() + " from the system?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            viewModel.deleteUser(user.getEmail());
        }
    }

    public void reset() {
        error.setText("");
    }

    public Region getRoot() {
        return root;
    }

    public void addUserPressed() {
        viewHandler.openView(ViewHandler.ADD_USER_VIEW);
    }
}
