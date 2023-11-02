package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATERIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import java.util.Set;

import seedu.address.logic.commands.eventcommands.EventCreateCommand;
import seedu.address.logic.commands.eventcommands.EventEditCommand.EditEventDescriptor;
import seedu.address.model.event.Event;
import seedu.address.model.event.Material;
import seedu.address.model.event.Role;

/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns an add command string for adding the {@code event}.
     */
    public static String getEventCreateCommand(Event event) {
        return EventCreateCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getEventName().eventName + " ");
        event.getRoles().stream().forEach(
                s -> sb.append(PREFIX_ROLE + "" + s.requiredQuantity + " " + s.roleName + " ")
        );
        sb.append(PREFIX_START_DATETIME + event.getStartDate().toString() + " ");
        sb.append(PREFIX_END_DATETIME + event.getEndDate().toString() + " ");
        sb.append(PREFIX_LOCATION + event.getLocation().location + " ");
        sb.append(PREFIX_DESCRIPTION + event.getDescription().description + " ");
        event.getMaterials().stream().forEach(
                s -> sb.append(PREFIX_MATERIAL + s.material + " ")
        );
        sb.append(PREFIX_BUDGET + event.getBudget().budget + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditEventDescriptor}'s details.
     */
    public static String getEditEventDescriptorDetails(EditEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getEventName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.eventName).append(" "));
        if (descriptor.getRoles().isPresent()) {
            Set<Role> roles = descriptor.getRoles().get();
            if (roles.isEmpty()) {
                sb.append(PREFIX_ROLE).append(" ");
            } else {
                roles.forEach(r -> sb.append(PREFIX_ROLE).append(r.roleName).append(" "));
            }
        }
        descriptor.getStartDate().ifPresent(date -> sb.append(PREFIX_START_DATETIME).append(date)
                                                        .append(" "));
        descriptor.getEndDate().ifPresent(date -> sb.append(PREFIX_END_DATETIME).append(date).append(" "));
        descriptor.getLocation().ifPresent(l -> sb.append(PREFIX_LOCATION).append(l.location).append(" "));
        descriptor.getDescription().ifPresent(d -> sb.append(PREFIX_DESCRIPTION).append(d.description).append(" "));
        if (descriptor.getMaterials().isPresent()) {
            Set<Material> materials = descriptor.getMaterials().get();
            if (materials.isEmpty()) {
                sb.append(PREFIX_MATERIAL).append(" ");
            } else {
                materials.forEach(m -> sb.append(PREFIX_MATERIAL).append(m.material).append(" "));
            }
        }
        descriptor.getBudget().ifPresent(b -> sb.append(PREFIX_BUDGET).append(b.budget).append(" "));
        return sb.toString();
    }
}
