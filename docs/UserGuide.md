---
layout: page
title: User Guide
---

Fine\$\$e is a **desktop finance tracker optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Fine\$\$e can help you track your transactions (expenses/incomes) and cultivate good financial habits faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `finesse.jar` from [here](https://github.com/AY2021S1-CS2103T-W16-3/tp/releases).

1. Copy the file to the folder you want to use as the *home folder* for Fine$$e.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds, but without any sample data.<br>
   ![Ui](images/Ui.png)

1. Type a command in the command box and press Enter to execute it. e.g. typing `help` and pressing Enter will bring up the help message.<br>
   Some example commands you can try:

   * `list`: Lists all transactions on the current list.

   * `add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage`: Adds an expense with the title `Bubble Tea`, amount `5`, date `03/10/2020` and category `Food & Beverage` to the finance tracker.

   * `delete 3`: Deletes the 3rd transaction shown in the current list.

   * `exit`: Exits the app.

1. Refer to the [Features](#features) below for the details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add-expense t/TITLE`, `TITLE` is a parameter which can be used as `add t/Bubble Tea`.

* Items in square brackets are optional.<br>
  e.g `t/TITLE [c/CATEGORY]` can be used as `t/Bubble Tea c/Food & Beverage` or as `t/Bubble Tea`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[c/CATEGORY]…​` can be used as `  ` (i.e. 0 times), `c/Food & Beverage`, `c/Food & Beverage c/Tea` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `t/TITLE a/AMOUNT`, `a/AMOUNT t/TITLE` is also acceptable.

</div>

### Viewing help: `help`
Shows a message with instructions on how to access the user guide.

Format: `help`

### Add an expense: `add-expense` / `adde` / (on Expense tab) `add`
Adds an expense to the finance tracker.

Format: `add-expense t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...`

* `AMOUNT` should be a number with 0 or 2 decimal places, with an optional `$` in front.
* `DATE` should be in `dd/mm/yyyy` format.

Examples:
* `add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage`
* `adde t/Taxi Home from School a/$13.50 d/10/10/2020 c/Transport c/School`

### Add an income: `add-income` / `addi` / (on Income tab) `add`
Adds an income to the finance tracker.

Format: `add-income t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...`

* `AMOUNT` should be a number with 0 or 2 decimal places, with an optional `$` in front.
* `DATE` should be in `dd/mm/yyyy` format.

Examples:
* `add-income t/Internship a/560 d/03/10/2020 c/Work`
* `addi t/Angpao money a/$20 d/25/01/2020 c/CNY c/Gift`

### List transactions: `list`

Shows a list of all transactions on the current tab.
* On Overview tab: lists all transactions (expenses/incomes).
* On Expenses tab: lists all expenses.
* On Income tab: lists all incomes.

Format: `list`

### Finding transactions by keyword: `find`

Finds transactions with titles that contain any of the given keywords on the current tab.
* On Overview tab: searches all transactions (expenses/incomes).
* On Expenses tab: searches all expenses.
* On Income tab: searches all incomes.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `taxi` will match `Taxi`
* The order of the keywords does not matter. e.g. `Bus Train` will match `Train Bus`
* Only the title is searched.
* Only full words will be matched. e.g. `Snack` will not match `Snacks`
* Titles matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Tea Coffee` will return `Bubble Tea`, `Starbucks Coffee`

Examples:
* `find milk` returns `strawberry milk` and `Chocolate Milk`
* `find tea coffee` returns `Bubble Tea` and `Starbucks Coffee`

### Edit a transaction: `edit`

Edits an existing transaction in the finance tracker.

Format: `edit INDEX [t/TITLE] [a/AMOUNT] [d/DATE] [c/CATEGORY]…​`

* Edits the transaction at the specified `INDEX` on the current list.
  The index refers to the index number shown on the current displayed list.
  The index **must be a positive integer** 1, 2, 3, …​
    * On Overview tab: edits the transaction at the specified `INDEX` on the current transaction list.
    * On Expenses tab: edits the expense at the specified `INDEX` on the current expenses list.
    * On Income tab: edits the income at the specified `INDEX` on the current incomes list.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing category, the existing categories for that transaction will be removed i.e adding of categories is not cumulative.
* You can remove all the transaction's categories by typing `c/` without specifying any categories after it.

Examples:
*  (On Expenses tab) `list` followed by `edit 1 t/Brunch d/22/09/2020` edits the title and date of the 1st expense in the expenses list to be `Brunch` and `22/09/2020` respectively.
*  (On Income tab) `find prize` followed by `edit 2 a/500 c/` edits the amount of the 2nd income in the resulting incomes list of the `find` command to be `500` and clears all existing categories.

### Delete a transaction: `delete`

Deletes a specified transaction from the finance tracker.

Format: `delete INDEX`

* Deletes a specified transaction at the specified `INDEX` on the current list.
  The index refers to the index number shown on the current displayed list.
  The index **must be a positive integer** 1, 2, 3, …​
    * On Overview tab: deletes the transaction at the specified `INDEX` on the current transaction list.
    * On Expenses tab: deletes the expense at the specified `INDEX` on the current expenses list.
    * On Income tab: deletes the income at the specified `INDEX` on the current incomes list.

Examples:
* (On Expenses tab) `list` followed by `delete 3` deletes the 3rd expense in the expenses list from the finance tracker.
* (On Income tab) `find allowance` followed by `delete 2` deletes the 2nd income in the resulting incomes list of the `find` command from the finance tracker.

### Switch tab: `tab`

Switches the current tab on the finance tracker.

Format: `delete INDEX`

* Switches to the tab corresponding to the specified `INDEX`. The index **must be 1, 2, 3, or 4**.
    1. Switches to the Overview tab.
    1. Switches to the Expenses tab.
    1. Switches to the Income tab.
    1. Switches to the Analytics tab.

### Exiting the app: `exit`
Exits the app.

Format: `exit`

### Saving the data

Fine$$e data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Fine$$e finance tracker in the home folder.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action | Format | Examples
------|------|--------
Add Expense | `add-expense t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` <br> `adde t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` <br> (On Expenses tab) `add t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` | `add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage` <br> `adde t/Taxi Home from School a/$13.50 d/10/10/2020 c/Transport c/School`
Add Income | `add-income t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` <br> `addi t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` <br> (On Income tab) `add t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` | `add-income t/Internship a/560 d/03/10/2020 c/Work` <br> `addi t/Angpao money a/$20 d/10/10/2020 c/CNY c/Gift`
List | `list`
Find | `find KEYWORD [MORE_KEYWORDS]` | `find milk` <br> `find tea coffee`
Edit | `edit INDEX [t/TITLE] [a/AMOUNT] [d/DATE] [c/CATEGORY]…​`| `edit 1 t/Brunch d/22/09/2020` <br> `edit 2 a/500 c/`
Delete | `delete INDEX` | `delete 1`
Tab | `tab INDEX` | `tab 2`
Help | `help`
Exit | `exit`
