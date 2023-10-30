package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.VolunteerFindCommandTest.preparePredicate;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VOLUNTEERS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalVolunteers.ALICE;
import static seedu.address.testutil.TypicalVolunteers.BENSON;
import static seedu.address.testutil.TypicalVolunteers.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.SkillNameContainsKeywordsPredicate;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.VolunteerStorageBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new VolunteerStorage(), new VolunteerStorage(modelManager.getVolunteerStorage()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setVolunteerStorageFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setVolunteerStorageFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setVolunteerStorageFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setVolunteerStorageFilePath(null));
    }

    @Test
    public void setVolunteerStorageFilePath_validPath_setsVolunteerStorageFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setVolunteerStorageFilePath(path);
        assertEquals(path, modelManager.getVolunteerStorageFilePath());
    }

    @Test
    public void undoBothStorages_nil_displaysCorrectVolunteerList() {
        VolunteerStorage volunteerStorage = new VolunteerStorageBuilder().build();
        List<Volunteer> list = new ArrayList<>();
        list.add(ALICE);
        list.add(BOB);
        volunteerStorage.setVolunteers(list);
        assertEquals(volunteerStorage.getVolunteerList(), list);
    }

    @Test
    public void commitToBothVersionedStorages_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager
                .commitToBothVersionedStorages(null, null));
    }

    @Test
    public void hasVolunteer_nullVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasVolunteer(null));
    }

    @Test
    public void hasVolunteer_volunteerNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasVolunteer(ALICE));
    }

    @Test
    public void hasVolunteer_volunteerInAddressBook_returnsTrue() {
        modelManager.addVolunteer(ALICE);
        assertTrue(modelManager.hasVolunteer(ALICE));
    }

    @Test
    public void getFilteredVolunteerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredVolunteerList().remove(0));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredEventList().remove(0));
    }

    @Test
    public void getEventToShowList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getEventToShowList().remove(0));
    }

    @Test
    public void equals() throws ParseException {
        // Need to change
        EventStorage eventStorage = TypicalEvents.getTypicalEventStorage();
        EventStorage differentEventStorage = new EventStorage();

        VolunteerStorage volunteerStorage = new VolunteerStorageBuilder().withVolunteer(ALICE).withVolunteer(BENSON)
                .build();
        VolunteerStorage differentVolunteerStorage = new VolunteerStorage();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(eventStorage, volunteerStorage, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(eventStorage, volunteerStorage, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentEventStorage, differentVolunteerStorage, userPrefs)));

        // different filteredList -> returns false
        String name = ALICE.getName().fullName.split("\\s+")[0];
        SkillNameContainsKeywordsPredicate predicate = preparePredicate(" n/" + name);
        modelManager.updateFilteredVolunteerList(predicate);
        assertFalse(modelManager.equals(new ModelManager(eventStorage, volunteerStorage, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setVolunteerStorageFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(eventStorage, volunteerStorage, differentUserPrefs)));

        // different filteredEventList -> returns false
        CommandTestUtil.showEventAtIndex(modelManager, INDEX_FIRST);
        assertFalse(modelManager.equals(new ModelManager(eventStorage, volunteerStorage, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        // different eventToShowList -> returns false
        Predicate<Event> predicateShowEvent = e -> e.equals(FIRST);
        modelManager.updateEventToShowList(predicateShowEvent);
        assertFalse(modelManager.equals(new ModelManager(eventStorage, volunteerStorage, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateEventToShowList(PREDICATE_SHOW_ALL_EVENTS);
    }
}
