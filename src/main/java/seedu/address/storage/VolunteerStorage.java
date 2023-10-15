package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyVolunteerStorage;

/**
 * Represents a storage for {@link seedu.address.model.VolunteerStorage}.
 */
public interface VolunteerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getVolunteerStorageFilePath();

    /**
     * Returns VolunteerStorage data as a {@link ReadOnlyVolunteerStorage}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyVolunteerStorage> readVolunteerStorage() throws DataLoadingException;

    /**
     * @see #getVolunteerStorageFilePath()
     */
    Optional<ReadOnlyVolunteerStorage> readVolunteerStorage(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyVolunteerStorage} to the storage.
     * @param volunteerStorage cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveVolunteerStorage(ReadOnlyVolunteerStorage volunteerStorage) throws IOException;

    /**
     * @see #saveVolunteerStorage(ReadOnlyVolunteerStorage)
     */
    void saveVolunteerStorage(ReadOnlyVolunteerStorage volunteerStorage, Path filePath) throws IOException;

}
