package application;

import application.client.RentalSystemClient;
import application.client.RentalSystemClientImplementation;
import application.model.models.ModelManager;
import application.view.ViewHandler;
import application.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.registry.Registry;

public class RentalSystemApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        RentalSystemClient client = new RentalSystemClientImplementation("localhost", Registry.REGISTRY_PORT);
        ModelManager modelManager = new ModelManager(client);
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelManager, modelManager, modelManager);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(primaryStage);
    }
}
