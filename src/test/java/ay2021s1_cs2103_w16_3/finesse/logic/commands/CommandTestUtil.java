package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditBookmarkTransactionDescriptorBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditTransactionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TITLE_BUBBLE_TEA = "Bubble Tea";
    public static final String VALID_TITLE_INTERNSHIP = "Internship";
    public static final String VALID_TITLE_SPOTIFY_SUBSCRIPTION = "Spotify Subscription";
    public static final String VALID_TITLE_PHONE_BILL = "Phone Bill";
    public static final String VALID_TITLE_PART_TIME = "Part Time";
    public static final String VALID_AMOUNT_BUBBLE_TEA = "$4.80";
    public static final String VALID_AMOUNT_INTERNSHIP = "$560";
    public static final String VALID_AMOUNT_SPOTIFY_SUBSCRIPTION = "$9.90";
    public static final String VALID_AMOUNT_PHONE_BILL = "$60";
    public static final String VALID_AMOUNT_PART_TIME = "$450";
    public static final String VALID_DATE_BUBBLE_TEA = "14/10/2020";
    public static final String VALID_DATE_INTERNSHIP = "06/10/2020";
    public static final String VALID_DATE_SPOTIFY_SUBSCRIPTION = "08/10/2020";
    public static final String VALID_CATEGORY_FOOD_BEVERAGE = "Food & Beverage";
    public static final String VALID_CATEGORY_WORK = "Work";
    public static final String VALID_CATEGORY_UTILITIES = "Utilities";
    public static final String VALID_CATEGORY_MISCELLANEOUS = "Miscellaneous";

    public static final String TITLE_DESC_BUBBLE_TEA = " " + PREFIX_TITLE + VALID_TITLE_BUBBLE_TEA;
    public static final String TITLE_DESC_INTERNSHIP = " " + PREFIX_TITLE + VALID_TITLE_INTERNSHIP;
    public static final String TITLE_DESC_PHONE_BILL = " " + PREFIX_TITLE + VALID_TITLE_PHONE_BILL;
    public static final String TITLE_DESC_SPOTIFY_SUBSCRIPTION = " " + PREFIX_TITLE + VALID_TITLE_SPOTIFY_SUBSCRIPTION;
    public static final String TITLE_DESC_PART_TIME = " " + PREFIX_TITLE + VALID_TITLE_PART_TIME;
    public static final String AMOUNT_DESC_BUBBLE_TEA = " " + PREFIX_AMOUNT + VALID_AMOUNT_BUBBLE_TEA;
    public static final String AMOUNT_DESC_INTERNSHIP = " " + PREFIX_AMOUNT + VALID_AMOUNT_INTERNSHIP;
    public static final String AMOUNT_DESC_PHONE_BILL = " " + PREFIX_AMOUNT + VALID_AMOUNT_PHONE_BILL;
    public static final String AMOUNT_DESC_SPOTIFY_SUBSCRIPTION = " " + PREFIX_AMOUNT
            + VALID_AMOUNT_SPOTIFY_SUBSCRIPTION;
    public static final String AMOUNT_DESC_PART_TIME = " " + PREFIX_AMOUNT + VALID_AMOUNT_PART_TIME;
    public static final String DATE_DESC_BUBBLE_TEA = " " + PREFIX_DATE + VALID_DATE_BUBBLE_TEA;
    public static final String DATE_DESC_INTERNSHIP = " " + PREFIX_DATE + VALID_DATE_INTERNSHIP;
    public static final String DATE_DESC_SPOTIFY_SUBSCRIPTION = " " + PREFIX_DATE + VALID_DATE_SPOTIFY_SUBSCRIPTION;
    public static final String CATEGORY_DESC_FOOD_BEVERAGE = " " + PREFIX_CATEGORY + VALID_CATEGORY_FOOD_BEVERAGE;
    public static final String CATEGORY_DESC_WORK = " " + PREFIX_CATEGORY + VALID_CATEGORY_WORK;
    public static final String CATEGORY_DESC_UTILITIES = " " + PREFIX_CATEGORY
            + VALID_CATEGORY_UTILITIES;
    public static final String CATEGORY_DESC_MISCELLANEOUS = " " + PREFIX_CATEGORY
            + VALID_CATEGORY_MISCELLANEOUS;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "Movie\u2416"; // 'SYN' not allowed in titles
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "911a"; // 'a' not allowed in amounts
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "bobby!zijian"; // only numbers and '/' allowed
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + "CS2103T\u2416";
    // 'SYN' not allowed in categories

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTransactionDescriptor DESC_BUBBLE_TEA;
    public static final EditCommand.EditTransactionDescriptor DESC_INTERNSHIP;
    public static final EditBookmarkTransactionDescriptor DESC_SPOTIFY_SUBSCRIPTION;
    public static final EditBookmarkTransactionDescriptor DESC_PHONE_BILL;

    static {
        DESC_BUBBLE_TEA = new EditTransactionDescriptorBuilder().withTitle(VALID_TITLE_BUBBLE_TEA)
                .withAmount(VALID_AMOUNT_BUBBLE_TEA).withDate(VALID_DATE_BUBBLE_TEA)
                .withCategories(VALID_CATEGORY_FOOD_BEVERAGE).build();
        DESC_INTERNSHIP = new EditTransactionDescriptorBuilder().withTitle(VALID_TITLE_INTERNSHIP)
                .withAmount(VALID_AMOUNT_INTERNSHIP).withDate(VALID_DATE_INTERNSHIP)
                .withCategories(VALID_CATEGORY_WORK, VALID_CATEGORY_FOOD_BEVERAGE).build();
        DESC_SPOTIFY_SUBSCRIPTION = new EditBookmarkTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).withAmount(VALID_AMOUNT_SPOTIFY_SUBSCRIPTION)
                .withCategories(VALID_CATEGORY_MISCELLANEOUS).build();
        DESC_PHONE_BILL = new EditBookmarkTransactionDescriptorBuilder().withTitle(VALID_TITLE_PHONE_BILL)
                .withAmount(VALID_AMOUNT_PHONE_BILL).withCategories(VALID_CATEGORY_UTILITIES).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel, boolean isCalculateBudgetInfo) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, isCalculateBudgetInfo);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the finance tracker, filtered transaction list, filtered bookmark expense list, filtered bookmark income list
     * and selected transaction in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FinanceTracker expectedFinanceTracker = new FinanceTracker(actualModel.getFinanceTracker());
        List<Transaction> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTransactionList());
        List<BookmarkExpense> expectedFilteredBookmarkExpenseList =
                new ArrayList<>(actualModel.getFilteredBookmarkExpenseList());
        List<BookmarkIncome> expectedFilteredBookmarkIncomeList =
                new ArrayList<>(actualModel.getFilteredBookmarkIncomeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedFinanceTracker, actualModel.getFinanceTracker());
        assertEquals(expectedFilteredList, actualModel.getFilteredTransactionList());
        assertEquals(expectedFilteredBookmarkExpenseList, actualModel.getFilteredBookmarkExpenseList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the transaction at the given {@code targetIndex} in the
     * {@code model}'s finance tracker.
     */
    public static void showTransactionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTransactionList().size());

        Transaction transaction = model.getFilteredTransactionList().get(targetIndex.getZeroBased());
        final String[] splitTitle = transaction.getTitle().toString().split("\\s+");
        model.updateFilteredTransactionList(t -> t == transaction);

        assertEquals(1, model.getFilteredTransactionList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the expense at the given {@code targetIndex} in the
     * {@code model}'s finance tracker.
     */
    public static void showExpenseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExpenseList().size());

        Expense expense = model.getFilteredExpenseList().get(targetIndex.getZeroBased());
        final String[] splitTitle = expense.getTitle().toString().split("\\s+");
        model.updateFilteredExpenseList(e -> e == expense);

        assertEquals(1, model.getFilteredExpenseList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the bookmark expense at the given {@code targetIndex} in the
     * {@code model}'s finance tracker.
     */
    public static void showBookmarkExpenseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBookmarkExpenseList().size());

        BookmarkExpense bookmarkExpense = model.getFilteredBookmarkExpenseList().get(targetIndex.getZeroBased());
        final String[] splitTitle = bookmarkExpense.getTitle().toString().split("\\s+");
        model.updateFilteredBookmarkExpenseList(e -> e == bookmarkExpense);

        assertEquals(1, model.getFilteredBookmarkExpenseList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the bookmark income at the given {@code targetIndex} in the
     * {@code model}'s finance tracker.
     */
    public static void showBookmarkIncomeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBookmarkIncomeList().size());

        BookmarkIncome bookmarkIncome = model.getFilteredBookmarkIncomeList().get(targetIndex.getZeroBased());
        final String[] splitTitle = bookmarkIncome.getTitle().toString().split("\\s+");
        model.updateFilteredBookmarkIncomeList(e -> e == bookmarkIncome);

        assertEquals(1, model.getFilteredBookmarkIncomeList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the income at the given {@code targetIndex} in the
     * {@code model}'s finance tracker.
     */
    public static void showIncomeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredIncomeList().size());

        Income income = model.getFilteredIncomeList().get(targetIndex.getZeroBased());
        final String[] splitTitle = income.getTitle().toString().split("\\s+");
        model.updateFilteredIncomeList(i -> i == income);

        assertEquals(1, model.getFilteredIncomeList().size());
    }
}
