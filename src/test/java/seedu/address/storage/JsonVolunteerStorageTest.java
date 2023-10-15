package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVolunteers.ALICE;
import static seedu.address.testutil.TypicalVolunteers.HOON;
import static seedu.address.testutil.TypicalVolunteers.IDA;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyVolunteerStorage;
import seedu.address.model.VolunteerStorage;

public class JsonVolunteerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyVolunteerStorage> readAddressBook(String filePath) throws Exception {
        return new JsonVolunteerStorage(Paths.get(filePath)).readVolunteerStorage(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        VolunteerStorage original = getTypicalVolunteerStorage();
        JsonVolunteerStorage jsonAddressBookStorage = new JsonVolunteerStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveVolunteerStorage(original, filePath);
        ReadOnlyVolunteerStorage readBack = jsonAddressBookStorage.readVolunteerStorage(filePath).get();
        assertEquals(original, new VolunteerStorage(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addVolunteer(HOON);
        original.removeVolunteer(ALICE);
        jsonAddressBookStorage.saveVolunteerStorage(original, filePath);
        readBack = jsonAddressBookStorage.readVolunteerStorage(filePath).get();
        assertEquals(original, new VolunteerStorage(readBack));

        // Save and read without specifying file path
        original.addVolunteer(IDA);
        jsonAddressBookStorage.saveVolunteerStorage(original); // file path not specified
        readBack = jsonAddressBookStorage.readVolunteerStorage().get(); // file path not specified
        assertEquals(original, new VolunteerStorage(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyVolunteerStorage addressBook, String filePath) {
        try {
            new JsonVolunteerStorage(Paths.get(filePath))
                    .saveVolunteerStorage(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new VolunteerStorage(), null));
    }
}
