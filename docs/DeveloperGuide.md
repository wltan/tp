---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103T-W16-3/tp/blob/master/src/main/java/ay2021s1_cs2103_w16_3/finesse/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103T-W16-3/tp/blob/master/src/main/java/ay2021s1_cs2103_w16_3/finesse/MainApp.java). It is responsible for:
* on app launch - initializing the components in the correct sequence, and connecting them to each other.
* on shut down - shutting down the components and invoking cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The App's User Interface.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components:

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/AY2021S1-CS2103T-W16-3/tp/blob/master/src/main/java/ay2021s1_cs2103_w16_3/finesse/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TransactionListPanel`, `StatusBarFooter` etc. All of these, including `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts is defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of [`MainWindow`](https://github.com/AY2021S1-CS2103T-W16-3/tp/blob/master/src/main/java/ay2021s1_cs2103_w16_3/finesse/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2021S1-CS2103T-W16-3/tp/blob/master/src/main/resources/view/MainWindow.fxml).

The `UI` component:

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103T-W16-3/tp/blob/master/src/main/java/ay2021s1_cs2103_w16_3/finesse/logic/Logic.java)

1. `Logic` uses the `FinanceTrackerParser` class to parse the user command.
1. This results in a `Command` object which is executed by `LogicManager`.
1. The command execution can affect `Model` (e.g. adding a transaction).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to `Ui`.
1. In addition, the `CommandResult` object can also instruct `Ui` to perform certain actions, such as displaying the help message to the user.

Below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/AY2021S1-CS2103T-W16-3/tp/blob/master/src/main/java/ay2021s1_cs2103_w16_3/finesse/model/Model.java)

The `Model`:

* stores a `UserPref` object that represents the user’s preferences.
* stores the finance tracker data in the following components:
    * a `TransactionList` containing `Transaction`s.
    * a `BookmarkExpenseList` and `BookmarkIncomeList`, each containing `BookmarkTransaction`s.
    * a `MonthlyBudget`.
* exposes `MonthlyBudget`, which can be 'observed' e.g. the UI can be bound to the values in the class so that the UI automatically updates when the values in the class changes.
* exposes an unmodifiable `ObservableList<Transaction>`, `ObservableList<Expense>`, `ObservableList<Income>`, `ObservableList<BookmarkExpense>` and `ObservableList<BookmarkIncome>` each, which can be 'observed' e.g. the UI can be bound to these lists so that the UI automatically updates when the data in the lists change.
* does not depend on any of the other three components (`UI`, `Logic`, `Storage`).

A `Transaction`:
* represents a unit of user data of transactions within the finance tracker.
* is either an `Expense` or an `Income`.
* is composed of a `Title`, an `Amount`, a `Date`, and any number of `Category`s. These are known as *data fields*.

A `BookmarkTransaction`:
* represents a unit of user data of bookmark transactions within the finance tracker.
* is either a `BookmarkExpense` or a `BookmarkIncome`.
* is composed of a `Title`, an `Amount`, and any number of `Category`s.

The *data fields* `Title`, `Amount`, `Date`, and `Category` are encapsulations of an underlying Java data type.

| Data Field  | Underlying Java Data Type   |
| ----------- | -------------------------   |
| `Title`     | `java.lang.String`          |
| `Amount`    | `java.math.BigDecimal`      |
| `Date`      | `java.time.LocalDate`       |
| `Category`  | `java.lang.String`          |

The underlying Java data types allow more operations to be done on `Transaction` objects, such as filtering `Transaction`s by `Date`, or aggregating the `Amount`s of `Expense`s and `Income`s.

<div markdown="span" class="alert alert-info">:information_source: &nbsp;
**Note:** All data fields take in a `String` in their constructor, regardless of the underlying Java data type.
Within the constructor, data validation takes place to ensure that the `String` correctly represents a valid data field.
If the `String` given is not valid, an `IllegalArgumentException` will be thrown.
Otherwise, the `String` is parsed into the underlying Java data type.<br><br>
The reason for this is that when a data field object needs to be created, it is generally from a `String`
(such as when a user command parsed from the `Logic` component, or when a transaction is loaded from the `Storage` component).
This abstraction is maintained so that the implementation of other components (`Logic` and `Storage`) is independent of
the underlying Java data type choices in the `Model` component.
</div>

The `FinanceTracker` has the following fields:
* a `TransactionList` field which stores all `Expense` and `Income` objects together.
* a `BookmarkExpenseList` field which stores all `BookmarkExpense` objects.
* a `BookmarkIncomeList` field which stores all `BookmarkIncome` objects.
* a `MonthlyBudget` field which stores the user's set monthly expense limit and monthly savings goal, as well as all calculated values for the user's statistics.

The `ModelManager` has the following fields:
* three `FilteredList` fields which point to the same `ObservableList` obtained from `FinanceTracker::getTransactionList`. The `Predicate` fields in the three `FilteredList` fields are set such that:
    * `filteredTransactions` shows a view of all `Transaction` objects.
    * `filteredExpenses` shows a view of all `Transaction` objects of type `Expense`.
    * `filteredIncomes` shows a view of all `Transaction` objects of type `Income`.

    The motivation behind having three lists is due to the fact that there are three tabs in the user interface which display transactions, each having its own list while at the same time retrieving data from the same transaction list.
* two `ObservableList` fields, `castFilteredExpenses` and `castFilteredIncomes` that contain the same objects as that in `filteredExpenses` and `filteredIncomes` respectively. This is so that the UI can automatically update when the values in the two lists change.
* two `FilteredList` fields, `FilteredBookmarkExpenses` and `FilteredBookmarkIncomes` that point to the `ObservableList`s obtained from `FinanceTracker::getBookmarkExpenseList` and `FinanceTracker::getBookmarkIncomeList` respectively.
* a `MonthlyBudget` field which is the same as the `MonthlyBudget` stored in `FinanceTracker`.

### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103T-W16-3/tp/blob/master/src/main/java/ay2021s1_cs2103_w16_3/finesse/storage/Storage.java)

The `Storage` component:

* can save `UserPref` objects in `JSON` format and read it back.
* can save the finance tracker data in `JSON` format and read it back.

`JsonAdaptedExpense` and `JsonAdaptedIncome` are `JSON`-friendly adaptations of `Expense` and `Income` respectively.
However, unlike `Expense` and `Income`, all the fields in `JsonAdaptedExpense` and `JsonAdaptedIncome` are `String` objects.
This is so that the fields are compatible with the `JSON` format.

`JsonSerializableFinanceTracker` contains two `List` fields, one containing all `JsonAdaptedExpense` objects and the other containing all `JsonAdaptedIncome` objects.
Despite the similarities between `Expense` and `Income`, and despite the fact that objects of both types are put into the same list in `FinanceTracker`,
the motivation behind keeping them separately is to ensure that they are separately listed in the `JSON` data file.

### Common classes

Classes used by multiple components are in the `ay2021s1_cs2103_w16_3.finesse.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Transactions

#### Add transactions feature

##### Overview
The add transactions feature allows users to add transactions into the `FinanceTracker`.
Each new transaction must have the data fields `Title`, `Amount`, `Date`, and `Category`.

Below is the class diagram of the components involved in the add transactions feature.

![Class diagram for add transactions feature](images/AddTransactionClassDiagram.png)

##### Implementation of feature

The add transactions feature is implemented via `AddExpenseCommand` and `AddIncomeCommand`, which are created from `AddExpenseCommandParser` and `AddIncomeCommandParser` respectively.

1. `AddExpenseCommandParser` and `AddIncomeCommandParser` take in the argument string and parses it into an `ArgumentMultimap` that contains all the different data fields mapped (as strings) to their respective prefix.
1. The strings are then parsed to create the data fields within the Model component (dependency arrows omitted in the above diagram for simplicity).
1. The parsers use the data fields to create `Expense` or `Income` objects, which are then used to create `AddExpenseCommand` or `AddIncomeCommand` objects.

`FinanceTrackerParser` decides whether `AddExpenseCommandParser` or `AddIncomeCommandParser` is used.
* On an `add-expense` or `adde` command, `AddExpenseCommandParser` is used.
* On an `add-income` or `addi` command, `AddIncomeCommandParser` is used.
* On an `add` command:
  * `AddExpenseCommandParser` is used if the user is currently on the Expenses tab.
  * `AddIncomeCommandParser` is used if the user is currently on the Incomes tab.
  * A `ParseException` is thrown if the user is currently on neither of the above tabs.

##### Adding transactions

Below is the sequence diagram for interactions within the `Logic` and `Model` components when the user inputs the `adde t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage` command while on the Expenses tab.
Note that the arguments in the full command string have been abbreviated to `...`.

![Sequence diagram for executing the `add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage` command on the Expenses tab](images/AddTransactionSequenceDiagram.png)

When `AddIncomeCommandParser` is used, the sequence diagram is similar, but replace all occurrences of `Expense` with `Income`.

The following activity diagram summarizes what happens within `FinanceTrackerParser` when the user executes a new add command:

![Activity diagram for executing the add command](images/AddTransactionActivityDiagram.png)

*Note: The diagram contains many repetitive processes. However, it cannot be simplified due to the limitations of PlantUML.*

##### Design considerations

The alternative implementations considered, as well as the rationale behind our current implementation are as follows:

| Alternative considered  | Current implementation and rationale   |
| ----------- | -------------------------   |
| Collapse both `AddExpenseCommandParser` and `AddIncomeCommandParser` into a single `AddTransactionCommandParser` whose result can then be used to create `AddExpenseCommand` or `AddIncomeCommand`. | Keep two separate parsers `AddExpenseCommandParser` and `AddIncomeCommandParser`.<br>Although the `add` command can result in either `AddExpenseCommand` or `AddIncomeCommand`, `adde` will always create `AddExpenseCommand` while `addi` will always create `AddIncomeCommand`. It is more apt to use a specific parser in the latter cases. |
| Implement either `add` or `adde` + `addi` but not both. This reduces the amount of development work needed. | Both options are implemented to provide the user with a choice for whichever command they feel is most convenient. `add` can be used to add to the currently active tab, while `adde` and `addi` can be used on any tab. |


#### Edit transactions feature
##### Overview
The edit transactions feature allows users to edit existing transactions in the `FinanceTracker`.
At least one of the data fields `Title`, `Amount`, `Date`, and `Category` must be modified.

Below is the class diagram of the components involved in the edit transactions feature.

![Class diagram for edit transactions feature](images/EditTransactionClassDiagram.png)

##### Implementation of feature

The edit transactions feature is implemented via `EditCommandParser`, which returns an `EditCommand` that is then used to create `EditExpenseCommand` or `EditIncomeCommand`.

1. `EditCommandParser` takes in the argument string and parses it into an `ArgumentMultimap` that contains all the different data fields mapped (as strings) to their respective prefix.
1. The index is parsed into an `Index`, which is used to locate the transaction to be modified during command execution.
1. The remaining strings are then parsed to create the data fields within the Model component (dependency arrows omitted in the above diagram for simplicity).
1. The data fields are used to create an `EditTransactionDescriptor` describing all of the new data fields.
1. The `Index` is combined with the `EditTransactionDescriptor` to return an `EditCommand`.

`FinanceTrackerParser` decides whether `EditExpenseCommand` or `EditIncomeCommand` is created from the resulting `EditCommand`.
* `EditExpenseCommand` is used if the user is currently on the Expenses tab.
* `EditIncomeCommand` is used if the user is currently on the Incomes tab.

##### Editing transactions

Below is the sequence diagram for interactions within the `Logic` and `Model` components when the user inputs the `edit 1 a/5` command while on the Expenses tab.
It is split into two diagrams, one for parsing and one for execution.

![Sequence diagram for parsing the `edit 1 a/5` command on the Expenses tab](images/EditTransactionSequenceDiagram.png)

![Sequence diagram for executing the `edit 1 a/5` command on the Expenses tab](images/EditTransactionSequenceDiagram2.png)

When `EditExpenseCommand` is used, the sequence diagram is similar, but replace all occurrences of `Expense` with `Income`.

The following activity diagram summarizes what happens within `FinanceTrackerParser` when the user executes a new edit command:

![Activity diagram for executing the edit command](images/EditTransactionActivityDiagram.png)

#### Delete transactions feature
##### Overview
The delete transactions feature allows users to remove transactions from the `FinanceTracker`.

Below is the class diagram of the components involved in the delete transactions feature.

![Class diagram for delete transactions feature](images/DeleteTransactionClassDiagram.png)

##### Implementation of feature

The edit transactions feature is implemented via `DeleteCommandParser`, which returns an `DeleteCommand` that is then used to create `DeleteExpenseCommand` or `DeleteIncomeCommand`.

1. `DeleteCommandParser` take in the argument string and parses the index into an `Index`, which is used to locate the transaction to be deleted during command execution.
1. The `Index` is used to return a `DeleteCommand`.

`FinanceTrackerParser` decides whether `DeleteExpenseCommand` or `DeleteIncomeCommand` is created from the resulting `DeleteCommand`.
* `DeleteExpenseCommand` is used if the user is currently on the Expenses tab.
* `DeleteIncomeCommand` is used if the user is currently on the Incomes tab.

##### Deleting transactions

Below is the sequence diagram for interactions within the `Logic` and `Model` components when the user inputs the `delete 1` command while on the Expenses tab.

![Sequence diagram for parsing the `delete 1` command on the Expenses tab](images/DeleteSequenceDiagram.png)

When `DeleteExpenseCommand` is used, the sequence diagram is similar, but replace all occurrences of `Expense` with `Income`.

The following activity diagram summarizes what happens within `FinanceTrackerParser` when the user executes a new delete command:

![Activity diagram for executing the delete command](images/DeleteTransactionActivityDiagram.png)

#### Find transactions feature
##### Overview

The find transactions feature allows users to search for transactions in the `FinanceTracker` by various search parameters.
Each search parameter corresponds to a different predicate which will be used to filter transactions in the various `FilteredList`s.

Below is the class diagram of the components involved in the find transactions feature.

![Class diagram for find transactions feature](images/FindClassDiagram.png)

##### Implementation of feature

The find transactions feature is implemented via `FindCommandParser`, as well as the following commands:

* `FindCommand`, the base command that is returned when the command is parsed.
* `FindTransactionCommand`, to be executed when the user inputs the command on the Overview tab.
* `FindExpenseCommand`, to be executed when the user inputs the command on the Expenses tab.
* `FindIncomeCommand`, to be executed when the user inputs the command on the Incomes tab.

`FindCommandParser` takes in the argument string and parses it into an `ArgumentMultimap` that contains all the different search parameters mapped to their respective prefix.

Depending on the parameters present, `FindCommandParser` then creates a `List<Predicate<Transaction>>` containing the predicates that will be used to filter the transactions.
It then returns a `FindCommand` containing the list of predicates.

Depending on the UI tab the user inputted the command in, a `FindXYZCommand` (`FindTransactionCommand`, `FindExpenseCommand` or `FindIncomeCommand`) will be created from the base `FindCommand`.
When executed, the `FindXYZCommand` will combine all of the predicates in the list into a `combinedPredicate`, then sets the predicate of the respective `FilteredList` in `ModelManager` so that only the transactions matching the `combinedPredicate` will be displayed in the UI.

The list of predicates that can be used to filter the `FilteredList`s are as follows:
* `TitleContainsKeyphrasesPredicate` checks if any of the given keyphrases is present in the transaction's title.
* `HasExactAmountPredicate` checks if the transaction's amount is equal to the given amount.
* `OnExactDatePredicate` checks if the transaction's date is equal to the given date.
* `HasCategoriesPredicate` checks if the transaction's categories contains any of the given categories.
* `InAmountRangePredicate` checks if the transaction's amount is within the given amount range.
* `InDateRangePredicate` checks if the transaction's date is within the given date range.

##### Finding transactions

Below is the sequence diagram for interactions within the `Logic` and `Model` components when the user inputs the `"find t/tea a/5"` command while on the Overview tab.

![Sequence diagram for executing the `find t/tea a/5` command on the Overview tab](images/FindSequenceDiagram.png)

The following activity diagram summarizes what happens when the user executes a new find command:

![Activity diagram for executing the find command](images/FindActivityDiagram.png)

##### Design considerations

The alternative implementations considered, as well as the rationale behind our current implementation are as follows:

| Alternative considered  | Current implementation and rationale   |
| ----------- | -------------------------   |
| Having separate command parsers for each tab in which the find command can be input, e.g. `FindTransactionCommandParser`, `FindExpenseCommandParser` and `FindIncomeCommandParser`, which return a `FindTransactionCommand`, a `FindExpenseCommand` and a `FindIncomeCommand` respectively. | Use only one `FindCommandParser`, which returns a `FindCommand` that is then further split into the respective `FindXYZCommand`. This is because the parsing for the input is similar same regardless of the tab the user is on.          |
| Have `FindCommandParser` take in an `Index` corresponding to the parameter being searched. | Make the input of similar format to that of adding transactions, so that the input can be parsed into an `ArgumentMultimap` which is then used generate the relevant `Predicate`s. This is so that multiple search parameters can be employed in one command. |

#### Programmatically switch selected tab

Several of the expense/income-specific commands update the list of transactions displayed in a particular tab.
Examples of these include the `add-expense` and `add-income` commands.
As such, on the execution of such commands, the UI has to switch to the affected tab to display the result of the command execution.

The ability to programmatically update the selected tab is implemented via `CommandResult`.
Upon the successful execution of a command, a `CommandResult` is generated and passed back to `MainWindow`.
This `CommandResult` contains the necessary information which the `MainWindow` needs to update the user interface, such as:
* The feedback to be displayed to the user;
* Whether to exit the application; and
* Whether to programmatically switch to a different tab in the user interface.

![Interactions inside the Logic Component when executing commands](images/CommandResultSequenceDiagram.png)

Alternatives considered:

* Add a method in `MainWindow` which can be called to programmatically switch tabs in the user interface upon execution of the command.
  This was decided against as it would result in a much tighter coupling of `Logic` and `UI` components.

### Budgeting
##### Overview

The budgeting feature allows users to track their remaining budget and current savings based on their set monthly expense limit and monthly savings goal.
The user sets the monthly expense limit and monthly savings goal, and the remaining budget and current savings will automatically be calculated based on the transactions in the `FinanceTracker`.

The class diagram below depicts the components involved in the budget feature.

![Class diagram for budgeting feature](images/BudgetClassDiagram.png)

##### Implementation of feature

The find transactions feature is implemented via `MonthlyBudget`, which contains the following fields:

* Two `ObjectProperty<Amount>` fields for `monthlyExpenseLimit` and `monthlySavingsGoal`.
* Two `ObjectProperty<CalculatedAmount>` fields for `remainingBudget` and `currentSavings`.
* Three `ObservableList<CalculatedAmount>` fields for `monthlyExpenses`, `monthlyIncomes` and `monthlySavings`, which are used for the [Analytics feature](#analytics).
* An `ObservableList<String>` of `months`, to determine which elements in the `ObservableList<CalculatedAmount>` corresponds to which month.

`CalculatedAmount` differs from `Amount` in that a `CalculatedAmount` can be negative, and supports addition and subtraction.

The method that is integral to the `MonthlyBudget` is the `calculateBudgetInfo` method.
It takes in the current `TransactionList` in the `FinanceTracker` and an integer `numOfMonths`, and recalculates all `CalculatedAmount`s in the `MonthlyBudget` as follows:
* Sum the amounts of all `Expense`s in the `TransactionList` dated in the current month, subtracts it from `monthlyExpenseLimit`, and sets it as the `remainingBudget`.
* Sums the amounts of all `Expense`s in the `TransactionList` dated in the current month, subtracts it from the sum of all `Income`s dated in the current month, and sets it as the `currentSavings`.
* For the past number of months indicated by `numOfMonths` (including the current month), calculate the total expenses, total incomes and total savings for each month and add them to the corresponding `ObservableList<CalculatedAmount>`.

The commands that trigger a call of `calculateBudgetInfo` are as follows:
* `AddExpenseCommand`, `AddIncomeCommand`, `DeleteExpenseCommand` and `DeleteIncomeCommand`, which change the total expenses/incomes of the `Transaction`s in the `TransactionList`.
* `EditExpenseCommand` and `EditIncomeCommand`, if the `Amount` is edited.
* `SetExpenseLimitCommand`, which changes the `Amount` in `monthlyExpenseLimit`.
* `SetSavingsGoalCommand`, which changes the `Amount` in `monthlySavingsGoal`.

`calculateBudgetInfo` is also called by `Storage` whenever the finance tracker data is loaded upon startup.

##### Set monthly spending limit

`SetExpenseLimitCommand` and `SetSavingsGoalCommand` work in similar ways.
Below is the sequence diagram for interactions within the `Logic` and `Model` components when the user inputs the `"setel a/500"` command.

![Sequence diagram for executing the `setel a/500` command](images/SetExpenseLimitSequenceDiagram.png)

The following activity diagram summarizes what happens when the user executes any command that changes the calculated values in `MonthlyBudget` (e.g. setting expense limit):

![Activity diagram for executing any command that changes the budget](images/BudgetActivityDiagram.png)

##### Design considerations

The alternative implementations considered, as well as the rationale behind our current implementation are as follows:

| Alternative considered  | Current implementation and rationale   |
| ----------- | -------------------------   |
| Use the `Amount` class for calculated amounts. | Use a separate class `CalculatedAmount` for calculated amounts, so as to avoid breaking abstraction and support negative values.

### Bookmark Transactions

#### Proposed Implementation
The Frequent Expense feature consists of `add-frequent-expense`, `edit-frequent-expense`, `delete-frequent-expense`,
`convert-frequent-expense`.

Frequent expenses are expenses that the user have identified to be occurring regularly or with high frequency.
Examples of such expenses can be phone bill, music subscription or buying bubble tea on a weekly basis.

This feature allows users to add frequent expenses to the finance tracker. The user can then choose to convert a
frequent expense to an expense that the user wants to add to the transaction list in the finance tracker. This is done by
calling `Logic#execute` which creates an  `AddFrequentExpenseCommand`. This command then calls
`ModelManager#addFrequentExpense`, adding the specified frequent expense into the frequent expense list.

This feature also simplifies the deletion and editing of frequent expenses, and they work in a similar manner.

#### Convert Frequent Expense To Expense Feature

The purpose of this feature is to allow users to add expenses, that they add to the transaction list in the finance
tracker on a frequent basis in a convenient manner. After adding a frequent expense into the frequent expense list in
the finance tracker, the user can call convert-frequent-expense to the respective frequent expense to make the
conversion.

Given below is the proposed UML sequence diagram and explanation of an example usage scenario for
convert-frequent-expense

![Sequence Diagram of the Convert Frequent Expense To Expense Feature](images/ConvertBookmarkExpenseSequenceDiagram.png)

1. User enters the command `convert-frequent-expense 1 d/05/05/2020`, where `1` is the index of the respective frequent
expense in the frequent expense list and `d/05/05/20` is the date on which the user wants to carry out the conversion,
to add the selected frequent expense into the expense list. This command is executed by `LogicManager`, which will then
call `FinanceTrackerParser#parseCommand(“convert-frequent-expense 1 d/05/05/2020”)`. This will create a
`ConvertFrequentExpenseCommand` object and return it to the `LogicManager`.
2. The `LogicManager` will then call `ConvertFrequentExpenseCommand#execute`, which will call
`Model#getFilteredFrequentExpenseList()` to retrieve the list of frequent expenses in the finance tracker.
3. The program will then retrieve the specified frequent expense from the list of frequent expenses. It will
then call `FrequentExpense#convert` on the frequent expense together with the date the user has inputted and convert
it to an `Expense` object.
4. After converting the `FrequentExpense` object to an `Expense` object, it will call `Model#addExpense` to add the
`Expense` object to the expense list in the finance tracker.

### Analytics

[Coming soon]

--------------------------------------------------------------------------------------------------------------------

## **Appendix A: Product scope**

**Target user profile**:

* Tertiary student
* Has a need to track everyday expenses/incomes
* Wishes to cultivate good saving habits
* Prefers desktop apps
* Prefers typing to mouse interactions

**Value proposition**: Manage finances and cultivate good financial habits (such as saving) efficiently by typing in CLI commands

--------------------------------------------------------------------------------------------------------------------

## **Appendix B: User stories**

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​               | I want to …​                  | So that I can…​                                                          |
| -------- | ------------------------ | -------------------------------  | --------------------------------------------------------------------------- |
| `* * *`  | new user                 | see usage instructions           | refer to instructions when I forget how to use the app                      |
| `* * *`  | user                     | add an expense                   |                                                                             |
| `* * *`  | user                     | add an income                    |                                                                             |
| `* * *`  | user                     | view a list of past transactions | keep track of my past expenses/incomes                                      |
| `* * *`  | user                     | edit a transaction               | update expenses/incomes that were entered wrongly                           |
| `* * *`  | user                     | delete a transaction             | remove expenses/incomes that were entered wrongly                           |
| `* * *`  | user                     | group transactions by categories | keep track of my past expenses/incomes across various categories            |
| `* * *`  | user                     | search for transactions          | easily find related expenses/incomes                                        |
| `* * *`  | user                     | bookmark expenses                | easily add recurring expenses                                               |
| `* * *`  | user                     | bookmark incomes                 | easily add recurring incomes                                                |
| `* *`    | user                     | set a monthly spending limit     | plan the maximum amount I wish to spend for the month                       |
| `* *`    | user                     | set a monthly saving goal        | work towards saving consistently and reaching my savings goals              |
| `* *`    | user                     | see my remaining monthly budget  | track how much I have left to spend for the month                           |
| `* *`    | user                     | set a current monthly savings    | track how much I have to save to hit my savings goal                        |
| `* *`    | user                     | view my saving trends            | better plan my future expenses                                              |

--------------------------------------------------------------------------------------------------------------------

## **Appendix C: Use cases**

(For all use cases below, the **System** is `Fine$$e` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add an expense**

**MSS**

1.  User requests to add an expense
2.  Fine$$e adds the expense

    Use case ends.

**Extensions**

* 1a. The given data fields are invalid.

    * 1a1. Fine$$e shows an error message.

      Use case ends.

**Use case: Edit an expense**

**MSS**

1.  User requests to list expenses.
2.  Fine$$e shows a list of expenses.
3.  User requests to edit a specific expense.
4.  Fine$$e edits the expense.

    Use case ends.

**Extensions**

* 1a. User filters the list first.

    * 1a1. User filters the list using <u>Find an expense</u>.

      Use case resumes at step 3.

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Fine$$e shows an error message.

      Use case resumes at step 2.

* 3b. No data fields are specified.

    * 3b1. Fine$$e shows an error message.

      Use case resumes at step 2.

* 3c. The given data fields are invalid.

    * 3c1. Fine$$e shows an error message.

      Use case resumes at step 2.

**MSS**

1.  User requests to list expenses.
2.  Fine$$e shows a list of expenses.
3.  User requests to delete a specific expense.
4.  Fine$$e deletes the expense.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Fine$$e shows an error message.

      Use case resumes at step 2.

*{More to be added}*

--------------------------------------------------------------------------------------------------------------------

## **Appendix D: Non-Functional Requirements**

1.  Should work on any mainstream OS as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 expenses/incomes without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The data should be stored locally and should be in a human-editable text file.
5.  The data should not be stored in a Database Management System (DBMS).
6.  Should not require an installer.
7.  Should not depend on any remote server.

*{More to be added}*

--------------------------------------------------------------------------------------------------------------------

## **Appendix E: Glossary**

* **Expense**: A single transaction that results in a decrease in cash
* **Income**: A single transaction that results in an increase in cash
* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Savings**: Net gain (positive) or loss (negative) in cash over a set period of time
* **Data Fields**: The title, amount, date, and categories that make up an Expense or Income.

--------------------------------------------------------------------------------------------------------------------

## **Appendix F: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: &nbsp; **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.

   1. Double-click the jar file.<br>
      Expected: Shows the GUI with a set of sample data. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

### Help and switching tabs

1. View help

    1. Test case: `help`<br>
       Expected: UI switches to the user guide.

    1. Test case: `help me`<br>
       Expected: There is no change to the UI. An error message is shown as the command cannot have any arguments.

1. Switching tabs

    1. Test case: `tab 2`<br>
       Expected: UI switches to the Expenses tab.

    1. Test case: `tab overview`<br>
       Expected: UI does not switch tabs. An error message is shown as the command format is invalid.

    1. Test case: `tab 5`<br>
       Expected: UI does not switch tabs. An error message is shown as the specified tab does not exist.

### Transactions

1. Adding an expense

    1. Test case: `add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage`<br>
    Expected: An expense titled `Bubble Tea` is added with the given details. UI switches to the Expenses tab.

    1. Test case: `add-expense t/Bubble Tea a/5 c/Food c/Beverage`<br>
    Expected: An expense titled `Bubble Tea` is added with the current date and two categories. UI switches to the Expenses tab.

    1. Test case: `add-expense t/Bubble Tea`<br>
    Expected: No expense is added. An error message is shown as the command format is invalid.

    1. Test case: `add-expense t/Bubble Tea a/5.000 d/03/10/2020 c/Food & Beverage`<br>
    Expected: No expense is added. An error message is shown as the parameter format is wrong.

    1. Test case: `add-expense t/ a/5.00 d/03/10/2020 c/Food & Beverage`<br>
    Expected: No expense is added. An error message is shown as the parameter is empty.

1. Adding an income

    1. Test case: `add-income t/Internship a/560 d/03/10/2020 c/Work`<br>
    Expected: An income titled `Internship` is added with the given details. UI switches to the Incomes tab.

    1. Test case: `add-income t/Internship a/560 c/Work c/Internship`<br>
    Expected: An expense titled `Internship` is added with the current date and two categories. UI switches to the Incomes tab.

    1. Test case: `add-income t/Internship`<br>
    Expected: No expense is added. An error message is shown as the command format is invalid.

    1. Test case: `add-income t/Internship a/560 d/03-10-2020 c/Work`<br>
    Expected: No expense is added. An error message is shown as the parameter format is wrong.

    1. Test case: `add-income t/Internship a/ d/03/10/2020 c/Work`<br>
    Expected: No expense is added. An error message is shown as the parameter is empty.

1. Editing a transaction

    1. Prerequisite: UI is on Expenses or Incomes tab. List displayed contains less than 5 transactions.

    1. Test case: `edit 1 t/Taxi a/10 d/31/10/2020 c/Transport`<br>
    Expected: First transaction in the list is edited with all details changed.

    1. Test case: `edit 1 c/`<br>
    Expected: First transaction in the list is edited with all categories cleared.

    1. Test case: `edit t/Taxi`<br>
    Expected: No transaction is edited. An error message is shown as the command format is invalid.

    1. Test case: `edit 5 a/10`<br>
    Expected: No transaction is edited. An error message is shown as the index provided is invalid.

    1. Test case: `edit 1 d/10/31/2020`<br>
    Expected: No transaction is edited. An error message is shown as the parameter format is wrong.

    1. Test case: `edit 1 d/`<br>
    Expected: No transaction is edited. An error message is shown as the parameter is empty.

    1. Test case: `edit 1`<br>
    Expected: No transaction is edited. An error message is shown as at least one parameter must be supplied.

1. Deleting a transaction

    1. Prerequisite: UI is on Expenses or Incomes tab. List displayed contains less than 5 transactions.

    1. Test case: `delete 1`<br>
    Expected: First transaction in the list is deleted.

    1. Test case: `delete first`<br>
    Expected: No transaction is deleted. An error message is shown as the command format is invalid.

    1. Test case: `delete 5`<br>
    Expected: No transaction is deleted. An error message is shown as the index provided is invalid.

1. Listing transactions

    1. Test case: `ls-expense`<br>
    Expected: UI switches to Expenses tab. All expenses are listed.

    1. Test case: `ls-income`<br>
    Expected: UI switches to Incomes tab. All incomes are listed.

    1. Test case: `ls-income all`<br>
    Expected: There is no change in the UI. An error message is shown as the command cannot have any arguments.

1. Finding transactions

    1. Prerequisite: UI is on Overview, Expenses or Incomes tab.

    1. Test case: `find t/Bubble Tea`<br>
    Expected: All transactions in the displayed list containing the keyphrase `Bubble Tea` (case-insensitive) are displayed.

    1. Test case: `find af/5 c/Food`<br>
    Expected: All transactions in the displayed list with the category `Food` and amount greater than or equal to `$5.00` are displayed.

    1. Test case: `find df/01/09/2020 dt/30/09/2020`<br>
    Expected: All transactions in the displayed list in September 2020 are displayed.

    1. Test case: `find tea`<br>
    Expected: There is no change in the UI. An error message is shown as the command format is wrong.

    1. Test case: `find d/`<br>
    Expected: There is no change in the UI. An error message is shown as the parameter is empty.

    1. Test case: `find af/10 at/5`<br>
    Expected: There is no change in the UI. An error message is shown as the range provided is invalid.

### Budgeting

1. Setting expense limit

    1. Test case: `set-expense-limit a/500`<br>
    Expected: UI switches to Overview tab. Monthly expense limit is changed to `$500.00`. Remaining monthly budget is recalculated.

    1. Test case: `set-expense-limit 500`<br>
    Expected: Monthly expense limit remains unchanged. An error message is shown as the command format is invalid.

    1. Test case: `set-expense-limit a/`<br>
    Expected: Monthly expense limit remains unchanged. An error message is shown as the parameter cannot be empty.

1. Setting savings goal

    1. Test case: `set-savings-goal a/500`<br>
    Expected: UI switches to Overview tab. Monthly savings goal is changed to `$500.00`.

    1. Test case: `set-savings-goal 500`<br>
    Expected: Monthly savings goal remains unchanged. An error message is shown as the command format is invalid.

    1. Test case: `set-savings-goal a/`<br>
    Expected: Monthly savings goal remains unchanged. An error message is shown as the parameter cannot be empty.

### Bookmark transactions

1. Adding a bookmark expense

    1. Test case: `add-bookmark-expense t/Phone Bill a/24 c/Utilities`<br>
    Expected: A bookmark expense titled `Phone Bill` is added with the given details. UI switches to the Expenses tab.

    1. Test case: `add-bookmark-expense t/Phone Bill`<br>
    Expected: No bookmark expense is added. An error message is shown as the command format is invalid.

    1. Test case: `add-bookmark-expense t/Phone Bill a/24.0 c/Utilities`<br>
    Expected: No bookmark expense is added. An error message is shown as the parameter format is wrong.

    1. Test case: `add-bookmark-expense t/ a/24 c/Utilities`<br>
    Expected: No bookmark expense is added. An error message is shown as the parameter is empty.

    1. Test case: `add-bookmark-expense t/Phone Bill a/24 d/10/10/2020 c/Utilities`<br>
    Expected: No bookmark expense is added. An error message is shown as bookmark expenses should not contain dates.

    1. Test case: `add-bookmark-expense t/Phone Bill a/24 c/Utilities` (again) <br>
    Expected: No bookmark expense is added. An error message is shown as duplicate bookmark expenses cannot be added.

1. Adding a bookmark income

    1. Test case: `add-bookmark-income t/Summer Internship a/1000 c/Work`<br>
    Expected: A bookmark income titled `Summer Internship` is added with the given details. UI switches to the Income tab.

    1. Test case: `add-bookmark-income a/1000 c/Work`<br>
    Expected: No bookmark income is added. An error message is shown as the command format is invalid.

    1. Test case: `add-bookmark-income t/Summer Internship a/1000.000 c/Work`<br>
    Expected: No bookmark income is added. An error message is shown as the parameter format is wrong.

    1. Test case: `add-bookmark-income t/Summer Internship a/ c/Work`<br>
    Expected: No bookmark income is added. An error message is shown as the parameter is empty.

    1. Test case: `add-bookmark-income t/Summer Internship a/1000 d/10/10/2020 c/Work`<br>
    Expected: No bookmark income is added. An error message is shown as bookmark incomes should not contain dates.

    1. Test case: `add-bookmark-income t/Summer Internship a/1000 c/Work` (again) <br>
    Expected: No bookmark income is added. An error message is shown as duplicate bookmark incomes cannot be added.

1. Editing a bookmark transaction

    1. Prerequisite: UI is on Expenses or Incomes tab. List displayed contains less than 5 bookmark transactions.

    1. Test case: `edit-bookmark 1 t/Taxi a/10 c/Transport`<br>
    Expected: First bookmark transaction in the list is edited with all details changed.

    1. Test case: `edit-bookmark 1 c/`<br>
    Expected: First bookmark transaction in the list is edited with all categories cleared.

    1. Test case: `edit-bookmark t/Taxi`<br>
    Expected: No bookmark transaction is edited. An error message is shown as the command format is invalid.

    1. Test case: `edit-bookmark 5 a/10`<br>
    Expected: No bookmark transaction is edited. An error message is shown as the index provided is invalid.

    1. Test case: `edit-bookmark 1 a/12.345`<br>
    Expected: No bookmark transaction is edited. An error message is shown as the parameter format is wrong.

    1. Test case: `edit-bookmark 1 d/`<br>
    Expected: No bookmark transaction is edited. An error message is shown as the parameter is empty.

    1. Test case: `edit 1`<br>
    Expected: No bookmark transaction is edited. An error message is shown as at least one parameter must be supplied.

1. Deleting a bookmark transaction

    1. Prerequisite: UI is on Expenses or Incomes tab. List displayed contains less than 5 bookmark transactions.

    1. Test case: `delete-bookmark 1`<br>
    Expected: First bookmark transaction in the list is deleted.

    1. Test case: `delete-bookmark first`<br>
    Expected: No bookmark transaction is deleted. An error message is shown as the command format is invalid.

    1. Test case: `delete-bookmark 5`<br>
    Expected: No bookmark transaction is deleted. An error message is shown as the index provided is invalid.

1. Converting a bookmark transaction

    1. Prerequisite: UI is on Expenses or Incomes tab. List displayed contains less than 5 bookmark transactions.

    1. Test case: `convert-bookmark 1 d/10/10/2020`<br>
    Expected: First bookmark transaction in the list is converted to a transaction with the given date.

    1. Test case: `convert-bookmark 1 c/`<br>
    Expected: First bookmark transaction in the list is converted to a transaction with the current date.

    1. Test case: `convert-bookmark d/10/10/2020`<br>
    Expected: No bookmark transaction is converted. An error message is shown as the command format is invalid.

    1. Test case: `convert-bookmark 5 d/10/10/2020`<br>
    Expected: No bookmark transaction is converted. An error message is shown as the index provided is invalid.

    1. Test case: `convert-bookmark 1 d/10/31/2020`<br>
    Expected: No bookmark transaction is converted. An error message is shown as the parameter format is wrong.

    1. Test case: `convert-bookmark 1 d/`<br>
    Expected: No bookmark transaction is converted. An error message is shown as the parameter is empty.

### Saving data

1. Dealing with missing data files

    1. Delete the file `fine$$e.json` in the `data` folder.

    1. Launch the app by double-clicking the jar file.
    Expected: Shows the GUI with a set of sample data.

1. Dealing with corrupted data files

    1. Replace the contents of `fine$$e.json` in the `data` folder with the text `File Corrupted`.

    1. Launch the app by double-clicking the jar file.
    Expected: Shows the GUI with no data.

1. Deleting data file while app is running

    1. Launch the app by double-clicking the jar file.

    1. While the app is running, delete the file `fine$$e.json` in the `data` folder.

    1. Close the app.
    Expected: `fine$$e.json` is regenerated with the data from the app.

