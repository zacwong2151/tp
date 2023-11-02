package seedu.address.logic.commands.eventvolunteercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_ID;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.volunteer.Volunteer;

/**
 * Adds a volunteer to an event.
 */
public class EventAddVolunteerCommand extends Command {

    public static final String COMMAND_WORD = "eaddv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a volunteer to an event. "
            + "Parameters: "
            + PREFIX_EVENT_ID + "EVENT ID "
            + PREFIX_VOLUNTEER_ID + "VOLUNTEER ID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_ID + "1 "
            + PREFIX_VOLUNTEER_ID + "2 ";
    public static final String MESSAGE_SUCCESS = "New volunteer added to event.\nUpdated Event: %1$s\n"
            + "Event currently has %2$d volunteers";
    public static final String MESSAGE_EVENT_FULL = "This event has already reached a maximum of %1$d volunteer(s), "
            + "and is unable to accept any more volunteers";
    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "This volunteer is already assigned to this event";
    public static final String MESSAGE_CLASHING_EVENTS = "This event clashes with the volunteer's assigned events";

    private final Index assignedEventIndex;

    private final Index assignedVolunteerIndex;

    /**
     * Creates an EventAddVolunteerCommand to add the specified {@code Volunteer} to the {@code Event}
     */
    public EventAddVolunteerCommand(Index assignedEventIndex, Index assignedVolunteerIndex) {
        this.assignedEventIndex = assignedEventIndex;
        this.assignedVolunteerIndex = assignedVolunteerIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownEventList = model.getFilteredEventList();
        List<Volunteer> lastShownVolunteerList = model.getFilteredVolunteerList();
        if (assignedEventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (assignedVolunteerIndex.getZeroBased() >= lastShownVolunteerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        Event eventToAssign = lastShownEventList.get(assignedEventIndex.getZeroBased());
        Volunteer volunteerToAssign = lastShownVolunteerList.get(assignedVolunteerIndex.getZeroBased());
        if (eventToAssign.hasVolunteer(volunteerToAssign)) {
            throw new CommandException(MESSAGE_DUPLICATE_VOLUNTEER);
        }
        if (eventToAssign.getAssignedVolunteers().size() >= eventToAssign.getMaxVolunteerSize().maxVolunteerSize) {
            throw new CommandException(String.format(MESSAGE_EVENT_FULL,
                    eventToAssign.getMaxVolunteerSize().maxVolunteerSize));
        }

        Set<EventName> assignedEvents = volunteerToAssign.getAssignedEvents();
        for (EventName eventName : assignedEvents) {
            Event otherEvent = model.getEventStorage().getEvent(eventName);
            if (hasClashingEvents(eventToAssign, otherEvent)) {
                throw new CommandException(MESSAGE_CLASHING_EVENTS);
            }
        }
        Volunteer updatedVolunteer = volunteerToAssign.addEvent(eventToAssign);
        Event updatedEvent = eventToAssign.addVolunteer(volunteerToAssign);
        model.setVolunteer(volunteerToAssign, updatedVolunteer);
        model.setEvent(eventToAssign, updatedEvent);
        model.commitToBothVersionedStorages(model.getEventStorage(), model.getVolunteerStorage());

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(updatedEvent),
                updatedEvent.getAssignedVolunteers().size()));
    }

    /**
     * Checks if {@code eventToAssign} clashes with {@code otherEvent}
     * @param eventToAssign The Event to be assigned.
     * @param otherEvent The Event to be checked.
     * @return true if the two events clash.
     */
    public boolean hasClashingEvents(Event eventToAssign, Event otherEvent) {
        boolean startDateTimeClashes = !eventToAssign.getStartDate().dateAndTime
                .isBefore(otherEvent.getStartDate().dateAndTime)
                && eventToAssign.getStartDate().dateAndTime.isBefore(otherEvent.getEndDate().dateAndTime);
        boolean endDateTimeClashes = eventToAssign.getEndDate().dateAndTime
                .isAfter(otherEvent.getStartDate().dateAndTime)
                && !eventToAssign.getEndDate().dateAndTime.isAfter(otherEvent.getEndDate().dateAndTime);
        boolean startsEarlierAndEndsLater = eventToAssign.getStartDate().dateAndTime
                .isBefore(otherEvent.getStartDate().dateAndTime)
                && eventToAssign.getEndDate().dateAndTime.isAfter(otherEvent.getEndDate().dateAndTime);
        return startDateTimeClashes || endDateTimeClashes || startsEarlierAndEndsLater;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventAddVolunteerCommand)) {
            return false;
        }

        EventAddVolunteerCommand otherEventAddVolunteerCommand = (EventAddVolunteerCommand) other;
        return assignedEventIndex.equals(otherEventAddVolunteerCommand.assignedEventIndex)
                && assignedVolunteerIndex.equals(otherEventAddVolunteerCommand.assignedVolunteerIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignedEventIndex", assignedEventIndex)
                .add("assignedVolunteerIndex", assignedVolunteerIndex)
                .toString();
    }
}
