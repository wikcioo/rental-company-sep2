package application.viewmodel;

import application.model.Model;
import application.model.users.User;
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
                User currentUser = model.getUser(email.get());
                if(result.equals("Manager")){
                    currentUser.setManager(true);
                    model.retrieveAllEquipment();
                }
                else{
                    model.retrieveAllUnreservedEquipment();
                }
                model.retrieveAllUsers();
                model.setCurrentlyLoggedInUser(currentUser);
                model.refreshReservations();
            }
            return result;
        } catch (RemoteException e) {
            return "ServerConnectionFailure";
        }
    }

}
