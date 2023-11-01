package seedu.address.ui.labels;

import static seedu.address.ui.labels.LabelColors.LABEL_GREEN;
import static seedu.address.ui.labels.LabelColors.LABEL_RED;

import seedu.address.model.event.Role;

/**
 * An {@code EnclosedLabel} that holds a specific {@code Role}.
 */
public class RoleLabel extends EnclosedLabel {

    /**
     * Creates a {@code RoleLabel} with the given {@code Role}.
     */
    public RoleLabel(Role role) {
        super(role.toUiString(), role.hasEnoughManpower() ? LABEL_GREEN : LABEL_RED);
    }
}
