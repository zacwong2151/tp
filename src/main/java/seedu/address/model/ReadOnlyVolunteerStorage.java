package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.volunteer.Volunteer;

/**
 * Unmodifiable view of a volunteer storage
 */
public interface ReadOnlyVolunteerStorage {

    /**
     * Returns an unmodifiable view of the volunteers list.
     * This list will not contain any duplicate volunteers.
     */
    ObservableList<Volunteer> getVolunteerList();

}
