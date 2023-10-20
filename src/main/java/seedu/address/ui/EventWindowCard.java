package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

import java.util.Comparator;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventWindowCard extends UiPart<Region> {

    private static final String FXML = "EventWindowCard.fxml";

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
    private FlowPane materials;

    @FXML
    private Label budget;

    @FXML
    private FlowPane roles;

    /**
     * Creates a {@code EventCode} with the given {@code Event} and index to display.
     */
    public EventWindowCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        eventName.setText(event.getEventName().name);
        dateAndTime.setText(event.getDateAndTime().toString());
        loc.setText(event.getLocation().location);
        budget.setText(event.getBudget().budget);
        event.getMaterials().stream()
                .sorted(Comparator.comparing(material -> material.material))
                .forEach(material -> materials.getChildren().add(new Label(material.material)));
        event.getRoles().stream()
                .sorted(Comparator.comparing(role -> role.roleName))
                .forEach(role -> roles.getChildren().add(new Label(role.roleName)));
        description.setText(event.getDescription().description);
    }
}
