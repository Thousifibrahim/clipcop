package com.clipboard.util;

import com.clipboard.model.ClipboardItem;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
// import java.awt.datatransfer.StringSelection;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClipboardManager {
    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private String lastContent = "";
    private final Consumer<ClipboardItem> callback;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ClipboardManager(Consumer<ClipboardItem> callback) {
        this.callback = callback;
    }

    public void startMonitoring() {
        scheduler.scheduleAtFixedRate(this::checkClipboard, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void checkClipboard() {
        try {
            if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                String content = (String) clipboard.getData(DataFlavor.stringFlavor);
                if (!content.equals(lastContent)) {
                    lastContent = content;
                    callback.accept(new ClipboardItem(content, LocalDateTime.now()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMonitoring() {
        scheduler.shutdown();
    }
}