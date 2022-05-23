package application.viewmodel;

import application.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.rmi.RemoteException;

public class LogInViewModel {
    private final StringProperty email;
    private final StringProperty password;
    private final Model model;

    public LogInViewModel(Model model) {
        this.model = model;
        email = new SimpleStringProperty("");
        password = new SimpleStringProperty("");
    }

    public void bindEmail(StringProperty property) {
        email.bind(property);
    }

    public void bindPassword(StringProperty property) {
        password.bind(property);
    }

    public void tryToReconnectClientLooped() {
        while (!model.tryToReconnectClient()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean tryToReconnectClient() {
        return model.tryToReconnectClient();
    }

    public boolean isClientConnected() {
        try {
            model.pingServer();
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    public String logIn() {
        try {
            String result = model.logIn(email.get(), password.get());
            if (!result.equals("Invalid")) {
                model.setCurrentlyLoggedInUser(model.getUser(email.get()));
            }
            return result;
        } catch (RemoteException e) {
            return "ServerConnectionFailure";
        }
    }
}
