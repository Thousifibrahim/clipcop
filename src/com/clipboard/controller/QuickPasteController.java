package com.clipboard.controller;

import com.clipboard.model.ClipboardItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class QuickPasteController {
    @FXML private ListView<ClipboardItem> popupListView;
    @FXML private Button closeButton;
    @FXML private Button toggleDarkModeButton;
    private ObservableList<ClipboardItem> items;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isDarkMode = false;

    public QuickPasteController(ObservableList<ClipboardItem> items) {
        this.items = items;
    }

    @FXML
    public void initialize() {
        popupListView.setItems(items);
        popupListView.setCellFactory(_ -> new ListCell<ClipboardItem>() {
            @Override
            protected void updateItem(ClipboardItem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getContent());
            }
        });
        popupListView.getSelectionModel().selectFirst();
        popupListView.getScene().getRoot().setUserData(this); // Store controller for dark mode sync
        updateDarkModeButtonText();
        // Initialize style class
        popupListView.getScene().getRoot().getStyleClass().add("light");
    }

    @FXML
    private void pasteSelectedItem(MouseEvent event) {
        ClipboardItem selected = popupListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            java.awt.datatransfer.StringSelection selection = new java.awt.datatransfer.StringSelection(selected.getContent());
            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
        }
        closePopup();
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            pasteSelectedItem(null);
        } else if (event.getCode() == KeyCode.ESCAPE) {
            closePopup();
        }
    }

    @FXML
    private void closePopup() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) popupListView.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        updateDarkModeButtonText();
        Stage stage = (Stage) toggleDarkModeButton.getScene().getWindow();
        stage.getScene().getRoot().getStyleClass().remove(isDarkMode ? "light" : "dark");
        stage.getScene().getRoot().getStyleClass().add(isDarkMode ? "dark" : "light");
        // Notify main window
        Stage mainStage = (Stage) javafx.stage.Stage.getWindows().stream()
                .filter(window -> window instanceof Stage && ((Stage) window).getTitle().equals("Clipboard Manager"))
                .findFirst().orElse(null);
        if (mainStage != null) {
            ClipboardController controller = (ClipboardController) mainStage.getScene().getRoot().getUserData();
            if (controller != null) {
                controller.setDarkMode(isDarkMode);
            }
        }
    }

    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
        updateDarkModeButtonText();
        Stage stage = (Stage) popupListView.getScene().getWindow();
        stage.getScene().getRoot().getStyleClass().remove(isDarkMode ? "light" : "dark");
        stage.getScene().getRoot().getStyleClass().add(isDarkMode ? "dark" : "light");
    }

    private void updateDarkModeButtonText() {
        toggleDarkModeButton.setText(isDarkMode ? "☀" : "🌙");
    }
}