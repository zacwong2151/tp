package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EventStorage;
import seedu.address.model.ReadOnlyEventStorage;
import seedu.address.model.ReadOnlyVolunteerStorage;
import seedu.address.model.VolunteerStorage;
import seedu.address.model.event.Event;
import seedu.address.model.event.Material;
import seedu.address.model.event.Role;
import seedu.address.model.skill.Skill;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;

/**
 * Contains utility methods for populating {@code Event} and {@code Volunteer} with sample data.
 */
public class SampleDataUtil {
    // To be updated
    public static Event[] getSampleEvents() {
        //        return new Volunteer[] {
        //                new Volunteer(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
        //                        new Address("Blk 30 Geylang Street 29, #06-40"),
        //                        getTagSet("friends")),
        //                new Volunteer(new Name("Bernice Yu"), new Phone("99272758"),
        //                        new Email("berniceyu@example.com"),
        //                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
        //                        getTagSet("colleagues", "friends")),
        //                new Volunteer(new Name("Charlotte Oliveiro"), new Phone("93210283"),
        //                        new Email("charlotte@example.com"),
        //                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("neighbours")),
        //                new Volunteer(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
        //                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
        //                        getTagSet("family")),
        //                new Volunteer(new Name("Irfan Ibrahim"), new Phone("92492021"),
        //                        new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
        //                        getTagSet("classmates")),
        //                new Volunteer(new Name("Roy Balakrishnan"), new Phone("92624417"),
        //                        new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
        //                        getTagSet("colleagues"))
        //      };
        return new Event[]{};
    }

    public static ReadOnlyEventStorage getSampleEventStorage() {
        EventStorage sampleAb = new EventStorage();
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }
        return sampleAb;
    }

    /**
     * Returns a role set containing the list of strings given.
     */
    public static Set<Role> getRoleSet(String... strings) {
        return Arrays.stream(strings)
                .map(Role::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a material set containing the list of strings given.
     */
    public static Set<Material> getMaterialSet(String... strings) {
        return Arrays.stream(strings)
                .map(Material::new)
                .collect(Collectors.toSet());
    }
    public static Volunteer[] getSampleVolunteers() {
        return new Volunteer[] {
            new Volunteer(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getSkillSet("friends"), new HashSet<Event>()),
            new Volunteer(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getSkillSet("colleagues", "friends"), new HashSet<Event>()),
            new Volunteer(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getSkillSet("neighbours"), new HashSet<Event>()),
            new Volunteer(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getSkillSet("family"), new HashSet<Event>()),
            new Volunteer(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getSkillSet("classmates"), new HashSet<Event>()),
            new Volunteer(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                getSkillSet("colleagues"), new HashSet<Event>())
        };
    }

    public static ReadOnlyVolunteerStorage getSampleVolunteerStorage() {
        VolunteerStorage sampleAb = new VolunteerStorage();
        for (Volunteer sampleVolunteer : getSampleVolunteers()) {
            sampleAb.addVolunteer(sampleVolunteer);
        }
        return sampleAb;
    }

    /**
     * Returns a skill set containing the list of strings given.
     */
    public static Set<Skill> getSkillSet(String... strings) {
        return Arrays.stream(strings)
                .map(Skill::new)
                .collect(Collectors.toSet());
    }

}
