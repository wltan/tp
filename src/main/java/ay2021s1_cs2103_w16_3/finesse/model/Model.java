package ay2021s1_cs2103_w16_3.finesse.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.command.history.CommandHistory;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {

    /** {@code Predicate} that always evaluate to true. */
    Predicate<Transaction> PREDICATE_SHOW_ALL_TRANSACTIONS = transaction -> true;

    /** {@code Predicate} that evaluates to true if the transaction is an {@code Expense} */
    Predicate<Transaction> PREDICATE_SHOW_ALL_EXPENSES = transaction -> transaction instanceof Expense;

    /** {@code Predicate} that evaluates to true if the transaction is an {@code Income} */
    Predicate<Transaction> PREDICATE_SHOW_ALL_INCOMES = transaction -> transaction instanceof Income;

    /** {@code Predicate} that always evaluates to true. */
    Predicate<BookmarkExpense> PREDICATE_SHOW_ALL_BOOKMARK_EXPENSES = bookmarkExpense -> true;

    Predicate<BookmarkIncome> PREDICATE_SHOW_ALL_BOOKMARK_INCOMES = bookmarkIncome -> true;
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' finance tracker file path.
     */
    Path getFinanceTrackerFilePath();

    /**
     * Sets the user prefs' finance tracker file path.
     */
    void setFinanceTrackerFilePath(Path financeTrackerFilePath);

    /**
     * Returns the command history.
     */
    CommandHistory getCommandHistory();

    /**
     * Replaces finance tracker data with the data in {@code financeTracker}.
     */
    void setFinanceTracker(ReadOnlyFinanceTracker financeTracker);

    /** Returns the FinanceTracker */
    ReadOnlyFinanceTracker getFinanceTracker();

    /**
     * Deletes the given transaction.
     * The transaction must exist in the finance tracker.
     */
    void deleteTransaction(Transaction target);

    /**
     * Deletes the given bookmark expense.
     * The bookmark expense must exist in the finance tracker.
     */
    void deleteBookmarkExpense(BookmarkExpense target);

    /**
     * Deletes the given bookmark income.
     * The bookmark income must exist in the finance tracker.
     */
    void deleteBookmarkIncome(BookmarkIncome target);

    /**
     * Adds the given expense.
     */
    void addExpense(Expense expense);

    /**
     * Adds the given income.
     */
    void addIncome(Income income);

    /**
     * Adds the given bookmark expense.
     */
    void addBookmarkExpense(BookmarkExpense bookmarkExpense);

    /**
     * Adds the given bookmark income.
     */
    void addBookmarkIncome(BookmarkIncome bookmarkIncome);

    /**
     * Replaces the given transaction {@code target} with {@code editedTransaction}.
     * {@code target} must exist in the finance tracker.
     */
    void setTransaction(Transaction target, Transaction editedTransaction);

    /**
     * Replaces the given bookmark expense {@code target} with {@code editedBookmarkExpense}.
     * {@code target} must exist in the finance tracker.
     */
    void setBookmarkExpense(BookmarkExpense target, BookmarkExpense editedBookmarkExpense);

    /**
     * Replaces the given bookmark income {@code target} with {@code editedBookmarkIncome}.
     * {@code target} must exist in the finance tracker.
     */
    void setBookmarkIncome(BookmarkIncome target, BookmarkIncome editedBookmarkIncome);

    /** Returns the monthly budget. */
    MonthlyBudget getMonthlyBudget();

    /** Sets an expense limit. */
    void setExpenseLimit(Amount limit);

    /** Sets a savings goal. */
    void setSavingsGoal(Amount goal);

    void calculateBudgetInfo();

    /** Returns an unmodifiable view of the filtered transaction list. */
    ObservableList<Transaction> getFilteredTransactionList();

    /** Returns an unmodifiable view of the filtered expense list. */
    ObservableList<Expense> getFilteredExpenseList();

    /** Returns an unmodifiable view of the filtered income list. */
    ObservableList<Income> getFilteredIncomeList();

    /** Returns an unmodifiable view of the filtered bookmark expense list. */
    ObservableList<BookmarkExpense> getFilteredBookmarkExpenseList();

    /** Returns an unmodifiable view of the filtered bookmark income list. */
    ObservableList<BookmarkIncome> getFilteredBookmarkIncomeList();

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);

    /**
     * Updates the filter of the filtered transaction list to filter by the given list of {@code predicates}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(List<Predicate<Transaction>> predicates);

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(Predicate<Transaction> predicate);

    /**
     * Updates the filter of the filtered expense list to filter by the given list of {@code predicates}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(List<Predicate<Transaction>> predicates);

    /**
     * Updates the filter of the filtered income list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIncomeList(Predicate<Transaction> predicate);

    /**
     * Updates the filter of the filtered income list to filter by the given list of {@code predicates}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIncomeList(List<Predicate<Transaction>> predicates);

    /**
     * Updates the filter of the filtered bookmark expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookmarkExpenseList(Predicate<BookmarkExpense> predicate);

    /**
     * Updates the filter of the filtered bookmark income list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookmarkIncomeList(Predicate<BookmarkIncome> predicate);
}
