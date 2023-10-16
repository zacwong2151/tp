package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyEventStorage;

/**
 * Represents a storage for {@link seedu.address.model.VolunteerStorage}.
 */
public interface EventStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEventStorageFilePath();

    /**
     * Returns Event Storage data as a {@link ReadOnlyEventStorage}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyEventStorage> readEventStorage() throws DataLoadingException;

    /**
     * @see #getEventStorageFilePath()
     */
    Optional<ReadOnlyEventStorage> readEventStorage(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyEventStorage} to the storage.
     * @param eventStorage cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventStorage(ReadOnlyEventStorage eventStorage) throws IOException;

    /**
     * @see #saveEventStorage(ReadOnlyEventStorage)
     */
    void saveEventStorage(ReadOnlyEventStorage eventStorage, Path filePath) throws IOException;

}
