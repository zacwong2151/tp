package seedu.address.storage.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_PARAMS;

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
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";
    private final String eventName;
    private final List<JsonAdaptedRole> roles = new ArrayList<>();
    private final String startDate;
    private final String endDate;
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
                            @JsonProperty("startDate") String startDate,
                            @JsonProperty("endDate") String endDate,
                            @JsonProperty("location") String location,
                            @JsonProperty("description") String description,
                            @JsonProperty("materials") List<JsonAdaptedMaterial> materials,
                            @JsonProperty("budget") String budget) {
        this.eventName = eventName;
        if (roles != null) {
            this.roles.addAll(roles);
        }
        this.startDate = startDate;
        this.endDate = endDate;
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
        eventName = source.getEventName().eventName;
        roles.addAll(source.getRoles().stream()
                .map(JsonAdaptedRole::new)
                .collect(Collectors.toList()));
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
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
     * @throws IllegalValueException if there were any data constraints violated in the adapted volunteer.
     */
    public Event toModelType() throws IllegalValueException {
        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                            EventName.class.getSimpleName()));
        }
        if (!EventName.isValidEventName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }
        final EventName modelName = new EventName(eventName);

        final List<Role> eventRoles = new ArrayList<>();
        for (JsonAdaptedRole role : roles) {
            eventRoles.add(role.toModelType());
        }

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(startDate)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelStartDate = new DateTime(startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(endDate)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelEndDate = new DateTime(endDate);

        // compare end datetime to ensure it is after/same as start datetime
        if (modelEndDate.dateAndTime.isBefore(modelStartDate.dateAndTime)) {
            throw new IllegalValueException(MESSAGE_INVALID_DATE_PARAMS);
        }

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

        Budget modelBudget;
        if (budget.isEmpty()) {
            modelBudget = new Budget("");
        } else if (!Budget.isValidBudget(budget)) {
            throw new IllegalValueException(Budget.MESSAGE_CONSTRAINTS);
        }
        modelBudget = new Budget(budget);

        final Set<Role> modelRoles = new HashSet<>(eventRoles);
        final Set<Material> modelMaterials = new HashSet<>(eventMaterials);
        return new Event(modelName, modelRoles, modelStartDate, modelEndDate, modelLocation, modelDescription,
                modelMaterials, modelBudget);
    }

}
