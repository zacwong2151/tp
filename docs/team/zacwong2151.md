---
layout: default.md
title: "Zac's Project Portfolio Page"
---

### Project: AddressBook Level 3 (Zac)

iVolunteer is an application for volunteer coordinators to manage volunteers, events, and their interactions. The user interacts with it using a CLI, with a GUI created with JavaFX. It is written in Java with about 21 kLoC.

Given below are my contributions to the project.

* **New Feature**: Enhance functionality for Volunteer Create, Volunteer List, and Volunteer Delete commands.
  * What it does: adapted the AB3 code for these commands to suit iVolunteer.
  * Highlights: The original AB3 implementation had an `address` field for their contacts, whereas in iVolunteer, the `address` field was not necessary. Thus, this implementation proved challenging as a huge amount of refactoring had to be done to remove all traces of the `address` field in iVolunteer's volunteers. 

* **New Feature**: Enhance functionality for Volunteer Find command, and implement the Event Find command.
  * What it does: allows the user to filter through the volunteer list for a specific volunteer(s). Able to filter by name or/and by skill.
  * Justification: This feature improves the product significantly because a volunteer coordinator can easily find volunteers with a particular skill set or name.
  * Highlights: This enhancement was not too difficult as it was a build up from the existing AB3 find command. Following this, I also implemented the Event Find command which allows users to filter through the event list.

* **New Feature**: Added the ability to undo/redo previous commands.
    * What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
    * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
    * Highlights: 
      * This enhancement affects existing commands and commands to be added in the future. This is because only certain commands are undo-able, and new undo-able commands that are added in the future will also have to adopt code from the undo feature.
      * It required an in-depth analysis of design alternatives.
      * The implementation was challenging as it required changes to existing commands.
    * Credits: *{mention here if you reused any code/ideas from elsewhere or if a third-party library is heavily used in the feature so that a reader can make a more accurate judgement of how much effort went into the feature}*


* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=zacwong&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=zacwong2151&tabRepo=AY2324S1-CS2103T-T14-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
    * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
    * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
    * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
    * User Guide:
        * Added documentation for the features `delete` and `find` [\#72]()
        * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
    * Developer Guide:
        * Added implementation details of the `delete` feature.

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
    * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
    * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
    * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
    * Integrated a third party library (Natty) to the project ([\#42]())
    * Integrated a new Github plugin (CircleCI) to the team repo

* _{you can add/remove categories in the list above}_
