package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;

/**
 * Pop-up window showing all information of a particular event.
 */
public class EventShowWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(EventShowWindow.class);
    private static final String FXML = "EventShowWindow.fxml";
    @FXML
    private ListView<Event> eventShowView;

    /**
     * Creates an EventShowWindow object.
     * @param root Stage to use as the root of the EventShowWindow.
     * @param eventToShow List containing the event to show.
     */
    public EventShowWindow(Stage root, ObservableList<Event> eventToShow) {
        super(FXML, root);
        eventShowView.setSelectionModel(new EventWindowSelectionModel<>());
        eventShowView.setItems(eventToShow);
        eventShowView.setCellFactory(listView -> new EventWindowViewCell());
    }

    public EventShowWindow(ObservableList<Event> eventToShow) {
        this(new Stage(), eventToShow);
    }

    /**
     * Shows the event window.
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
        logger.fine("Showing the event window.");
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
     * Custom {@code ListCell} that displays the graphics of an {@code Event} using a {@code EventWindowCard}.
     */
    class EventWindowViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventWindowCard(event, getIndex() + 1).getRoot());
            }
        }
    }
}
