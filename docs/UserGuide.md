---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# iVolunteer User Guide

iVolunteer is your dedicated application for volunteer coordination, designed with **volunteer coordinators** in mind. It combines the efficiency of a **Command Line Interface (CLI)** with the advantages of a **Graphical User Interface (GUI)**. If you're looking to enhance your volunteer coordination tasks, iVolunteer is the tool that empowers you to **get things done efficiently**. Whether you're creating events, updating details, or managing volunteers, iVolunteer streamlines the process for **swift and effective coordination**.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) or above installed in your Computer.

1. Download the latest `iVolunteer.jar` from [here](https://github.com/AY2324S1-CS2103T-T14-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for iVolunteer.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar iVolunteer.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `elist` : Lists all events
   
   * `eadd n/food donation r/chef r/packer d/23-9-2023 1500 dsc/help food distribution m/50 potatoes b/50` : Creates an event with name `food donation`, roles needed `chef` and `packer`, event date `23rd September 2023, 3pm`, description `help food distribution`, materials needed `50 potatoes` and budget `$50`

   * `edelete 3` : Deletes the 3rd event in the current event list
   
   * `vlist` : Lists all volunteers.

   * `vadd n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a volunteer named `John Doe` to the list of volunteers.

   * `vdelete 3` : Deletes the 3rd volunteer in the current volunteer list.

   * `vclear` : Deletes all volunteers.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a volunteer into an event: `eaddv` [COMING SOON]

Adds a volunteer to an event by id or name.

Format: `eaddv vid/VOLUNTEER_ID eid/EVENT_ID` or `eaddv vn/VOLUNTEER_NAME en/EVENT_NAME`

Parameters:
* vn/ - Volunteer name
* vid/ - Volunteer id
* eid/ - Event id
* en/ - Event name

Restrictions:
* The maximum number of characters of the event and volunteer is 50.
* The event and volunteer name entered must exist.
* The event id must be greater than or equal to 0 and lesser than the number of events existed.
* The volunteer id must be greater than or equal to 0 and lesser than the number of volunteers existed.

Examples:
* `eaddv vid/1 eid/1`
* `eaddv vn/Betsy Crowe en/fundraising`

### Listing all volunteers in an event: `elistv` [COMING SOON]

Shows a list of all volunteers in an event.

Format: `elistv eid/EVENT_ID` or `elistv en/EVENT_NAME`

Parameters:
* eid/ - Event id 
* en/ - Event name

Restrictions:
* The maximum number of characters of the event is 50.
* The event name entered must exist.
* The event id must be greater than or equal to 0 and lesser than the number of events existed.

Examples:
* `elistv eid/1`
* `elistv en/fundraising`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Removing a volunteer in an event: `eremovev` [COMING SOON]

Removes the specified volunteer from an event by name or id.

Format: `eremovev vid/VOLUNTEER_ID eid/EVENT_ID` or `eremovev vn/VOLUNTEER_NAME en/EVENT_NAME`

Parameters:
* vn/ - Volunteer name
* vid/ - Volunteer id
* en/ - Event name
* eid/ - Event id

Restrictions:
* The maximum number of characters of the event is 50.
* The event and volunteer name entered must exist.
* The maximum number of characters of a volunteer name is 30.
* The id must not exceed the number of volunteers in the event and greater or equal to 0.

Examples:
* `eremovev vid/1 eid/1`
* `eremovev vn/John en/fundraising`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Creating an event [coming soon]

Volunteer Coordinators can create new events.

Format: `ecreate add n/EVENT_NAME r/ROLES_NEEDED… d/DATE_AND_TIME l/LOCATION dsc/DESCRIPTION [m/MATERIALS_AND_LOGISTICS_NEEDED]... [b/BUDGET]`

Parameters:
 * n/ - Event name
 * r/ - Roles needed for the event
 * d/ - Date and time of the event
 * l/ - Location of the event
 * dsc/ - Description of the event
 * m/ - Materials needed for the event
 * b/ - Budget for the event

Restrictions:
* All parameters must be separated by a single space.
* The date and time format must be exactly `DD-MM-YYYY TTTT`
* The budget argument must be a floating point number with 2 decimal places.

Examples:
* `ecreate n/food donation r/chef r/packer d/23-9-2023 1500 dsc/help food distribution m/50 potatoes b/50` creates an event with name `food donation`, roles needed `chef` and `packer`, event date `23rd September 2023, 3pm`, description `help food distribution`, materials needed `50 potatoes` and budget `$50`

### Listing all events : `elist` [coming soon]
Volunteer coordinators can see all the events they are organising. For each event, only the most important information will be shown: name, date and time, location.

Format: `elist`

### Reading an individual event : `eshow` [coming soon]
Volunteer coordinators can read up more about an individual event, to familiarize themselves with its requirements while planning for it.

Format: `eshow EVENT_ID`

Restrictions:
* First part must be `eshow`
* Second part must be an integer that represents a valid `EVENT_ID`: If the list of events is 10 events long, the acceptable values will be from 1-10.
* First and second parts must be separated by a single space.

Examples:
* `eshow 7` will result in a pop-up window appearing, listing all details of the event at id `7`. This includes its name, date and time, location, roles needed, logistics needed (if any), budget (if any), and a description.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
