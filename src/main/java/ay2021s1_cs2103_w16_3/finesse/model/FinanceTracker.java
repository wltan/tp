package ay2021s1_cs2103_w16_3.finesse.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpenseList;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncomeList;
import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.TransactionList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the finance tracker level
 */
public class FinanceTracker implements ReadOnlyFinanceTracker {

    private static final int NUM_OF_MONTHS = 3;

    private final TransactionList transactions;
    private final BookmarkExpenseList bookmarkExpenses;
    private final BookmarkIncomeList bookmarkIncomes;
    private final MonthlyBudget monthlyBudget;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        transactions = new TransactionList();
        bookmarkExpenses = new BookmarkExpenseList();
        bookmarkIncomes = new BookmarkIncomeList();
        monthlyBudget = new MonthlyBudget();
    }

    public FinanceTracker() {}

    /**
     * Creates an FinanceTracker using the Transactions in the {@code toBeCopied}
     */
    public FinanceTracker(ReadOnlyFinanceTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the transaction list with {@code transactions}.
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setTransactions(transactions);
    }

    /**
     * Replaces the contents of the bookmark expense list with {@code bookmarkExpens}.
     */
    public void setBookmarkExpenses(List<BookmarkExpense> bookmarkExpens) {
        this.bookmarkExpenses.setBookmarkExpenses(bookmarkExpens);
    }

    /**
     * Replaces the contents of the bookmark incomes list with {@code bookmarkIncomes}.
     */
    public void setBookmarkIncomes(List<BookmarkIncome> bookmarkIncomes) {
        this.bookmarkIncomes.setBookmarkIncomes(bookmarkIncomes);
    }

    /**
     * Resets the existing data of this {@code FinanceTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyFinanceTracker newData) {
        requireNonNull(newData);

        setTransactions(newData.getTransactionList());
        setBookmarkExpenses(newData.getBookmarkExpenseList());
        setBookmarkIncomes(newData.getBookmarkIncomeList());
        setMonthlyBudget(newData.getMonthlyBudget());
        calculateBudgetInfo();
    }

    //// transaction-level operations

    /**
     * Adds a transaction to the finance tracker.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Adds a bookmark expense to the finance tracker.
     */
    public void addBookmarkExpense(BookmarkExpense bookmarkExpense) {
        bookmarkExpenses.add(bookmarkExpense);
    }

    /**
     * Adds a bookmark income to the finance tracker.
     */
    public void addBookmarkIncome(BookmarkIncome bookmarkIncome) {
        bookmarkIncomes.add(bookmarkIncome);
    }

    /**
     * Replaces the given transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the finance tracker.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireNonNull(editedTransaction);

        transactions.setTransaction(target, editedTransaction);
    }

    /**
     * Replaces the given bookmark expense {@code target} in the list with {@code editedBookmarkExpense}.
     * {@code target} must exist in the bookmark expense list.
     */
    public void setBookmarkExpense(BookmarkExpense target, BookmarkExpense editedBookmarkExpense) {
        requireNonNull(editedBookmarkExpense);

        bookmarkExpenses.setBookmarkExpense(target, editedBookmarkExpense);
    }

    /**
     * Replaces the given bookmark income {@code target} in the list with {@code editedBookmarkIncome}.
     * {@code target} must exist in the bookmark bookmark list.
     */
    public void setBookmarkIncome(BookmarkIncome target, BookmarkIncome editedBookmarkIncome) {
        requireNonNull(editedBookmarkIncome);

        bookmarkIncomes.setBookmarkIncome(target, editedBookmarkIncome);
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the finance tracker.
     */
    public void removeTransaction(Transaction key) {
        transactions.remove(key);
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the finance tracker.
     */
    public void removeBookmarkExpense(BookmarkExpense key) {
        bookmarkExpenses.remove(key);
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the finance tracker.
     */
    public void removeBookmarkIncome(BookmarkIncome key) {
        bookmarkIncomes.remove(key);
    }

    //// budget-level operations

    public void setExpenseLimit(Amount limit) {
        monthlyBudget.setMonthlyExpenseLimit(limit);
    }

    public void setSavingsGoal(Amount goal) {
        monthlyBudget.setMonthlySavingsGoal(goal);
    }

    public void setMonthlyBudget(MonthlyBudget budget) {
        monthlyBudget.setMonthlyExpenseLimit(budget.getMonthlyExpenseLimit().get());
        monthlyBudget.setMonthlySavingsGoal(budget.getMonthlySavingsGoal().get());
    }

    public void calculateBudgetInfo() {
        monthlyBudget.calculateBudgetInfo(transactions, NUM_OF_MONTHS);
    }

    //// util methods

    @Override
    public String toString() {
        return transactions.asUnmodifiableObservableList().size() + " transactions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        transactions.forEach(t -> {
            if (t instanceof Expense) {
                expenses.add((Expense) t);
            }
        });
        return FXCollections.unmodifiableObservableList(expenses);
    }

    @Override
    public ObservableList<Income> getIncomeList() {
        ObservableList<Income> incomes = FXCollections.observableArrayList();
        transactions.forEach(t -> {
            if (t instanceof Income) {
                incomes.add((Income) t);
            }
        });
        return FXCollections.unmodifiableObservableList(incomes);
    }

    @Override
    public ObservableList<BookmarkExpense> getBookmarkExpenseList() {
        return bookmarkExpenses.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<BookmarkIncome> getBookmarkIncomeList() {
        return bookmarkIncomes.asUnmodifiableObservableList();
    }

    public MonthlyBudget getMonthlyBudget() {
        return monthlyBudget;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinanceTracker // instanceof handles nulls
                && transactions.equals(((FinanceTracker) other).transactions));
    }

    @Override
    public int hashCode() {
        return transactions.hashCode();
    }

}
