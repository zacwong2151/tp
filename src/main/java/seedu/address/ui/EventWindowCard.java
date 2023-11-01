package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.event.Event;

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
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label loc;
    @FXML
    private Label description;
    @FXML
    private VBox materials;

    @FXML
    private Label budget;

    @FXML
    private VBox roles;

    /**
     * Creates a {@code EventWindowCard} with the given {@code Event} and index to display.
     */
    public EventWindowCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        eventName.setText("Name of event: " + event.getEventName().eventName);
        startDate.setText("Start date: " + event.getStartDate().toString());
        endDate.setText("End date: " + event.getEndDate().toString());
        loc.setText("Location: " + event.getLocation().location);
        event.getRoles().stream()
                .sorted(Comparator.comparing(role -> role.roleName))
                .forEach(role -> roles.getChildren().add(new Label("\u2022 " + role.toUiString())));
        budget.setText("Budget: " + event.getBudget().budget);
        event.getMaterials().stream()
                .sorted(Comparator.comparing(material -> material.material))
                .forEach(material -> materials.getChildren().add(new Label("\u2022 " + material.toUiString())));
        description.setText("Description: " + event.getDescription().description);
    }
}
