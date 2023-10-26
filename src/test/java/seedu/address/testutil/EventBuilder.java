package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Budget;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Material;
import seedu.address.model.event.Role;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Volunteer objects.
 */
public class EventBuilder {
    public static final String DEFAULT_EVENTNAME = "Cleaning the park";
    public static final String DEFAULT_DATE_AND_TIME = "23/9/2023 1900";
    public static final String DEFAULT_DATE_AND_TIME_2 = "23/9/2023 2300";
    public static final String DEFAULT_LOCATION = "Hougang";
    public static final String DEFAULT_DESCRIPTION = "Clean very clean";
    public static final String DEFAULT_BUDGET = "50.00";

    private EventName eventName;
    private Set<Role> roles;
    private DateTime startDate;
    private DateTime endDate;
    private Location location;
    private Description description;
    private Set<Material> materials;
    private Budget budget;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        eventName = new EventName(DEFAULT_EVENTNAME);
        roles = new HashSet<>();
        startDate = new DateTime(DEFAULT_DATE_AND_TIME);
        endDate = new DateTime(DEFAULT_DATE_AND_TIME_2);
        location = new Location(DEFAULT_LOCATION);
        description = new Description(DEFAULT_DESCRIPTION);
        materials = new HashSet<>();
        budget = new Budget(DEFAULT_BUDGET);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventName = eventToCopy.getEventName();
        roles = new HashSet<>(eventToCopy.getRoles());
        startDate = eventToCopy.getStartDate();
        endDate = eventToCopy.getEndDate();
        location = eventToCopy.getLocation();
        description = eventToCopy.getDescription();
        materials = new HashSet<>(eventToCopy.getMaterials());
        budget = eventToCopy.getBudget();
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = new EventName(eventName);
        return this;
    }

    /**
     * Parses the {@code roles} into a {@code Set<Role>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withRoles(String ... roles) {
        this.roles = SampleDataUtil.getRoleSet(roles);
        return this;
    }

    /**
     * Sets the start {@code DateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStartDate(String startDate) {
        this.startDate = new DateTime(startDate);
        return this;
    }

    /**
     * Sets the end {@code DateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndDate(String endDate) {
        this.endDate = new DateTime(endDate);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code materials} into a {@code Set<Material>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withMaterials(String ... materials) {
        this.materials = SampleDataUtil.getMaterialSet(materials);
        return this;
    }

    /**
     * Sets the {@code Budget} of the {@code Event} that we are building.
     */
    public EventBuilder withBudget(String budget) {
        this.budget = new Budget(budget);
        return this;
    }

    public Event build() {
        return new Event(eventName, roles, startDate, endDate, location, description, materials, budget);
    }

}
