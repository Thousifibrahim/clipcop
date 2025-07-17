package com.clipboard.util;

import com.clipboard.model.ClipboardItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class JsonStorage {
    private static final String FILE_PATH = "data/clipboard_history.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule()); // Support LocalDateTime
    }

    public static void saveClipboardHistory(ObservableList<ClipboardItem> items) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            mapper.writeValue(file, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<ClipboardItem> loadClipboardHistory() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists() || file.length() == 0) {
                return FXCollections.observableArrayList();
            }
            ClipboardItem[] items = mapper.readValue(file, ClipboardItem[].class);
            return FXCollections.observableArrayList(Arrays.asList(items));
        } catch (IOException e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }
}