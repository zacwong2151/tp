package seedu.address.ui.labels;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * A formatted {@code Label} that holds a specific string.
 */
public class EnclosedLabel extends UiPart<Region> {

    private static final String FXML = "EnclosedLabel.fxml";

    @FXML
    private Label enclosedLabel;

    /**
     * Creates an {@code EnclosedLabel} with the given {@code text} and CSS {@code color}.
     * @param text The text to place in the {@code EnclosedLabel} as a {@code String}.
     * @param color The colour to set the background of the {@code EnclosedLabel}, as a CSS colour
     *              (predefined/hex/RGB). Set as {@code #222222} by default.
     */
    public EnclosedLabel(String text, String color) {
        super(FXML);
        enclosedLabel.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 20;");
        enclosedLabel.setText(text);
    }

    /**
     * Creates an {@code EnclosedLabel} with the given {@code text} and default colour of {@code #222222}.
     * @param text The text to place in the {@code EnclosedLabel} as a {@code String}.
     */
    public EnclosedLabel(String text) {
        super(FXML);
        enclosedLabel.setStyle("-fx-background-color: #222222; -fx-background-radius: 20;");
        enclosedLabel.setText(text);
    }
}
