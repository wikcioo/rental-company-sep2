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
    public static final String ADD_EQUIPMENT_VIEW = "add_equipment_view";
    public static final String EQUIPMENT_LIST_VIEW = "equipment_list_view";
    public static final String MANAGER_EQUIPMENT_LIST_VIEW = "manager_equipment_list_view";
    public static final String RESERVATION_LIST_VIEW = "reservation_list_view";
    public static final String LOG_IN = "log_in_view";
    public static final String ADD_USER_VIEW = "add_user_view";

    private Stage primaryStage;
    private final Scene currentScene;
    private final ViewFactory viewFactory;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewFactory = new ViewFactory(this, viewModelFactory);
        this.currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView(LOG_IN);
    }

    public void openView(String id) {
        Region root = switch (id) {
            case DUMMY_VIEW -> viewFactory.loadDummyView();
            case ADD_EQUIPMENT_VIEW -> viewFactory.loadAddEquipmentView();
            case EQUIPMENT_LIST_VIEW -> viewFactory.loadShowEquipmentView();
            case MANAGER_EQUIPMENT_LIST_VIEW -> viewFactory.loadManagerEquipmentView();
            case LOG_IN -> viewFactory.loadLogInView();
            case RESERVATION_LIST_VIEW -> viewFactory.loadReservationListView();
            case ADD_USER_VIEW -> viewFactory.loadAddUserView();
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
