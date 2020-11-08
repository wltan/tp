package ay2021s1_cs2103_w16_3.finesse.model.budget;

import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount.CalculatedAmount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.TransactionList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the monthly budget in the finance tracker.
 */
public class MonthlyBudget {
    private static final String[] MONTHS = new DateFormatSymbols().getMonths();
    private static final int NUM_OF_MONTHS = 12;

    private ObjectProperty<Amount> monthlyExpenseLimit;
    private ObjectProperty<Amount> monthlySavingsGoal;
    private ObjectProperty<CalculatedAmount> remainingBudget;
    private ObjectProperty<CalculatedAmount> currentSavings;
    private ObservableList<CalculatedAmount> monthlyExpenses;
    private ObservableList<CalculatedAmount> monthlyIncomes;
    private ObservableList<CalculatedAmount> monthlySavings;
    private ObservableList<String> months;

    @FunctionalInterface
    public interface ChangeListener extends Runnable {

    }

    private Collection<ChangeListener> listeners;

    /**
     * Creates a {@code MonthlyBudget} with an expense limit and savings goal of $0.
     */
    public MonthlyBudget() {
        monthlyExpenseLimit = new SimpleObjectProperty<>(Amount.ZERO_AMOUNT);
        monthlySavingsGoal = new SimpleObjectProperty<>(Amount.ZERO_AMOUNT);
        remainingBudget = new SimpleObjectProperty<>(new CalculatedAmount());
        currentSavings = new SimpleObjectProperty<>(new CalculatedAmount());
        monthlyExpenses = FXCollections.observableArrayList();
        monthlyIncomes = FXCollections.observableArrayList();
        monthlySavings = FXCollections.observableArrayList();
        months = FXCollections.observableArrayList();
        listeners = new ArrayList<>();
    }

    public ObjectProperty<Amount> getMonthlyExpenseLimit() {
        return monthlyExpenseLimit;
    }

    public void setMonthlyExpenseLimit(Amount limit) {
        monthlyExpenseLimit.setValue(limit);
    }

    public ObjectProperty<Amount> getMonthlySavingsGoal() {
        return monthlySavingsGoal;
    }

    public void setMonthlySavingsGoal(Amount goal) {
        monthlySavingsGoal.setValue(goal);
    }

    public ObjectProperty<CalculatedAmount> getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(CalculatedAmount budget) {
        remainingBudget.setValue(budget);
    }

    public ObjectProperty<CalculatedAmount> getCurrentSavings() {
        return currentSavings;
    }

    public void setCurrentSavings(CalculatedAmount savings) {
        currentSavings.setValue(savings);
    }

    public ObservableList<CalculatedAmount> getMonthlyExpenses() {
        return FXCollections.unmodifiableObservableList(monthlyExpenses);
    }

    public ObservableList<CalculatedAmount> getMonthlyIncomes() {
        return FXCollections.unmodifiableObservableList(monthlyIncomes);
    }

    public ObservableList<CalculatedAmount> getMonthlySavings() {
        return FXCollections.unmodifiableObservableList(monthlySavings);
    }

    public ObservableList<String> getMonths() {
        return FXCollections.unmodifiableObservableList(months);
    }

    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    private void callChangeListeners() {
        listeners.forEach(ChangeListener::run);
    }

    /**
     * Calculates all information related to the user's savings.
     * These are:
     * 1. Expenses for the past number of months specified by the user
     * 2. Incomes for the past number of months specified by user
     * 3. Savings for the past number of months specified by user
     * 4. The months to be displayed according to the number of months specified by user
     *
     * @param transactions The current list of transactions.
     * @param numOfMonths The number of months of data the user wishes to display.
     */
    public void calculateBudgetInfo(TransactionList transactions, int numOfMonths) {
        this.monthlyExpenses.clear();
        this.monthlyIncomes.clear();
        this.months.clear();

        YearMonth today = YearMonth.now();
        int thisMonthValue = today.getMonthValue();

        Function<Transaction, Integer> monthsBeforeToday = transaction -> (int) ChronoUnit.MONTHS.between(
                YearMonth.from(transaction.getDateValue()), today);

        Map<Class<? extends Transaction>, List<Transaction>> filteredAndSplit =
                StreamSupport.stream(transactions.spliterator(), false)
                        .filter(transaction -> monthsBeforeToday.apply(transaction) < numOfMonths)
                        .collect(Collectors.groupingBy(Transaction::getClass));

        Function<Class<? extends Transaction>, Map<Integer, List<Transaction>>> groupByMonth = transactionType ->
                filteredAndSplit.getOrDefault(transactionType, Collections.emptyList()).stream()
                        .collect(Collectors.groupingBy(monthsBeforeToday));

        Function<Map<Integer, List<Transaction>>, List<CalculatedAmount>> amountSum = transactionsByMonth ->
                IntStream.range(0, numOfMonths)
                        .mapToObj(month -> transactionsByMonth.getOrDefault(month, Collections.emptyList()))
                        .map(transactionsInMonth -> transactionsInMonth.stream()
                                .map(Transaction::getAmount)
                                .map(CalculatedAmount::new)
                                .reduce(new CalculatedAmount(), (x, y) -> y.add(x)))
                        .collect(Collectors.toUnmodifiableList());

        Map<Integer, List<Transaction>> expensesByMonth = groupByMonth.apply(Expense.class);
        Map<Integer, List<Transaction>> incomesByMonth = groupByMonth.apply(Income.class);

        List<CalculatedAmount> monthlyExpenses = amountSum.apply(expensesByMonth);
        List<CalculatedAmount> monthlyIncomes = amountSum.apply(incomesByMonth);

        this.monthlyExpenses.addAll(monthlyExpenses);
        this.monthlyIncomes.addAll(monthlyIncomes);

        IntStream.range(0, numOfMonths)
                .map(i -> numOfMonths - i)
                .map(i -> thisMonthValue - i)
                .map(i -> i < 0 ? NUM_OF_MONTHS - i : i)
                .mapToObj(i -> MONTHS[i])
                .forEach(months::add);

        calculateBudget();
        calculateSavings();

        callChangeListeners();
    }

    /**
     * Calculates the user's remaining budget for the month
     * by deducting this month's expenses from the monthly expense limit.
     * If the result is positive, the result is set as the remaining budget.
     * If the result is negative, the magnitude of the result is set as the budget deficit.
     */
    private void calculateBudget() {
        setRemainingBudget(new CalculatedAmount(monthlyExpenseLimit.get())
                .subtract(monthlyExpenses.get(monthlyExpenses.size() - 1)));
    }

    /**
     * Calculates the user's current savings for the month
     * by deducting this month's expenses from this month's incomes.
     * If the result is positive, the result is set as the current savings.
     * If the result is negative, the magnitude of the result is set as the savings deficit.
     */
    private void calculateSavings() {
        monthlySavings.clear();
        for (int i = 0; i < monthlyIncomes.size(); i++) {
            CalculatedAmount savings = monthlyIncomes.get(i).subtract(monthlyExpenses.get(i));
            monthlySavings.add(savings);
        }
        setCurrentSavings(monthlySavings.get(monthlyIncomes.size() - 1));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MonthlyBudget)) {
            return false;
        }

        MonthlyBudget otherMonthlyBudget = (MonthlyBudget) other;
        return monthlyExpenseLimit.getValue().equals(otherMonthlyBudget.monthlyExpenseLimit.getValue())
                && monthlySavingsGoal.getValue().equals(otherMonthlyBudget.monthlySavingsGoal.getValue())
                && remainingBudget.getValue().equals(otherMonthlyBudget.remainingBudget.getValue())
                && currentSavings.getValue().equals(otherMonthlyBudget.currentSavings.getValue())
                && monthlyExpenses.equals(otherMonthlyBudget.monthlyExpenses)
                && monthlyIncomes.equals(otherMonthlyBudget.monthlyIncomes)
                && monthlySavings.equals(otherMonthlyBudget.monthlySavings)
                && months.equals(otherMonthlyBudget.months);
    }
}
