package seedu.address.testutil;

import seedu.address.model.VolunteerStorage;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class to help with building VolunteerStorage objects.
 * Example usage: <br>
 *     {@code VolunteerStorage ab = new VolunteerStorageBuilder().withVolunteer("John", "Doe").build();}
 */
public class VolunteerStorageBuilder {

    private VolunteerStorage volunteerStorage;

    public VolunteerStorageBuilder() {
        volunteerStorage = new VolunteerStorage();
    }

    public VolunteerStorageBuilder(VolunteerStorage volunteerStorage) {
        this.volunteerStorage = volunteerStorage;
    }

    /**
     * Adds a new {@code Volunteer} to the {@code VolunteerStorage} that we are building.
     */
    public VolunteerStorageBuilder withVolunteer(Volunteer volunteer) {
        volunteerStorage.addVolunteer(volunteer);
        return this;
    }

    public VolunteerStorage build() {
        return volunteerStorage;
    }
}
