package seedu.address.model.volunteer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.skill.Skill;

/**
 * Tests that a {@code Volunteer}'s {@code Name} matches any of the keywords given.
 */
public class SkillNameContainsKeywordsPredicate implements Predicate<Volunteer> {
    private final List<Name> names;
    private final List<Skill> skills;

    /**
     * Constructor that takes in names and skills
     * @param names list of names
     * @param skills list of skills
     */
    public SkillNameContainsKeywordsPredicate(List<Name> names, List<Skill> skills) {
        this.names = names;
        this.skills = skills;
    }

    /**
     * Constructor that only takes in names
     * @param names list of names
     */
    public SkillNameContainsKeywordsPredicate(List<Name> names) {
        this.names = names;
        this.skills = new ArrayList<>();
    }

    @Override
    public boolean test(Volunteer volunteer) {
        if (names.isEmpty()) {
            assert !skills.isEmpty() : "the check for both skills and names not being empty is done in"
                    + " parse method of VolunteerFindCommandParser class";
            /*
             why allMatch here compared to anyMatch below?
              if you have a command e.g. vfind s/chef s/boxer, then you want to find volunteers that are both chef
              *and* boxer. Whereas if you have command e.g. vfind n/alice n/bob, you want to find volunteers whose names
               are *either* alice or bob
             */
            return skills.stream()
                    .allMatch(skill ->
                            StringUtil.containsSkillIgnoreCase(volunteer.getSkills(), skill));
        }
        if (skills.isEmpty()) {
            return names.stream()
                    .anyMatch(name ->
                            StringUtil.containsWordIgnoreCase(volunteer.getName().fullName, name.fullName));
        }

        assert !names.isEmpty() && !skills.isEmpty() : "names and skills should not be an empty list";

        return names.stream()
                .anyMatch(name ->
                        StringUtil.containsWordIgnoreCase(volunteer.getName().fullName, name.fullName))
                &&
                skills.stream()
                        .anyMatch(skill ->
                                StringUtil.containsSkillIgnoreCase(volunteer.getSkills(), skill));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SkillNameContainsKeywordsPredicate)) {
            return false;
        }

        SkillNameContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (SkillNameContainsKeywordsPredicate) other;
        return names.equals(otherNameContainsKeywordsPredicate.names)
                && skills.equals(otherNameContainsKeywordsPredicate.skills);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("names", names)
                .add("skills", skills)
                .toString();
    }
}
