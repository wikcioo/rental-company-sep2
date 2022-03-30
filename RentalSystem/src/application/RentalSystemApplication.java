package application;

import application.model.Model;
import application.model.ModelManager;
import application.view.ViewHandler;
import application.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class RentalSystemApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new ModelManager();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(primaryStage);
    }
}
