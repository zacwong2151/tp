package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;
import seedu.address.ui.labels.MaterialLabel;
import seedu.address.ui.labels.RoleLabel;

import java.util.Comparator;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label eventName;
    @FXML
    private Label id;
    @FXML
    private Label dateAndTime;
    @FXML
    private Label loc;
    @FXML
    private Label description;
    @FXML
    private FlowPane roles;
    @FXML
    private FlowPane materials;

    /**
     * Creates a {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        eventName.setText(event.getEventName().eventName);
        dateAndTime.setText(event.getStartDate().toString() + " to " + event.getEndDate().toString());
        loc.setText("Location: " + event.getLocation().location);
        description.setText("Description: " + event.getDescription().description);
        event.getRoles().stream()
                .sorted(Comparator.comparing(role -> role.roleName))
                .forEach(role -> roles.getChildren().add(new RoleLabel(role).getRoot()));
        event.getMaterials().stream()
                .sorted(Comparator.comparing(material -> material.material))
                .forEach(material -> materials.getChildren().add(new MaterialLabel(material).getRoot()));
    }
}
