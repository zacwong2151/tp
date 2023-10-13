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


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

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

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Create an event [coming soon]

Volunteer Coordinators can create new events by specifying the following parameters.
* Roles needed
* Date and Time
* Location
* Brief Description
* Logistics and material needed(optional)
* Budget(optional)

Format: `event create n/EVENT_NAME r/ROLES_NEEDED… d/DATE_AND_TIME l/LOCATION dsc/DESCRIPTION [m/MATERIALS_AND_LOGISTICS_NEEDED]... [b/BUDGET]`
* Note that shorthands `e create` and `ec` can also be used instead of `event create`
* All parameters must be separated by a single space.
* Date and Time - The format must be exactly `DD-MM-YYYY TTTT`
* Budget - Argument must be a floating point number with at most 2 decimal places.


Examples:
* `event create n/food donation r/chef r/packer d/23-9-2023 1500 dsc/help food distribution m/50 potatoes b/50` creates an event with name `food donation`, roles needed `chef` and `packer`, event date `23rd September 2023, 3pm`, description `help food distribution`, materials needed `50 potatoes` and budget `$50`

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

**Q**: As a Windows 11 user, how do I open a command terminal?<br>
**A**: On your Windows 11 computer, do the following:
1. Select the Start Menu (the Windows icon) in the taskbar, or press the Windows key.
2. Type `cmd` in the search bar.
3. Select Command Prompt from the list.

**Q**: As a Mac user, how do I open a command terminal?<br>
**A**: On your Mac, do one of the following:
1. Click the Launchpad icon in the Dock, type Terminal in the search field, then click Terminal. 
2. In the Finder, open the `/Applications/Utilities` folder, then double-click Terminal.

--------------------------------------------------------------------------------------------------------------------

## Known issues

coming soon

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                                        | Format, Examples                                                                                                                                                                                                                                                                       |
|-----------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Create a new event**                        | `ecreate n/EVENT_NAME r/ROLES_NEEDED… d/DATE_AND_TIME l/LOCATION dsc/DESCRIPTION [m/MATERIALS_AND_LOGISTICS_NEEDED]... [b/BUDGET]` <br> e.g., `ecreate n/Fundraising at Orchard r/logistics leader d/23-09-2023 1500 dsc/station at locations to ask for donations m/50 tin cans b/50` |
| **List all events**                           | `elist`                                                                                                                                                                                                                                                                                |
| **Read an individual event**                  | `eshow EVENT_ID` <br> e.g., `eshow 8`                                                                                                                                                                                                                                                  |
| **Delete an event**                           | `edelete EVENT_ID` <br> e.g., `edelete 3`                                                                                                                                                                                                                                              |
| **Create a new volunteer profile**            | `vcreate vn/VOLUNTEER_NAME hp/PHONE_NUMBER e/EMAIL [s/SKILLS]...`<br> e.g.,`vcreate vn/John Lim hp/81234567 e/john123@gmail.com s/Cooking`                                                                                                                                             |
| **List all volunteer profiles**               | `vlist`                                                                                                                                                                                                                                                                                |
| **Delete a volunteer profile**                | `vdelete VOLUNTEER_ID` <br> e.g., `vdelete 4`                                                                                                                                                                                                                                          |
| **Add a volunteer to an event**               | `eaddv vid/VOLUNTEER_ID eid/EVENT_ID`<br> e.g., `eaddv vid/1 eid/3` <br> Alternatively, `eaddv vn/VOLUNTEER_NAME en/EVENT_NAME` <br> e.g., `eaddv vn/Betsy Crowe en/Fundraising for needy families`                                                                                    |
| **Check for volunteers assigned to an event** | `elistv eid/EVENT_ID` <br> e.g. `elistv eid/8` <br> Alternatively, `elistv en/EVENT_NAME` <br> e.g., `elistv en/October beach clean up`                                                                                                                                                |
| **Remove a volunteer from an event**          | `eremovev vid/VOLUNTEER_ID eid/EVENT_ID`<br> e.g., `eremovev vid/3 eid/4` <br> Alternatively, `eremovev vn/VOLUNTEER_NAME en/EVENT_NAME` <br> e.g., `eremovev vn/John Lim en/October beach clean up`                                                                                   |
