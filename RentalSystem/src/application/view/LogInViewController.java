package application.view;

import application.viewmodel.LogInViewModel;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

import java.util.concurrent.*;

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

    // TODO[viktor]: Find out why the first log in attempt after server reboot is blocking UI thread,
    // TODO[viktor]: unless server wakes up while we're in the overlay (loop that constantly tries to reconnect)
    @FXML
    public void onLogIn() throws ExecutionException, InterruptedException {
        if (areInputFieldsEmpty()) {
            error.setText("Please provide all credentials!");
            return;
        }

        new Thread(() -> Platform.runLater(() -> constructOverlayWithMessage(""))).start();

        if (!viewModel.isClientConnected()) {
            viewModel.tryToReconnectClient();
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> viewModel.logIn());
        String result = future.get();

        pause.setOnFinished(event -> {
            if (result.equals("ServerConnectionFailure")) {
                destructOverlay();
                if (!viewModel.tryToReconnectClient()) {
                    constructOverlayWithMessage("Failed to connect to the server. Restoring the connection...");
                    new Thread(() -> {
                        viewModel.tryToReconnectClientLooped();
                        Platform.runLater(this::reset);
                    }).start();
                } else {
                    error.setText("There was a problem connecting to you account.\nPlease try again");
                }
            } else {
                switch (result) {
                    case "Manager" -> {
                        Platform.runLater(() -> viewHandler.openView(ViewHandler.MANAGER_EQUIPMENT_LIST_VIEW));
                    }
                    case "Rentee" -> {
                        Platform.runLater(() -> viewHandler.openView(ViewHandler.EQUIPMENT_LIST_VIEW));
                    }
                    default -> {
                        destructOverlay();
                        error.setText("Wrong credentials");
                    }
                }
            }
        });
        pause.play();
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