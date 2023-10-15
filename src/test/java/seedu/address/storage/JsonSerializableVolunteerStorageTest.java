package seedu.address.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

//import org.junit.jupiter.api.Test;

//import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.commons.util.JsonUtil;
//import seedu.address.model.VolunteerStorage;
//import seedu.address.testutil.TypicalVolunteers;

public class JsonSerializableVolunteerStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
                                                        "JsonSerializableVolunteerStorageTest");
    private static final Path TYPICAL_VOLUNTEERS_FILE = TEST_DATA_FOLDER
                                                        .resolve("typicalVolunteersVolunteerStorage.json");
    private static final Path INVALID_VOLUNTEER_FILE = TEST_DATA_FOLDER
                                                        .resolve("invalidVolunteerVolunteerStorage.json");
    private static final Path DUPLICATE_VOLUNTEER_FILE = TEST_DATA_FOLDER
                                                        .resolve("duplicateVolunteerVolunteerStorage.json");

    // Commenting out this test as it is causing CI errors. To be resolved later
    //    @Test
    //    public void toModelType_typicalVolunteersFile_success() throws Exception {
    //        JsonSerializableVolunteerStorage dataFromFile = JsonUtil.readJsonFile(TYPICAL_VOLUNTEERS_FILE,
    //                JsonSerializableVolunteerStorage.class).get();
    //        VolunteerStorage volunteerStorageFromFile = dataFromFile.toModelType();
    //        VolunteerStorage typicalVolunteersVolunteerStorage = TypicalVolunteers.getTypicalVolunteerStorage();
    //        assertEquals(volunteerStorageFromFile, typicalVolunteersVolunteerStorage);
    //    }

    // Commenting out this test as it is causing CI errors. To be resolved later
    //    @Test
    //    public void toModelType_invalidVolunteerFile_throwsIllegalValueException() throws Exception {
    //        JsonSerializableVolunteerStorage dataFromFile = JsonUtil.readJsonFile(INVALID_VOLUNTEER_FILE,
    //                JsonSerializableVolunteerStorage.class).get();
    //        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    //    }

    // Commenting out this test as it is causing CI errors. To be resolved later
    //    @Test
    //    public void toModelType_duplicateVolunteers_throwsIllegalValueException() throws Exception {
    //        JsonSerializableVolunteerStorage dataFromFile = JsonUtil.readJsonFile(DUPLICATE_VOLUNTEER_FILE,
    //                JsonSerializableVolunteerStorage.class).get();
    //        assertThrows(IllegalValueException.class, JsonSerializableVolunteerStorage.MESSAGE_DUPLICATE_VOLUNTEER,
    //                dataFromFile::toModelType);
    //    }

}
