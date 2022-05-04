package application;

import application.client.RentalSystemClient;
import application.client.RentalSystemClientImplementation;
import application.model.Model;
import application.model.ModelManager;
import application.view.ViewHandler;
import application.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.registry.Registry;

public class RentalSystemApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        RentalSystemClient client = new RentalSystemClientImplementation("localhost", Registry.REGISTRY_PORT);
        Model model = new ModelManager(client);
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(primaryStage);
    }
}
