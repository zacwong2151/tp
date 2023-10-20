package seedu.address.storage.volunteer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.skill.Skill;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.storage.event.JsonAdaptedEvent;

/**
 * Jackson-friendly version of {@link Volunteer}.
 */
public class JsonAdaptedVolunteer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Volunteer's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final List<JsonAdaptedSkill> skills = new ArrayList<>();
    private final List<JsonAdaptedEvent> assignedEvents = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedVolunteer} with the given volunteer details.
     */
    @JsonCreator
    public JsonAdaptedVolunteer(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                @JsonProperty("email") String email,
                                @JsonProperty("skills") List<JsonAdaptedSkill> skills,
                                @JsonProperty("events") List<JsonAdaptedEvent> assignedEvents) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        if (skills != null) {
            this.skills.addAll(skills);
        }
        if (assignedEvents != null) {
            this.assignedEvents.addAll(assignedEvents);
        }
    }

    /**
     * Converts a given {@code Volunteer} into this class for Jackson use.
     */
    public JsonAdaptedVolunteer(Volunteer source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        skills.addAll(source.getSkills().stream()
                .map(JsonAdaptedSkill::new)
                .collect(Collectors.toList()));
        assignedEvents.addAll(source.getAssignedEvents().stream()
                .map(JsonAdaptedEvent::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Volunteer object into the model's {@code Volunteer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted volunteer.
     */
    public Volunteer toModelType() throws IllegalValueException {
        final List<Skill> volunteerSkills = new ArrayList<>();
        for (JsonAdaptedSkill skill : skills) {
            volunteerSkills.add(skill.toModelType());
        }

        final List<Event> volunteerEvents = new ArrayList<>();
        for (JsonAdaptedEvent event : assignedEvents) {
            volunteerEvents.add(event.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        final Set<Skill> modelSkills = new HashSet<>(volunteerSkills);
        final Set<Event> modelEvents = new HashSet<>(volunteerEvents);
        return new Volunteer(modelName, modelPhone, modelEmail, modelSkills, modelEvents);
    }

}
