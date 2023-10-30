package seedu.address.ui.labels;

import static seedu.address.ui.labels.LabelColors.LABEL_GREEN;
import static seedu.address.ui.labels.LabelColors.LABEL_RED;

import seedu.address.model.event.Material;

/**
 * An {@code EnclosedLabel} that holds a specific {@code Skill}.
 */
public class MaterialLabel extends EnclosedLabel {

    /**
     * Creates a {@code MaterialLabel} with the given {@code Material}.
     */
    public MaterialLabel(Material material) {
        super(material.toUiString(), material.hasEnoughItems() ? LABEL_GREEN : LABEL_RED);
    }
}
