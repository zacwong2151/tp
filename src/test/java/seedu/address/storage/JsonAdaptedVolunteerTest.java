package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedVolunteer.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVolunteers.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;

public class JsonAdaptedVolunteerTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final List<JsonAdaptedSkill> VALID_TAGS = BENSON.getSkills().stream()
            .map(JsonAdaptedSkill::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validVolunteerDetails_returnsVolunteer() throws Exception {
        JsonAdaptedVolunteer volunteer = new JsonAdaptedVolunteer(BENSON);
        assertEquals(BENSON, volunteer.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer = new JsonAdaptedVolunteer(null, VALID_PHONE, VALID_EMAIL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer = new JsonAdaptedVolunteer(VALID_NAME, null, VALID_EMAIL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer = new JsonAdaptedVolunteer(VALID_NAME, VALID_PHONE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedSkill> invalidSkills = new ArrayList<>(VALID_TAGS);
        invalidSkills.add(new JsonAdaptedSkill(INVALID_TAG));
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(VALID_NAME, VALID_PHONE, VALID_EMAIL, invalidSkills);
        assertThrows(IllegalValueException.class, volunteer::toModelType);
    }
}
