package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path volunteerStorageFilePath = Paths.get("data" , "volunteerStorage.json");
    private Path eventStorageFilePath = Paths.get("data", "eventStorage.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setVolunteerStorageFilePath(newUserPrefs.getVolunteerStorageFilePath());
        setEventStorageFilePath(newUserPrefs.getEventStorageFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getVolunteerStorageFilePath() {
        return volunteerStorageFilePath;
    }
    public Path getEventStorageFilePath() {
        return eventStorageFilePath;
    }

    public void setVolunteerStorageFilePath(Path volunteerStorageFilePath) {
        requireNonNull(volunteerStorageFilePath);
        this.volunteerStorageFilePath = volunteerStorageFilePath;
    }
    public void setEventStorageFilePath(Path eventStorageFilePath) {
        requireNonNull(eventStorageFilePath);
        this.eventStorageFilePath = eventStorageFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserPrefs)) {
            return false;
        }

        UserPrefs otherUserPrefs = (UserPrefs) other;
        return guiSettings.equals(otherUserPrefs.guiSettings)
                && volunteerStorageFilePath.equals(otherUserPrefs.volunteerStorageFilePath)
                && eventStorageFilePath.equals(otherUserPrefs.eventStorageFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, volunteerStorageFilePath, eventStorageFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nVolunteer data file location : " + volunteerStorageFilePath);
        sb.append("\nEvent data file location : " + eventStorageFilePath);
        return sb.toString();
    }

}
