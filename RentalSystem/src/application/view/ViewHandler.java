package application.view;

import application.viewmodel.ViewModelFactory;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ViewHandler {
    public static final String DUMMY_VIEW = "dummy_view";

    private Stage primaryStage;
    private final Scene currentScene;
    private final ViewFactory viewFactory;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewFactory = new ViewFactory(this, viewModelFactory);
        this.currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView(DUMMY_VIEW);
    }

    public void openView(String id) {
        Region root = switch(id) {
            case DUMMY_VIEW -> viewFactory.loadDummyView();
            default -> throw new IllegalArgumentException("Unknown id: " + id);
        };

        this.currentScene.setRoot(root);
        if (root.getUserData() == null) {
            this.primaryStage.setTitle("");
        } else {
            this.primaryStage.setTitle(root.getUserData().toString());
        }

        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                closeView();
                Platform.exit();
                System.exit(0);
            }
        });

        this.primaryStage.setScene(this.currentScene);
        this.primaryStage.sizeToScene();
        this.primaryStage.show();
    }

    public void closeView() {
        this.primaryStage.close();
    }
}
