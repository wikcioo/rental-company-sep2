package application.view;

import application.viewmodel.LogInViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class LogInViewController {
    @FXML
    private StackPane stackPane;
    @FXML
    private VBox vBox;
    private VBox overlayVBox;
    @FXML
    public TextField email;
    @FXML
    public TextField password;
    @FXML
    public Label error;
    private ViewHandler viewHandler;
    private LogInViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, LogInViewModel logInViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = logInViewModel;
        this.root = root;
        error.setTextFill(Paint.valueOf("RED"));
        viewModel.bindEmail(email.textProperty());
        viewModel.bindPassword(password.textProperty());
    }

    public void reset() {
        email.clear();
        password.clear();
        error.setText(null);
        destructOverlay();
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    public synchronized void onLogIn() {
        class LogInValueRetriever implements Runnable {
            private volatile String value;

            @Override
            public void run() {
                value = viewModel.logIn();
            }

            public String getValue() {
                return value;
            }
        }

        if (areInputFieldsEmpty()) {
            error.setText("Please provide all credentials!");
            return;
        }

        new Thread(() -> Platform.runLater(() -> constructOverlayWithMessage(""))).start();

        if (!viewModel.isClientConnected()) {
            viewModel.tryToReconnectClient();
        }

        LogInValueRetriever logInValueRetriever = new LogInValueRetriever();
        Thread t = new Thread(logInValueRetriever);
        t.start();

        new Thread(() -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String result = logInValueRetriever.getValue();

            if (result.equals("ServerConnectionFailure")) {
                Platform.runLater(this::destructOverlay);
                if (!viewModel.tryToReconnectClient()) {
                    Platform.runLater(() -> constructOverlayWithMessage("Failed to connect to the server. Restoring the connection..."));
                    new Thread(() -> {
                        viewModel.tryToReconnectClientLooped();
                        Platform.runLater(this::reset);
                    }).start();
                } else {
                    Platform.runLater(() -> error.setText("There was a problem connecting to you account.\nPlease try again"));
                }
            } else {
                switch (result) {
                    case "Manager" -> {
                        Platform.runLater(() -> viewHandler.openView(ViewHandler.MANAGER_MAIN_MENU_VIEW));
                    }
                    case "Rentee" -> {
                        Platform.runLater(() -> viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW));
                    }
                    default -> {
                        Platform.runLater(() -> {
                            destructOverlay();
                            error.setText("Wrong credentials");
                        });
                    }
                }
            }
        }).start();
    }

    private boolean areInputFieldsEmpty() {
        return email.getText().isEmpty() || password.getText().isEmpty();
    }

    @FXML
    public void onExitButtonClick() {
        viewHandler.closeView();
    }

    private void constructOverlayWithMessage(String msg) {
        ProgressIndicator pi = new ProgressIndicator();
        Label message = new Label(msg);
        overlayVBox = new VBox();
        overlayVBox.getChildren().addAll(pi, message);
        overlayVBox.setAlignment(Pos.CENTER);
        vBox.setDisable(true);
        stackPane.getChildren().add(overlayVBox);
        stackPane.setStyle("-fx-background-color: rgba(200, 200, 200, 200);");
        vBox.setEffect(new BoxBlur());
    }

    private void destructOverlay() {
        stackPane.getChildren().remove(overlayVBox);
        stackPane.setStyle(null);
        vBox.setDisable(false);
        vBox.setEffect(null);
    }
}