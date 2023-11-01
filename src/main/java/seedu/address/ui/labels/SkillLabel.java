package seedu.address.ui.labels;

import seedu.address.model.skill.Skill;

/**
 * An {@code EnclosedLabel} that holds a specific {@code Skill}.
 */
public class SkillLabel extends EnclosedLabel {

    /**
     * Creates a {@code SkillLabel} with the given {@code Skill}.
     */
    public SkillLabel(Skill skill) {
        super(skill.skillName);
    }
}
