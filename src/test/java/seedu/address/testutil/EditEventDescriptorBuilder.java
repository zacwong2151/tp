package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.eventcommands.EventEditCommand.EditEventDescriptor;
import seedu.address.model.event.Budget;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Location;
import seedu.address.model.event.Material;
import seedu.address.model.event.Role;


/**
 * A utility class to help with building EditVolunteerDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code volunteer}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setRoles(event.getRoles());
        descriptor.setStartDate(event.getStartDate());
        descriptor.setEndDate(event.getEndDate());
        descriptor.setLocation(event.getLocation());
        descriptor.setDescription(event.getDescription());
        descriptor.setMaterials(event.getMaterials());
        descriptor.setBudget(event.getBudget());
    }

    /**
     * Parses the {@code roles} into a {@code Set<Role>} and set it to the {@code EditEventDescriptor}
     * that we are building.
     */
    public EditEventDescriptorBuilder withRoles(String... roles) {
        Set<Role> roleSet = Stream.of(roles).map(Role::new).collect(Collectors.toSet());
        descriptor.setRoles(roleSet);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(new DateTime(startDate));
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEndDate(String endDate) {
        descriptor.setEndDate(new DateTime(endDate));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Parses the {@code materials} into a {@code Set<Material>} and set it to the {@code EditEventDescriptor}
     * that we are building.
     */
    public EditEventDescriptorBuilder withMaterials(String... materials) {
        Set<Material> materialSet = Stream.of(materials).map(Material::new).collect(Collectors.toSet());
        descriptor.setMaterials(materialSet);
        return this;
    }

    /**
     * Sets the {@code Budget} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withBudget(String budget) {
        descriptor.setBudget(new Budget(budget));
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}

