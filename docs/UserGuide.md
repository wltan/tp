---
layout: page
title: User Guide
---

<div class="toc-no-bullet-points">
  * Table of Contents
  {:toc}
</div>

--------------------------------------------------------------------------------------------------------------------
## 1. Introduction

### 1.1 About Fine$$e

Welcome to Fine$$e!

Looking for an all-in-one solution to help you develop good financial habits? Look no further!

Fine<span>$</span><span>$</span>e is an integrated platform fully customized for tertiary and university students with the aim of helping you track your finances effectively.
Fine<span>$</span><span>$</span>e allows you to keep track of your incomes, expenses and savings with a few simple commands.
Furthermore, to help you cultivate good financial habits, Fine$$e allows you to budget your finances by setting an expense limit and savings goal, as well as viewing your past spending and saving trends.

All your important information will be displayed on our sleek Graphical User Interface (GUI).
Fine$$e is optimized for users who are fast typists and are comfortable working on the Command Line Interface (CLI).

If you want to better manage your finances while cultivating good financial habits, then look no further as Fine\$\$e is definitely the application for you.

Explore our User Guide to find out more about Fine\$\$e’s amazing features.

### 1.2 Navigating the User Guide
The aim of the User Guide is to provide you with all the necessary information required for you to utilize Fine$$e.
We understand that a Command Line Interface (CLI) might not be the easiest to use, hence we have ensured that the information provided is concise and easily readable.

If you require help on setting up Fine$$e, you can go to [Section 2. "Quick Start"](#2-quick-start).

If you want to find out more about the various features that Fine$$s has to offer, you can go to [Section 4. "Features"](#4-features).

If you want to see an overview of Fine$$e's commands, you can visit [Section 5. "Command Summary"](#5-command-summary).

Do take note of the following symbols and formatting used throughout this document:

[Table showing the various symbol/formats that we will be using]

--------------------------------------------------------------------------------------------------------------------
## 2. Quick start

This section will show you the various components that make up Fine<span>$</span><span>$</span>e's user interface.
You can also follow our step-by-step guide on how to install Fine<span>$</span><span>$</span>e and get it to work on your computer.
Let's get started!

### 2.1 Layout of Fine$$e's Interface

The user interface of Fine$$e is divided into 4 tabs, each serving a specific purpose.

#### 2.1.1 Overview Tab

Displays a list of all recent transactions ([incomes](#44-income)/[expenses](#43-expense)), along with a summary of the current [savings goals](#48-savings-goal).
This tab provides you with an overview of your most recent activities and allows you to track your savings.

![Overview Tab](images/userguide/OverviewTab.png)

#### 2.1.2 Incomes Tab

Displays a list of [incomes](#44-income) and [bookmarked incomes](#46-bookmark-income).
This tab allows you to keep track of all your sources of income.

![Incomes Tab](images/userguide/IncomesTab.png)

#### 2.1.3 Expenses Tab

Displays a list of [expenses](#43-expense) and [bookmarked expenses](#45-bookmark-expense).
This tab allows you to keep track of all your sources of expenditure.

![Expenses Tab](images/userguide/ExpensesTab.png)

#### 2.1.4 Analytics Tab

Displays visualizations of your financial habits.
This tab allows you analyze your spending and saving habits through charts.

![Analytics Tab](images/userguide/AnalyticsTab.png)

### 2.2 Installation

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `finesse.jar` from [here](https://github.com/AY2021S1-CS2103T-W16-3/tp/releases).

1. Copy the file to the folder you want to use as the *home folder* for Fine$$e.

1. Double-click the file to start the app.
The GUI similar to the below should appear in a few seconds, with sample data included.
   ![Ui](images/Ui.png)

1. Type a command in the command box and press Enter to execute it. e.g. typing `help` and pressing Enter will bring up the help message.
   Some example commands you can try:

   * `list`: Lists all transactions on the current list.

   * `add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage`:
   Adds an expense with the title `Bubble Tea`, amount `5`, date `03/10/2020` and category `Food & Beverage` to the finance tracker.

   * `delete 3`: Deletes the 3rd transaction shown in the current list.

   * `exit`: Exits the app.

1. Refer to the [Features](#features) below for the details of each command.

> :bulb: If you have any questions, you can check out our [Section 7. FAQ](#7-faq).

--------------------------------------------------------------------------------------------------------------------

## 3. Overview of Features

[To be added]

--------------------------------------------------------------------------------------------------------------------

## 4. Features

This section aims to provide you with in-depth details on Fine\$\$e's unique features, as well as provide you with example usages of the features.

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

### 4.1 Viewing Help

Shows a message with instructions on how to access the user guide.

Format: `help`

Example Usage:
```
help
```

Expected Outcome:
```
Refer to the user guide: https://ay2021s1-cs2103t-w16-3.github.io/tp/UserGuide.html.
Please copy the url and paste it in your favourite browser to view all valid commands.
```

### 4.2 Tabs

Fine$$e consists of the following 4 tabs, each of which serves a different purpose:
* [Overview Tab](#211-overview-tab)
* [Incomes Tab](#212-incomes-tab)
* [Expenses Tab](#213-expenses-tab)
* [Analytics Tab](#214-analytics-tab)

#### 4.2.1 Switch Tabs

Switches the current tab on Fine$$e.

Format: `tab INDEX`
* Switches to the tab corresponding to the specified `INDEX`. The index **must be either 1, 2, 3 or 4**.
    * Index 1 switches to the Overview Tab
    * Index 2 switches to the Incomes Tab
    * Index 3 switches to the Expenses Tab
    * Index 4 switches to the Analytics Tab

Example:
* `tab 3` switches Fine$$e to the Expenses tab.

Example Usage:
```
tab 1
```

Expected Outcome:
```
Switched to overview tab.
```

### 4.3 Expense

An **expense** represents you *paying for something*.
It could be textbooks for the new semester, that cup of bubble tea you drank the other day, or even just taking public transport.

To help you manage your finances, Fine\$\$e records the following information about each of your expenses:
1. **Title**: A descriptive title, to remind you what the expense was about.
1. **Amount**: How much money you paid, to calculate how much you have spent in total.
1. **Date**: The date the payment took place, to track your spending over periods of time.
1. **Categories**: Any number of categories, to help you group related expenses together.

#### 4.3.1 Add Expense

Adds an expense to the finance tracker.

Format: `add-expense t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...`

- `TITLE` should consist of <abbr title="Alphanumeric characters, space, and the special characters !&quot;#$%&'()*+,-./:;&lt;=&gt;?@[\]^_`{\|}~">printable ASCII characters</abbr>,
and cannot begin with a space.
- `AMOUNT` should be a number with 0 or 2 decimal places, with an optional `$` in front.
- `DATE` should be in `dd/mm/yyyy` format, and cannot be later than the current date.
- `CATEGORY` is optional. Multiple `c/` prefixes can be used to specify multiple categories.
Each category name should consist of <abbr title="Alphanumeric characters, space, and the special characters !&quot;#$%&'()*+,-./:;&lt;=&gt;?@[\]^_`{\|}~">printable ASCII characters</abbr>,
and cannot begin with a space.

Shortcut: `adde`, (when on the [Expenses tab](#213-expenses-tab)) `add`

Examples:
- `add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage`
adds a new expense titled `Bubble Tea`, with amount `$5.00`, date `03/10/2020`, and a single category `Food & Beverage`.
- `adde t/Taxi Home from School a/$13.50 d/10/10/2020 c/Transport c/School`
adds a new expense titled `Taxi Home from School`, with amount `$13.50`, date `10/10/2020`, and two categories `Transport` and `School`.

Example Usage:
```
add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage
```

Expected Outcome:
```
New expense added: Bubble Tea Amount: $5.00 Date: 03/10/2020 Categories: [Food & Beverage]
```

#### 4.3.2 Edit Expense

Edits an expense in the finance tracker.

Format: (when on the [Expenses tab](#213-expenses-tab)) `edit INDEX [t/TITLE] [a/AMOUNT] [d/DATE] [c/CATEGORY]...`

- `INDEX` allows you to choose which expense to edit by specifying its position in the expenses list.
- `TITLE`, `AMOUNT`, `DATE`, `CATEGORY` allow you to specify the updated expense information. None of them are mandatory, but at least one must be specified.
  For parameters that have been omitted, the value will remain unchanged.
  - The restrictions on the input values are identical to the [Add Expense](#431-add-expense) feature above.

> :bulb: To specify an edit that removes all categories, use the parameter `c/` with no category name.

Examples:
- `edit 1 a/5 d/22/09/2020` edits the first expense in the expenses list to have amount `$5.00` and date `22/09/2020`.
The rest of the expense information remains unchanged.
- `edit 3 a/$2000 c/` edits the third expense in the expenses list to have amount `$2000.00` and no categories.
The rest of the expense information remains unchanged.

Example Usage:
```
edit 1 a/5 d/22/09/2020
```

Expected Outcome:
```
Edited Expense: Artificial Intelligence: A Modern Approach Amount: $5.00 Date: 22/09/2020 Categories: [Textbook]
```

#### 4.3.3 Delete Expense

Deletes the specified expense from the finance tracker.

Format: (when on the [Expenses tab](#213-expenses-tab)) `delete INDEX`

* `INDEX` allows you to choose which expense to delete by specifying its position in the expenses list.

Example:
* `delete 3` deletes the third expense in the expenses list.

Example Usage:
```
delete 3
```

Expected Outcome:
```
Deleted Expense: Bubble Tea Amount: $4.80 Date: 14/10/2020 Categories: [Food & Beverage]
```

#### 4.3.4 List Expense

Shows a list of all the expenses in the finance tracker.
This effectively resets any current filtering of the expense list, such as those made by `find`.

Format: (when on the [Expenses tab](#213-expenses-tab)) `list`

Example Usage:
```
list
```

Expected Outcome:
```
Listed all expenses.
```

#### 4.3.5 Find Expense

Finds expenses with titles that contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `taxi` will match `Taxi`
* The order of the keywords does not matter. e.g. `Bus Train` will match `Train Bus`
* Only the title is searched.
* Only full words will be matched. e.g. `Snack` will not match `Snacks`
* Titles matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Tea Coffee` will return `Bubble Tea`, `Starbucks Coffee`

Examples:
* `find milk` lists all expenses with titles containing `milk`, such as `strawberry milk` and `Chocolate Milk`.
* `find tea coffee` lists all expenses with titles containing `tea` and/or `coffee`, such as `bubble tea` and `Starbucks Coffee`.

Example Usage:
```
find tea coffee
```

Expected Outcome:
```
2 transactions listed!
```

### 4.4 Income

An **income** represents *you being paid for something*.
It could be your internship allowance, prize money you won from a pitching competition, even just red packets that you receive over Chinese New Year.

To help you manage your finances, Fine\$\$e records the following information about each of your incomes:
1. **Title**: A descriptive title, to remind you what the income was about.
1. **Amount**: How much money you earned, to calculate how much you have earned in total.
1. **Date**: The date you were paid, to track your income over periods of time.
1. **Categories**: Any number of categories, to help you group related incomes together.

#### 4.4.1 Add Income

Adds an income to the finance tracker.

Format: `add-income t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...`

- `TITLE` should consist of <abbr title="Alphanumeric characters, space, and the special characters !&quot;#$%&'()*+,-./:;&lt;=&gt;?@[\]^_`{\|}~">printable ASCII characters</abbr>,
and cannot begin with a space.
- `AMOUNT` should be a number with 0 or 2 decimal places, with an optional `$` in front.
- `DATE` should be in `dd/mm/yyyy` format, and cannot be later than the current date.
- `CATEGORY` is optional. Multiple `c/` prefixes can be used to specify multiple categories.
Each category name should consist of <abbr title="Alphanumeric characters, space, and the special characters !&quot;#$%&'()*+,-./:;&lt;=&gt;?@[\]^_`{\|}~">printable ASCII characters</abbr>,
and cannot begin with a space.

Shortcut: `addi`, (when on the [Income tab](#212-incomes-tab)) `add`

Examples:
- `add-income t/Internship a/560 d/03/10/2020 c/Work`
adds a new income titled `Internship`, with amount `$560.00`, date `03/10/2020`, and a single category `Work`.
- `addi t/Angpao money a/$20 d/25/01/2020 c/CNY c/Gift`
adds a new income titled `Angpao money`, with amount `$20.00`, date `25/01/2020`, and two categories `CNY` and `Gift`.

Example Usage:
```
add-income t/Internship a/560 d/03/10/2020 c/Work
```

Expected Outcome:
```
New income added: Internship Amount: $560.00 Date: 03/10/2020 Categories: [Work]
```

#### 4.4.2 Edit Income

Edits an expense in the finance tracker.

Format: (when on the [Income tab](#212-incomes-tab)) `edit INDEX [t/TITLE] [a/AMOUNT] [d/DATE] [c/CATEGORY]...`

- `INDEX` allows you to choose which income to edit by specifying its position in the incomes list.
- `TITLE`, `AMOUNT`, `DATE`, `CATEGORY` allow you to specify the updated income information. None of them are mandatory, but at least one must be specified.
  For parameters that have been omitted, the value will remain unchanged.
  - The restrictions on the input values are identical to the [Add Income](#441-add-income) feature above.

> :bulb: To specify an edit that removes all categories, use the parameter `c/` with no category name.

Examples:
- `edit 3 a/$2000 c/` edits the third income in the incomes list to have amount `$2000.00` and no categories.
The rest of the income information remains unchanged.
- `edit 1 a/5 d/22/09/2020` edits the first income in the incomes list to have amount `$5.00` and date `22/09/2020`.
The rest of the income information remains unchanged.

Example Usage:
```
edit 3 a/$2000 c/
```

Expected Outcome:
```
Edited Income: Teaching Assistant Amount: $2000.00 Date: 18/10/2020 Categories:
```

#### 4.4.3 Delete Income

Deletes the specified income from the finance tracker.

Format: (when on the [Incomes tab](#212-incomes-tab)) `delete INDEX`

* `INDEX` allows you to choose which income to delete by specifying its position in the incomes list.

Example:
* `delete 3` deletes the third income in the incomes list.

Example Usage:
```
delete 3
```

Expected Outcome:
```
Deleted Income: Teaching Assistant Amount: $1920.00 Date: 18/10/2020 Categories: [CS2103T][CS1101S]
```

#### 4.4.4 List Income

Shows a list of all the incomes in the finance tracker.
This effectively resets any current filtering of the income list, such as those made by `find`.

Format: (when on the [Incomes tab](#212-incomes-tab)) `list`

Example Usage:
```
list
```

Expected Outcome:
```
Listed all incomes.
```


#### 4.4.5 Find Income

Finds incomes with titles that contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `work` will match `Work`
* The order of the keywords does not matter. e.g. `part-time TA` will match `TA part-time`
* Only the title is searched.
* Only full words will be matched. e.g. `internship` will not match `intern`
* Titles matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `allowance prize` will return `Laptop Allowance`, `Hackathon Prize`

Examples:
* `find internship` lists all incomes with titles containing `internship`, such as `shoppee internship` and `Internship at Google`.
* `find allowance prize` lists all incomes with titles containing `allowance` and/or `prize`, such as `laptop allowance` and `Hackathon Prize`.

Example Usage:
```
find allowance prize
```

Expected Outcome:
```
2 transactions listed!
```

### 4.5 Bookmark Expense

Fine\\$\\$e's Bookmark Expense feature is used to store frequent expenses that the user makes such as paying of monthly phone bills or buying bubble tea weekly.
The user will be then be able to edit, delete and convert a bookmarked expense to conveniently add it into Fine\\$\\$e's expense list.

#### 4.5.1 Add Bookmark Expense

Adds a bookmarked expense into the bookmark expense list in Fine$$e.

Format: `add-bookmark-expense t/TITLE a/AMOUNT [c/CATEGORIES]...`

- The title, amount, and categories of the bookmark expense follows the same rules as the [Add Expense](#431-add-expense) feature above.

Shortcut: `addbe`

Examples:
- `add-bookmark-expense t/Phone Bill a/60 c/Utilities c/Personal`
adds a bookmark expense titled `Phone Bill` with amount `$60.00` and two categories `Utilities` and `Personal`.
- `add-bookmark-expense t/Spotify Subscription a/$9 c/Others`
adds a bookmark expense titled `Spotify Subscription` with amount `$9.00` and a single category `Others`.
- `addbe t/Bubble Tea a/$4.50 c/Food & Beverage`
adds a bookmark expense titled `Bubble Tea` with amount `$4.50` and a single category `Food & Beverage`.
- `addbe t/Lunch a/$5.00`
adds a bookmark expense titled `Lunch` with amount `$5.00` and no categories.

Example Usage:
```
add-bookmark-expense t/Phone Bill a/60 c/Utilities
```

Expected Outcome:
```
New bookmark expense added: Phone Bill Amount: $60.00 Categories: [Utilities]
```

#### 4.5.2 Edit Bookmark Expense

Edits the details of bookmark expenses.

Format: (when on the [Expenses tab](#213-expenses-tab)) `edit-bookmark INDEX [t/TITLE] [a/AMOUNT] [c/CATEGORIES]`

- `INDEX` allows you to choose which bookmark expense to edit by specifying its position in the bookmark expense list.
- `TITLE`, `AMOUNT`, `DATE`, `CATEGORY` allow you to specify the updated bookmark expense information.
None of them are mandatory, but at least one must be specified.
For parameters that have been omitted, the value will remain unchanged.
  - The restrictions on the input values are identical to the [Add Expense](#431-add-expense) feature above.

> :bulb: To specify an edit that removes all categories, use the parameter `c/` with no category name.

Examples:
- `edit-bookmark 1 a/65`
edits the first bookmark expense in the bookmark expense list to have an amount of `$65.00`.
- `edit-bookmark 2 t/Part Time a/400 c/Work`
edits the second bookmark expense in the bookmark expense list to have a title of `Part Time`, an amount of `$65.00`,
and a single category `Work`.
- `edit-bookmark 3 c/Others`
edits the third bookmark expense in the bookmark expense list to have a single category `Others`.

Example Usage:
```
edit-bookmark 1 a/65
```

Expected Outcome:
```
Edited Bookmark Expense: Phone Bill Amount: $65.00 Categories: [Utilities][Personal]
```

#### 4.5.3 Delete Bookmark Expense

Deletes the bookmark expense and all of its information from the bookmark expense list in Fine$$e.

Format: (when on the [Expenses tab](#213-expenses-tab)) `delete-bookmark INDEX`

* `INDEX` allows you to choose which bookmark expense to delete by specifying its position in the bookmark expense list.

Example:
* `delete-bookmark 3` deletes the bookmark expense at index 3 in the bookmark expense list.

Example Usage:
```
delete-bookmark 3
```

Expected Outcome:
```
Deleted Bookmark Expense: Phone Bill Amount: $60.00 Categories: [Utilities]
```

#### 4.5.4 Convert Bookmark Expense

Converts a bookmark expense with the date it has been converted on, and adds it to the expense list in Fine$$e.

Format: (when on the [Expenses tab](#213-expenses-tab)) `convert-bookmark INDEX d/DATE`

- The `INDEX` refers to the index number of the bookmark expense shown in the bookmark expense list.
- The `DATE` should be in `dd/mm/yyyy` format, and cannot be later than the current date.

Shortcut: (when on the [Expenses tab](#213-expenses-tab)) `convertb`

Examples:
- `convert-bookmark 2 d/10/10/2020`
converts the bookmark expense at index 2 in the bookmark expense list into an expense with the information of the
specified bookmark expense and date `10/10/2020`.
- `convertb 1 d/05/05/2020`
converts the bookmark expense at index 1 in the bookmark expense list into an expense with the information of the
specified bookmark expense and date `05/05/2020`.

Example Usage:
```
convert-bookmark 2 d/10/10/2020
```

Expected Outcome:
```
Bookmark expense has been converted and successfully added to finance tracker: Phone Bill Amount: $65.00 Date: 10/10/2020 Categories: [Utilities][Personal]
```

### 4.6 Bookmark Income

Fine\\$\\$e's Bookmark Income feature is used to store frequent incomes that the user receives such as monthly salary or stipend for being a teaching assistant.
The user will then be able to edit, delete and convert a bookmarked income to conveniently add it into Fine\\$\\$e's income list.

#### 4.6.1 Add Bookmark Income

Adds a bookmarked income into the bookmark income list in Fine$$e.

Format: `add-bookmark-income t/TITLE a/AMOUNT [c/CATEGORIES]...`

- The title, amount, and categories of the bookmark income follows the same rules as the [Add Income](#441-add-income) feature above.

Shortcut: `addbi`

Examples:
- `add-bookmark-income t/Internship a/$1000 c/Work`
adds a bookmark income titled `Internship` with amount `$1000.00` and a single category `Work`.
- `add-bookmark-income t/Part Time a/450 c/Work c/Startup`
adds a bookmark income titled `Part Time` with amount `$450.00` and two categories `Work` and `Startup`.
- `addbi t/Investments a/400 c/Personal c/Dividends`
adds a bookmark income titled `Investments` with amount `$400.00` and two categories `Personal` and `Dividends`.
- `addbi t/Monthly Allowance a/300`
adds a bookmark income titled `Monthly Allowance` with amount `$300.00` and no categories.

Example Usage:
```
add-bookmark-income t/Internship a/1000 c/Work
```

Expected Outcome:
```
New bookmark income added: Internship Amount: $1000.00 Categories: [Work]
```

#### 4.6.2 Edit Bookmark Income

Edits the details of bookmark incomes.

Format: (when on the [Incomes tab](#212-incomes-tab)) `edit-bookmark INDEX [t/TITLE] [a/AMOUNT] [c/CATEGORIES]...`

- `INDEX` allows you to choose which bookmark income to edit by specifying its position in the bookmark income list.
- `TITLE`, `AMOUNT`, `DATE`, `CATEGORY` allow you to specify the updated bookmark income information.
None of them are mandatory, but at least one must be specified.
For parameters that have been omitted, the value will remain unchanged.
  - The restrictions on the input values are identical to the [Add Income](#441-add-income) feature above.

> :bulb: To specify an edit that removes all categories, use the parameter `c/` with no category name.

Examples:
- `edit-bookmark 1 t/Monthly Tuition c/Work c/Part Time`
edits the first bookmark income in the bookmark income list to have a title of `Monthly Tuition` and
two categories `Work` and `Part Time`.
- `edit-bookmark 2 a/1200`
edits the second bookmark income in the bookmark income list to have an amount of `$1200.00`.
- `edit-bookmark 3 t/Investments a/$300.00`
edits the third bookmark income in the bookmark income list to have a title of `Investments` and an amount of `$300.00`.

Example Usage:
```
edit-bookmark 2 a/1200
```

Expected Outcome:
```
Edited Bookmark Income: Internship Amount: $1200.00 Categories: [Work][Summer]
```

#### 4.6.3 Delete Bookmark Income

Deletes the bookmark income and all of its information from the bookmark income list in Fine$$e.

Format: (when on the [Incomes tab](#212-incomes-tab)) `delete-bookmark INDEX`

* `INDEX` allows you to choose which bookmark income to delete by specifying its position in the bookmark income list.

Example:
- `delete-bookmark 2`
deletes the bookmark income at index 2 in the bookmark income list.

Example Usage:
```
delete-bookmark 2
```

Expected Outcome:
```
Deleted Bookmark Income: Internship Amount: $1200.00 Categories: [Work][Summer]
```

#### 4.6.4 Convert Bookmark Income

Converts a bookmark income with the date it has been converted on and adds it to the income list in Fine$$e.

Format: (when on the [Incomes tab](#212-incomes-tab)) `convert-bookmark INDEX d/DATE`

- The `INDEX` refers to the index number of the bookmark expense shown in the bookmark expense list.
- The `DATE` should be in `dd/mm/yyyy` format, and cannot be later than the current date.

Shortcut: (when on the [Incomes tab](#212-incomes-tab)) `convertb INDEX d/DATE`

Examples:
- `convert-bookmark 1 d/10/10/2020`
converts the bookmark income at index 1 in the bookmark list into an income with the information of the
specified bookmark income and date `10/10/2020`.
- `convertb 2 d/15/10/2020`
converts the bookmark income at index 2 in the bookmark list into an income with the information of the
specified bookmark income and date `15/10/2020`.

Example Usage:
```
convert-bookmark 1 d/10/10/2020
```

Expected Outcome:
```
Bookmark income has been converted and successfully added to finance tracker: Teaching Assistant Amount: $1890.00 Date: 10/10/2020 Categories: [CS1231S][CS1101S]
```

### 4.7 Expense Limit

Ever feel like your wallet just won't stop bleeding? Try setting a monthly expense limit!
The expense limit feature allows you to limit your spending by setting a budget for the month.
Once the expense limit is set, it will be visible on the Overview tab along with your remaining budget for this month.

#### 4.7.1 Set Expense Limit

Sets the monthly expense limit in the finance tracker.

Format: `set-expense-limit AMOUNT`

Shortcut: `setel AMOUNT`

* `AMOUNT` should be a number with 0 or 2 decimal places, with an optional `$` in front.

Example:
* `set-expense-limit 500` sets the monthly expense limit in the finance tracker to be `$500.00`.

Example Usage:
```
setel $400.00
```

Expected Outcome:
```
New monthly expense limit set: $400.00
```

### 4.8 Savings Goal

Want to save up for the new PS5 but can't seem to no matter what? Fine$$e has you covered!
The savings goal feature allows you to save consistently by setting a monthly savings goal, so that you can save up bit by bit and build good financial habits.
Once the savings goal is set, it will be visible on the Overview tab along with your current savings for this month.

#### 4.8.1 Set Savings Goal

Sets the monthly savings goal in the finance tracker.

Format: `set-savings-goal AMOUNT`

Shortcut: `setsg AMOUNT`

* `AMOUNT` should be a number with 0 or 2 decimal places, with an optional `$` in front.

Example:
* `set-savings-goal 100` sets the monthly savings goal in the finance tracker to be `$100.00`.

Example Usage:
```
setel $50.00
```

Expected Outcome:
```
New monthly savings goal set: $50.00
```

### 4.9 Analytics

Want to see your spending/saving trends?
The Analytics feature will allow you to view a variety of trends based on the incomes/expenses you have entered into the Finance Tracker.
These analytics will give you a better overview and help you better understand your incomes/expenses and make the targeted changes to your habits if need be.

There are three bar charts that show the following data over the past three months:
* Total monthly expenses
* Total monthly incomes
* Total monthly savings

The data in the bar charts are automatically updated to include any recent modifications you have made in the Finance Tracker every time you switch to the [Analytics Tab]().
This will ensure that you will always see the latest information about your spending and saving trends.

### 4.10 Exiting the Program
Exits the program.

Format: `exit`

### 4.11 Saving the data

Fine$$e data is saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

## 5. Command Summary

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

--------------------------------------------------------------------------------------------------------------------

## 6. Glossary

[To be added]

--------------------------------------------------------------------------------------------------------------------

## 7. FAQ

**Q**: How do I transfer my data to another Computer?

**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Fine$$e finance tracker in the home folder.
