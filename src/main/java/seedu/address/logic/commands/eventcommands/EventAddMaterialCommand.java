package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATERIAL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.Material;

/**
 * Adds an amount of material to a specified event in the event list.
 */
public class EventAddMaterialCommand extends Command {

    public static final String COMMAND_WORD = "eaddm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an amount of material to a specified event in"
            + " the event list. Parameters: "
            + PREFIX_EVENT_ID + "EVENT_ID "
            + PREFIX_MATERIAL + "MATERIAL\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_ID + "3 "
            + PREFIX_MATERIAL + "20 TONGS";
    public static final String MESSAGE_SUCCESS = "Amount of material updated in event: %1$s";

    public static final String MESSAGE_MATERIAL_NOT_FOUND = "Material %1$s is not found in the event.";

    private final Index eventIndex;
    private final int quantityToAdd;
    private final String materialName;

    /**
     * Creates an EventAddMaterialCommand to add the specified quantity of {@code Material}.
     *
     * @param eventIndex The index of the event to add the specified quantity of {@code Material} in.
     * @param quantityToAdd The integer quantity to add.
     * @param materialName The name of the material within the {@code event} object.
     */
    public EventAddMaterialCommand(Index eventIndex, int quantityToAdd, String materialName) {
        requireNonNull(eventIndex);
        requireNonNull(materialName);
        this.eventIndex = eventIndex;
        this.quantityToAdd = quantityToAdd;
        this.materialName = materialName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Event> lastShownList = model.getFilteredEventList();

        if (eventIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event event = lastShownList.get(eventIndex.getZeroBased());
        Material material = event.getMaterialByName(materialName);

        if (material == null) {
            throw new CommandException(String.format(MESSAGE_MATERIAL_NOT_FOUND, materialName));
        }

        Material updatedMaterial = material.addItems(quantityToAdd);
        Event updatedEvent = createEditedEvent(event, updatedMaterial);
        model.setEvent(event, updatedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.commitToBothVersionedStorages(model.getEventStorage(), model.getVolunteerStorage());

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(event)));
    }

    /**
     * Creates and returns an {@code Event} with the updated materials, which is indicated by the
     * {@code updatedMaterial} parameter.
     *
     * @param eventToEdit The event to be edited.
     * @param updatedMaterial The updated {@code Material} to be put in the list of materials within the event to edit.
     * @return New Event object containing the new {@code updatedMaterial}.
     */
    private static Event createEditedEvent(Event eventToEdit, Material updatedMaterial) {
        assert eventToEdit != null;
        requireNonNull(updatedMaterial);

        Set<Material> updatedMaterials = eventToEdit
                .getMaterials()
                .stream()
                .map(material -> (material.material.equals(updatedMaterial.material) ? updatedMaterial : material))
                .collect(Collectors.toSet());

        return new Event(eventToEdit.getEventName(),
                eventToEdit.getRoles(),
                eventToEdit.getStartDate(),
                eventToEdit.getEndDate(),
                eventToEdit.getLocation(),
                eventToEdit.getDescription(),
                updatedMaterials,
                eventToEdit.getBudget(),
                eventToEdit.getAssignedVolunteers(),
                eventToEdit.getMaxVolunteerSize());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventAddMaterialCommand)) {
            return false;
        }

        EventAddMaterialCommand otherEventAddMaterialCommand = (EventAddMaterialCommand) other;
        return eventIndex.equals(otherEventAddMaterialCommand.eventIndex)
                && quantityToAdd == otherEventAddMaterialCommand.quantityToAdd
                && materialName.equals(otherEventAddMaterialCommand.materialName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventIndex", eventIndex)
                .add("quantityToAdd", quantityToAdd)
                .add("materialName", materialName)
                .toString();
    }
}
