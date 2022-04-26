package application.viewmodel;

import application.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LogInViewModel {
    private final StringProperty name;
    private final StringProperty password;
    private final Model model;

    public LogInViewModel(Model model) {
        this.model = model;
        name = new SimpleStringProperty("");
        password = new SimpleStringProperty("");
    }

    public void bindName(StringProperty property) {
        name.bind(property);
    }

    public void bindPassword(StringProperty property) {
        password.bind(property);
    }

    public boolean logIn(){
        return model.logIn(name.get(), password.get());
    }
}
