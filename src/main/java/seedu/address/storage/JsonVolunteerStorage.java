package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyVolunteerStorage;
import seedu.address.storage.volunteer.JsonSerializableVolunteerStorage;

/**
 * A class to access VolunteerStorage data stored as a json file on the hard disk.
 */
public class JsonVolunteerStorage implements VolunteerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonVolunteerStorage.class);

    private Path filePath;

    public JsonVolunteerStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getVolunteerStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyVolunteerStorage> readVolunteerStorage() throws DataLoadingException {
        return readVolunteerStorage(filePath);
    }

    /**
     * Similar to {@link #readVolunteerStorage()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyVolunteerStorage> readVolunteerStorage(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableVolunteerStorage> jsonVolunteerStorage = JsonUtil.readJsonFile(
                filePath, JsonSerializableVolunteerStorage.class);
        if (!jsonVolunteerStorage.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonVolunteerStorage.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveVolunteerStorage(ReadOnlyVolunteerStorage volunteerStorage) throws IOException {
        saveVolunteerStorage(volunteerStorage, filePath);
    }

    /**
     * Similar to {@link #saveVolunteerStorage(ReadOnlyVolunteerStorage)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveVolunteerStorage(ReadOnlyVolunteerStorage volunteerStorage, Path filePath) throws IOException {
        requireNonNull(volunteerStorage);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableVolunteerStorage(volunteerStorage), filePath);
    }

}
