---
layout: default.md
title: "Jordan Kang's Project Portfolio Page"
---

### Project: iVolunteer

iVolunteer is an application for volunteer coordinators to manage volunteers, events, and their interactions. The user interacts with it using a CLI, with a GUI created with JavaFX. It is written in Java with about 14 kLoC.

Given below are my contributions to the project.

* **New Feature**: Create Event Feature
  * Provided the **foundational code** for creating volunteering events in our application. This is important as the main goal of our application is to allow volunteer coordinators to be able to store their events, along with the critical details associated with them.
  * The process of implementing this feature includes 
    **creating a new entity** `Event` with their associated fields, alongside the preexisting `Person` entity.
    **Parsing of parameter arguments**, such as ensuring that the budget for an event is non-negative and in 2 decimal places to replicate real-life scenarios.
    Implementing **checking for valid values** for the new classes created.
    Ensuring that the newly created `Event` can be **added to the Model seamlessly**.
    Ensuring that Event data can be **written into and retrieved from storage files** successfully.
    **Writing test cases** to ensure the feature works correctly.

* **New Feature**: Event Add Volunteer Feature
  * Implemented the functionality of **assigning volunteers to events**. This is important as it helps volunteer coordinators **easily keep track** of which volunteers are participating in which event, easing the volunteer management process.
  The process of implementing this feature includes
    **creating a new field** for `Event`: `assignedVolunteers` which is a `Set<Name>` which stores the names of volunteers assigned to the event. I also created a `assignedEvents` field for `Volunteer` which works the same way.
    **Parsing of parameter arguments**, such as ensuring that the `Event` and `Volunteer` indexes provided are valid.
    **Implementing the logic** for adding a volunteer to an event, such as checking if the volunteer is already assigned to the event and checking if any of the volunteer's assigned events clashes with the current event.
    **Updating the `Model`** with the updated `Event` and `Volunteer`.
    **Writing test cases** to ensure the feature works correctly.

* **New Feature**: Event List Volunteer Feature
  * Implemented the functionality of **showing the volunteers assigned to an event**. This is important as it **increases the convenience** for volunteer coordinators as they can view the volunteers participating in an event all in a single panel.
  * The process of implementing this feature includes
    **parsing of parameter arguments**, such as ensuring that the event index provided is valid.
    **Implementing the logic** for listing the assigned volunteers, such as mapping the names in `assignedVolunteers` to the actual volunteer objects.
    **Integrating the resultant list with JavaFX** to display the volunteers in a single ListView panel.
    **Writing test cases** to ensure the feature works correctly.

* **New Feature**: Event Remove Volunteer Feature
  * Implemented the functionality of **removing volunteers from events**. This is important as it helps volunteer coordinators **easily keep track** of which volunteers are no longer participating in which event, easing the volunteer management process.
  * The process of implementing this feature includes
    **parsing of parameter arguments**, such as ensuring that the `Event` and `Volunteer` indexes provided are valid.
    **Implementing the logic** for removing a volunteer from an event, such as checking if the volunteer is assigned to the event before removing.
    **Updating the `Model`** with the updated `Event` and `Volunteer`.
    **Writing test cases** to ensure the feature works correctly.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=jordankanghm&breakdown=true)

* **Enhancements to existing features**:
  * Initially, the code we were given was to be used as an address book with the main class being `Person`. At the beginning of the project, I refactored all aspects of the code to match `Volunteer`, which was the main target of our application.
  * The process of implementing this feature includes
    **changing the command word for each command**, e.g. The command word for adding a new `Person` was changed from `add` to `vcreate`.
    **Refactoring all file names**, e.g. from `AddCommand` to `VolunteerCreateCommand`.
    **Updating the `Model`** to accept `Volunteer` instead of `Person`.
    **Changing the writing and reading of data** from `Person` to `Volunteer`.

* **Project management**:
  * Helped set up the issue tracker and milestone management on GitHub.

* **Documentation**:
    * User Guide:
      * Added the Quick Start portion of the UG (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/47/files)
      * Added documentation for event create, event add volunteer, event list volunteer and event remove volunteer features(`ecreate`, `eaddv`, `elistv`, `eremovev`). (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/81/files, https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/111/files)
    * Developer Guide:
      * Added use cases for event create feature. (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/53/files)
      * Added implementation details for event create, event add volunteer, event list volunteer and event remove volunteer features, inclusive of UML class, object and sequence diagrams. (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/115/files, https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/219/files)

* **Community**:
  * Provided feedback on the PRs of team members (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/91, https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/229)
  * Responded to feedback given on own PRs (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/53)

<!-- force a page break -->
<div style="page-break-before:always"></div> 

