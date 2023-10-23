package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.EventName;
import seedu.address.model.skill.Skill;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class to help with building Volunteer objects.
 */
public class VolunteerBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";

    private Name name;
    private Phone phone;
    private Email email;
    private Set<Skill> skills;
    private Set<EventName> assignedEvents = new HashSet<>();

    /**
     * Creates a {@code VolunteerBuilder} with the default details.
     */
    public VolunteerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        skills = new HashSet<>();
    }

    /**
     * Initializes the VolunteerBuilder with the data of {@code volunteerToCopy}.
     */
    public VolunteerBuilder(Volunteer volunteerToCopy) {
        name = volunteerToCopy.getName();
        phone = volunteerToCopy.getPhone();
        email = volunteerToCopy.getEmail();
        skills = new HashSet<>(volunteerToCopy.getSkills());
    }

    /**
     * Sets the {@code Name} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<Skill>} and set it to the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withSkills(String ... skills) {
        this.skills = SampleDataUtil.getSkillSet(skills);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Volunteer build() {
        return new Volunteer(name, phone, email, skills, assignedEvents);
    }

}
