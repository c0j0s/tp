package seedu.address.commons.util;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import javafx.scene.control.TextArea;

/**
 * adapted from https://stackoverflow.com/questions/10785560
 */
public class LoggerHandler extends StreamHandler {
    private TextArea textArea = null;

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        flush();

        if (textArea != null) {
            textArea.appendText(getFormatter().format(record));
        }
    }
}
