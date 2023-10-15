package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Budget;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Material;
import seedu.address.model.event.Role;


/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";
    private final String eventName;
    private final List<JsonAdaptedRole> roles = new ArrayList<>();
    private final String dateAndTime;
    private final String location;
    private final String description;
    private final List<JsonAdaptedMaterial> materials = new ArrayList<>();
    private final String budget;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventName") String eventName,
                            @JsonProperty("roles") List<JsonAdaptedRole> roles,
                             @JsonProperty("dateAndTime") String dateAndTime, @JsonProperty("location") String location,
                             @JsonProperty("description") String description,
                            @JsonProperty("materials") List<JsonAdaptedMaterial> materials,
                            @JsonProperty("budget") String budget) {
        this.eventName = eventName;
        if (roles != null) {
            this.roles.addAll(roles);
        }
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.description = description;
        if (materials != null) {
            this.materials.addAll(materials);
        }
        this.budget = budget;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        eventName = source.getEventName().name;
        roles.addAll(source.getRoles().stream()
                .map(JsonAdaptedRole::new)
                .collect(Collectors.toList()));
        dateAndTime = source.getDateAndTime().toString();
        location = source.getLocation().location;
        description = source.getDescription().description;
        materials.addAll(source.getMaterials().stream()
                .map(JsonAdaptedMaterial::new)
                .collect(Collectors.toList()));
        budget = source.getBudget().budget;
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Event toModelType() throws IllegalValueException {
        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                            EventName.class.getSimpleName()));
        }
        if (!EventName.isValidName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }
        final EventName modelName = new EventName(eventName);

        final List<Role> eventRoles = new ArrayList<>();
        for (JsonAdaptedRole role : roles) {
            eventRoles.add(role.toModelType());
        }

        if (dateAndTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateAndTime)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateAndTime);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                            Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        final List<Material> eventMaterials = new ArrayList<>();
        for (JsonAdaptedMaterial material : materials) {
            eventMaterials.add(material.toModelType());
        }

        if (budget == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName()));
        }
        if (!Budget.isValidBudget(budget)) {
            throw new IllegalValueException(Budget.MESSAGE_CONSTRAINTS);
        }
        final Budget modelBudget = new Budget(budget);

        final Set<Role> modelRoles = new HashSet<>(eventRoles);
        final Set<Material> modelMaterials = new HashSet<>(eventMaterials);
        return new Event(modelName, modelRoles, modelDateTime, modelLocation, modelDescription, modelMaterials,
                            modelBudget);
    }

}
