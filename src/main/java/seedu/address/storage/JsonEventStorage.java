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
import seedu.address.model.ReadOnlyEventStorage;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonEventStorage implements EventStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonVolunteerStorage.class);

    private Path filePath;

    public JsonEventStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getEventStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventStorage> readEventStorage() throws DataLoadingException {
        return readEventStorage(filePath);
    }

    /**
     * Similar to {@link #readEventStorage()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyEventStorage> readEventStorage(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableEventStorage> jsonEventStorage = JsonUtil.readJsonFile(
                filePath, JsonSerializableEventStorage.class);
        if (!jsonEventStorage.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEventStorage.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveEventStorage(ReadOnlyEventStorage eventStorage) throws IOException {
        saveEventStorage(eventStorage, filePath);
    }

    /**
     * Similar to {@link #saveEventStorage(ReadOnlyEventStorage)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEventStorage(ReadOnlyEventStorage eventStorage, Path filePath) throws IOException {
        requireNonNull(eventStorage);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEventStorage(eventStorage), filePath);
    }

}
