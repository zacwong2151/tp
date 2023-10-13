package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.w3c.dom.events.Event;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyEventStorage;
import seedu.address.model.ReadOnlyVolunteerStorage;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends EventStorage, VolunteerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // ================ EventStorage methods ==============================
    @Override
    Path getEventStorageFilePath();

    @Override
    Optional<ReadOnlyEventStorage> readEventStorage() throws DataLoadingException;

    @Override
    void saveEventStorage(ReadOnlyEventStorage newData) throws IOException;

    // ================ VolunteerStorage methods ==============================
    @Override
    Path getVolunteerStorageFilePath();

    @Override
    Optional<ReadOnlyVolunteerStorage> readVolunteerStorage() throws DataLoadingException;

    @Override
    void saveVolunteerStorage(ReadOnlyVolunteerStorage newData) throws IOException;

}
