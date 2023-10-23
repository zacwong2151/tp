package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Volunteer;

/**
 * Represents an Event in the Event list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {
    // Identity fields
    private final EventName eventName;

    // Data fields
    private final Set<Role> roles;
    private final DateTime dateAndTime;
    private final Location location;
    private final Description description;
    private final Set<Material> materials;
    private final Budget budget;
    private final Set<Name> assignedVolunteers;

    /**
     * Every field must be present and not null.
     */
    public Event(EventName eventName, Set<Role> roles, DateTime dateAndTime, Location location, Description description,
                 Set<Material> materials, Budget budget, Set<Name> assignedVolunteers) {
        requireAllNonNull(eventName, roles, dateAndTime, location, description, materials, budget, assignedVolunteers);
        this.eventName = eventName;
        this.roles = roles;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.description = description;
        this.materials = materials;
        this.budget = budget;
        this.assignedVolunteers = assignedVolunteers;
    }

    public EventName getEventName() {
        return eventName;
    }
    /**
     * Returns an immutable Role set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }
    public DateTime getDateAndTime() {
        return dateAndTime;
    }

    public Location getLocation() {
        return location;
    }
    public Description getDescription() {
        return description;
    }
    /**
     * Returns an immutable Material set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Material> getMaterials() {
        return Collections.unmodifiableSet(materials);
    }
    public Budget getBudget() {
        return budget;
    }
    /**
     * Returns an immutable Volunteer set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Name> getAssignedVolunteers() {
        return Collections.unmodifiableSet(assignedVolunteers);
    }
    /**
     * Adds a volunteer to the {@code assignedVolunteers}.
     * @param volunteer The volunteer to be added.
     */
    public void addVolunteer(Volunteer volunteer) {
        assignedVolunteers.add(volunteer.getName());
    }
    /**
     * Checks if a volunteer is already in {@code assignedVolunteers}.
     * @param volunteer The volunteer to check.
     */
    public boolean hasVolunteer(Volunteer volunteer) {
        return assignedVolunteers.contains(volunteer.getName());
    }
    /**
     * Removes a volunteer from the {@code assignedVolunteers}.
     * @param volunteer The volunteer to be removed.
     */
    public void removeVolunteer(Volunteer volunteer) {
        assignedVolunteers.remove(volunteer.getName());
    }
    /**
     * Returns true if both events have the same name.
     * This defines a weaker notion of equality between two volunteers.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventName().equals(getEventName());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two volunteers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return eventName.equals(otherEvent.eventName)
                && roles.equals(otherEvent.roles)
                && dateAndTime.equals(otherEvent.dateAndTime)
                && location.equals(otherEvent.location)
                && description.equals(otherEvent.description)
                && materials.equals(otherEvent.materials)
                && budget.equals(otherEvent.budget)
                && assignedVolunteers.equals(otherEvent.assignedVolunteers);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, roles, dateAndTime, location, description, materials, budget,
                assignedVolunteers);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", eventName)
                .add("roles", roles)
                .add("dateAndTime", dateAndTime)
                .add("location", location)
                .add("description", description)
                .add("materials", materials)
                .add("budget", budget)
                .add("assigned volunteers", assignedVolunteers)
                .toString();
    }

}
