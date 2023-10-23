package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.volunteer.Volunteer;

/**
 * Pop-up window showing all volunteers of a particular event.
 */
public class VolunteerShowWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(VolunteerShowWindow.class);
    private static final String FXML = "VolunteerShowWindow.fxml";
    @FXML
    private ListView<Volunteer> volunteerShowView;

    /**
     * Creates a VolunteerShowWindow object.
     * @param root Stage to use as the root of the VolunteerShowWindow.
     * @param volunteersToShow List containing the volunteers to show.
     */
    public VolunteerShowWindow(Stage root, ObservableList<Volunteer> volunteersToShow) {
        super(FXML, root);
        volunteerShowView.setSelectionModel(new VolunteerWindowSelectionModel<>());
        volunteerShowView.setItems(volunteersToShow);
        volunteerShowView.setCellFactory(listView -> new VolunteerWindowViewCell());
    }

    public VolunteerShowWindow(ObservableList<Volunteer> volunteersToShow) {
        this(new Stage(), volunteersToShow);
    }

    /**
     * Shows the volunteer window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing the volunteer window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the event window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the event window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the event window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Volunteer} using a {@code VolunteerCard}.
     */
    class VolunteerWindowViewCell extends ListCell<Volunteer> {
        @Override
        protected void updateItem(Volunteer volunteer, boolean empty) {
            super.updateItem(volunteer, empty);

            if (empty || volunteer == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new VolunteerCard(volunteer, getIndex() + 1).getRoot());
            }
        }
    }
}
