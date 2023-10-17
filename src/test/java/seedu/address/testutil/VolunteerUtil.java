package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.Set;

import seedu.address.logic.commands.volunteercommands.VolunteerCreateCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerEditCommand.EditVolunteerDescriptor;
import seedu.address.model.skill.Skill;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class for Volunteer.
 */
public class VolunteerUtil {

    /**
     * Returns an add command string for adding the {@code volunteer}.
     */
    public static String getAddCommand(Volunteer volunteer) {
        return VolunteerCreateCommand.COMMAND_WORD + " " + getVolunteerDetails(volunteer);
    }

    /**
     * Returns the part of command string for the given {@code volunteer}'s details.
     */
    public static String getVolunteerDetails(Volunteer volunteer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + volunteer.getName().fullName + " ");
        sb.append(PREFIX_PHONE + volunteer.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + volunteer.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + volunteer.getAddress().value + " ");
        volunteer.getSkills().stream().forEach(
            s -> sb.append(PREFIX_SKILL + s.skillName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditVolunteerDescriptor}'s details.
     */
    public static String getEditVolunteerDescriptorDetails(EditVolunteerDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getSkills().isPresent()) {
            Set<Skill> skills = descriptor.getSkills().get();
            if (skills.isEmpty()) {
                sb.append(PREFIX_SKILL);
            } else {
                skills.forEach(s -> sb.append(PREFIX_SKILL).append(s.skillName).append(" "));
            }
        }
        return sb.toString();
    }
}
