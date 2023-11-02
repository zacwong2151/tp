package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_START_DATETIME = new Prefix("sd/");
    public static final Prefix PREFIX_END_DATETIME = new Prefix("ed/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("dsc/");
    public static final Prefix PREFIX_MATERIAL = new Prefix("m/");
    public static final Prefix PREFIX_BUDGET = new Prefix("b/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_SKILL = new Prefix("s/");
    public static final Prefix PREFIX_EVENT_ID = new Prefix("eid/");
    public static final Prefix PREFIX_VOLUNTEER_ID = new Prefix("vid/");
    public static final Prefix PREFIX_MAX_VOLUNTEER_SIZE = new Prefix("vs/");
}
