package com.clipboard.controller;

import com.clipboard.model.ClipboardItem;
import com.clipboard.util.ClipboardManager;
import com.clipboard.util.JsonStorage;
import javafx.application.Platform; // Import Platform
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClipboardController {
    @FXML private ListView<ClipboardItem> clipboardListView;
    @FXML private TextField searchField;
    @FXML private Button quickPasteButton;
    @FXML private Button minimizeButton;
    @FXML private Button closeButton;
    @FXML private Button toggleDarkModeButton;
    private ObservableList<ClipboardItem> clipboardItems;
    private ObservableList<ClipboardItem> filteredItems;
    private ObservableList<ClipboardItem> popupItems;
    private ClipboardManager clipboardManager;
    private Stage quickPasteStage;
    private boolean isDarkMode = false;

    @FXML
    public void initialize() {
        clipboardItems = FXCollections.observableArrayList();
        filteredItems = FXCollections.observableArrayList();
        popupItems = FXCollections.observableArrayList();
        filteredItems.addAll(clipboardItems);
        clipboardListView.setItems(filteredItems);

        clipboardListView.setCellFactory(_ -> new ListCell<ClipboardItem>() {
            @Override
            protected void updateItem(ClipboardItem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getContent());
            }
        });

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filteredItems.clear();
                if (newValue == null || newValue.isEmpty()) {
                    filteredItems.addAll(clipboardItems);
                } else {
                    String lowerCaseFilter = newValue.toLowerCase();
                    for (ClipboardItem item : clipboardItems) {
                        if (item.getContent().toLowerCase().contains(lowerCaseFilter)) {
                            filteredItems.add(item);
                        }
                    }
                }
            }
        });

        clipboardManager = new ClipboardManager(this::addClipboardItem);
        clipboardManager.startMonitoring();
        loadHistory();
        updateDarkModeButtonText();

        // --- FIX START ---
        // Defer the code that requires the scene to be available
        Platform.runLater(() -> {
            // Initialize style class only if the scene is available
            if (clipboardListView.getScene() != null) {
                clipboardListView.getScene().getRoot().getStyleClass().add("light");
            } else {
                System.err.println("Warning: Scene not available for clipboardListView during Platform.runLater initialization.");
            }
        });
        // --- FIX END ---
    }

    private void addClipboardItem(ClipboardItem item) {
        if (clipboardItems.size() >= 100) {
            clipboardItems.remove(clipboardItems.size() - 1);
        }
        clipboardItems.add(0, item);
        filteredItems.clear();
        filteredItems.addAll(clipboardItems);
        updatePopupItems();
        JsonStorage.saveClipboardHistory(clipboardItems);
    }

    private void loadHistory() {
        clipboardItems.addAll(JsonStorage.loadClipboardHistory());
        filteredItems.addAll(clipboardItems);
        updatePopupItems();
    }

    private void updatePopupItems() {
        popupItems.clear();
        int maxItems = Math.min(clipboardItems.size(), 10);
        for (int i = 0; i < maxItems; i++) {
            popupItems.add(clipboardItems.get(i));
        }
    }

    @FXML
    private void showQuickPaste() {
        try {
            if (quickPasteStage == null) {
                quickPasteStage = new Stage(StageStyle.TRANSPARENT);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/clipboard/QuickPastePopup.fxml"));
                QuickPasteController controller = new QuickPasteController(popupItems);
                controller.setDarkMode(isDarkMode); // Sync dark mode
                loader.setController(controller);
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(getClass().getResource("/com/clipboard/styles.css").toExternalForm());
                quickPasteStage.setScene(scene);
                quickPasteStage.getScene().getRoot().setUserData(controller);
                // Apply initial dark mode to popup
                if (isDarkMode) {
                    quickPasteStage.getScene().getRoot().getStyleClass().add("dark");
                }
            } else {
                QuickPasteController controller = (QuickPasteController) quickPasteStage.getScene().getRoot().getUserData();
                controller.setDarkMode(isDarkMode); // Update dark mode
            }
            quickPasteStage.show();
            quickPasteStage.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void minimizeWindow() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        updateDarkModeButtonText();
        Stage stage = (Stage) toggleDarkModeButton.getScene().getWindow();
        stage.getScene().getRoot().getStyleClass().remove(isDarkMode ? "light" : "dark");
        stage.getScene().getRoot().getStyleClass().add(isDarkMode ? "dark" : "light");
        if (quickPasteStage != null && quickPasteStage.isShowing()) {
            QuickPasteController controller = (QuickPasteController) quickPasteStage.getScene().getRoot().getUserData();
            controller.setDarkMode(isDarkMode);
        }
    }

    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
        updateDarkModeButtonText();
        // Assuming this method is called after the scene is set up.
        // It's safer to ensure toggleDarkModeButton.getScene() is not null.
        if (toggleDarkModeButton.getScene() != null) {
            Stage stage = (Stage) toggleDarkModeButton.getScene().getWindow();
            stage.getScene().getRoot().getStyleClass().remove(isDarkMode ? "light" : "dark");
            stage.getScene().getRoot().getStyleClass().add(isDarkMode ? "dark" : "light");
        } else {
            System.err.println("Warning: Scene not available for toggleDarkModeButton in setDarkMode.");
        }
    }

    private void updateDarkModeButtonText() {
        toggleDarkModeButton.setText(isDarkMode ? "☀" : "🌙");
    }
}