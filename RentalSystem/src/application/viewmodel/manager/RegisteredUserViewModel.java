package application.viewmodel.manager;

import application.model.Model;
import application.model.ModelManager;
import application.model.users.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class RegisteredUserViewModel implements PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<User>> listObjectProperty;
    private final StringProperty errorProperty;

    public RegisteredUserViewModel(Model model) {
        this.model = model;
        this.listObjectProperty = new SimpleObjectProperty<>();
        this.errorProperty = new SimpleStringProperty();
        this.model.addListener(ModelManager.USER_LIST_CHANGED, this);
        listObjectProperty.setValue(FXCollections.observableList(model.getAllUsers()));
    }

    public void bindRegisteredUserList(ObjectProperty<ObservableList<User>> property) {
        property.bind(listObjectProperty);
    }

    public void bindErrorLabel(StringProperty property) {
        property.bindBidirectional(errorProperty);
    }

    public void deleteUser(String email) {
        try {
            model.deleteUser(email);
        } catch (RemoteException e) {
            errorProperty.set("Failed to delete user with email: " + email);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case ModelManager.USER_LIST_CHANGED -> {
                listObjectProperty.setValue(FXCollections.observableList(model.getAllUsers()));
            }
        }
    }
}
