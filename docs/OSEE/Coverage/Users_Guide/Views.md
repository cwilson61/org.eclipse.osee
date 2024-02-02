[Coverage Home](http://wiki.eclipse.org/OSEE/Coverage/Users_Guide)

# Coverage Navigator

The **Coverage Navigator** provides a launching point for common
coverage tasks including open/deletion/creation of Coverage Packages.
Other reports and operations can be added to this navigator through
extension points. (see [Developer's
Guide](/docs/OSEE/Developers_Guide.md#How_to_Define_Classes_for_Coverage_Importing "wikilink"))
OSEE main menu:
Window--\>Show View--\>Other...--\>OSEE Coverage--\>Coverage Navigator

# Coverage Editor

The Coverage Editor is the central interface for the user to import,
save, disposition and report coverage for a project. It is made up of a
number of tabs providing these functions.

## Overview Tab

Displays roll-up metrics of coverage items stored in **Coverage
Package** or **Coverage Import**. Overview can be printed or a Detailed
Coverage Report can be generated by selecting the appropriate action on
the top right of this tab.

## Coverage Items Tab

This tab allows the searching and navigation of currently stored
**Coverage Units** and **Coverage Items**. By selecting "Search", the
corresponding coverage will show in the table based on whatever search
criteria has been entered at the top of the tab. If nothing is entered,
the top level coverage folders will be shown.

### Coverage Table

The Coverage Table is used throughout the Coverage application and
allows for sorting, filtering and display of information useful for the
current role of the user. These columns use the generic
[XViewer](http://www.eclipse.org/nebula/widgets/xviewer/xviewer.php)
widget which allows for multi-column sorting, column customization and
reporting.

## Coverage Work Product Tab

While the Coverage Editor provides a way to track and disposition
coverage results over time, there is still the need to track changes to
work products based on the disposition results. Coverage provides this
through tight integration with OSEE's Action Tracking System.

This tab provides the ability to relate the ATS Actions that have been
written to track changes to Work Products to the **Coverage Package**.
Once this is done, tasks can be created for any number of coverage items
through the **Coverage Items Tab**.

## Import Tab

The Import Tab allows for importing of Coverage results into the
Coverage Application. Coverage Import is done by programming a Coverage
Import that bridges external coverage tool results and the OSEE Coverage
application. By selecting the corresponding "Import Blam" (see
[Developer's
Guide](/docs/OSEE/Developers_Guide.md#How_to_Define_Classes_for_Coverage_Importing "wikilink")),
the user can enter in the fields necessary to import coverage to a
**Coverage Import** and ultimately persist the data in a **Coverage
Package**.

After a successful import, three more tabs will appear: Import Overview,
Import Items (x) and Import Merge that will aid the user in importing
the first results and successively importing changes into a **Coverage
Package**

### Import Overview Tab

Will provide an Overview of the Imported coverage results that are to be
stored or merged into an existing **Coverage Package**

### Import Items (x) Tab

Will provide the same searching and navigating as the **Coverage Items**
tab except the data is specific to the current import.

### Import Merge Tab

This tab will aid the user in performing the first import or merging
successive imports. The tab is broken into two tables. The left is the
**Coverage Package** table that shows the already imported coverage
items. It works off the same "Search" capabilities as the **Coverage
Items Tab**

  - Import Action - will store the checked import items into the
    currently open **Coverage Package**
  - Show Merge Details Action - will display the merge details for the
    selected import row. This will aid the user in determining what the
    differences are between the already imported data and the new import
  - Link with Import Action - when selecting an import item from the
    "Import Table", this action will open the matching item, if any, in
    the **Coverage Package** table that matches