package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyEventStorage;
import seedu.address.model.ReadOnlyVolunteerStorage;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of EventStorage and VolunteerStorage data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private VolunteerStorage volunteerStorage;
    private EventStorage eventStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code VolunteerStorage}, {@code EventStorage}
     * and {@code UserPrefStorage}.
     */
    public StorageManager(EventStorage eventStorage, VolunteerStorage volunteerStorage,
                          UserPrefsStorage userPrefsStorage) {
        this.eventStorage = eventStorage;
        this.volunteerStorage = volunteerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ EventStorage methods ==============================
    @Override
    public Path getEventStorageFilePath() {
        return eventStorage.getEventStorageFilePath();
    }

    @Override
    public Optional<ReadOnlyEventStorage> readEventStorage() throws DataLoadingException {
        return readEventStorage(eventStorage.getEventStorageFilePath());
    }

    @Override
    public Optional<ReadOnlyEventStorage> readEventStorage(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventStorage.readEventStorage(filePath);
    }

    @Override
    public void saveEventStorage(ReadOnlyEventStorage newData) throws IOException {
        saveEventStorage(newData, eventStorage.getEventStorageFilePath());
    }

    @Override
    public void saveEventStorage(ReadOnlyEventStorage newData, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventStorage.saveEventStorage(newData, filePath);
    }

    // ================ VolunteerStorage methods ==============================

    @Override
    public Path getVolunteerStorageFilePath() {
        return volunteerStorage.getVolunteerStorageFilePath();
    }

    @Override
    public Optional<ReadOnlyVolunteerStorage> readVolunteerStorage() throws DataLoadingException {
        return readVolunteerStorage(volunteerStorage.getVolunteerStorageFilePath());
    }

    @Override
    public Optional<ReadOnlyVolunteerStorage> readVolunteerStorage(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return volunteerStorage.readVolunteerStorage(filePath);
    }

    @Override
    public void saveVolunteerStorage(ReadOnlyVolunteerStorage newData) throws IOException {
        saveVolunteerStorage(newData, volunteerStorage.getVolunteerStorageFilePath());
    }

    @Override
    public void saveVolunteerStorage(ReadOnlyVolunteerStorage newData, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        volunteerStorage.saveVolunteerStorage(newData, filePath);
    }

}
