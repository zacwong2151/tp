package seedu.address.testutil;

import seedu.address.model.VolunteerStorage;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private VolunteerStorage addressBook;

    public AddressBookBuilder() {
        addressBook = new VolunteerStorage();
    }

    public AddressBookBuilder(VolunteerStorage addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Volunteer volunteer) {
        addressBook.addVolunteer(volunteer);
        return this;
    }

    public VolunteerStorage build() {
        return addressBook;
    }
}
