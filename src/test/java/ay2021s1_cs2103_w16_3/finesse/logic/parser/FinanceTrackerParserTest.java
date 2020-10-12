package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ClearCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ExitCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.HelpCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListTransactionCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.TabCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.TitleContainsKeywordsPredicate;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditTransactionDescriptorBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionUtil;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;

public class FinanceTrackerParserTest {

    private final FinanceTrackerParser parser = new FinanceTrackerParser();
    private final OverviewUiStateStub overviewUiStateStub = new OverviewUiStateStub();
    private final IncomeUiStateStub incomeUiStateStub = new IncomeUiStateStub();
    private final ExpensesUiStateStub expensesUiStateStub = new ExpensesUiStateStub();
    private final AnalyticsUiStateStub analyticsUiStateStub = new AnalyticsUiStateStub();

    @Test
    public void parseCommand_addWhenOverviewTab() {
        Transaction transaction = new TransactionBuilder().build();
        assertThrows(ParseException.class, () -> parser.parseCommand(
                TransactionUtil.getAddCommand(transaction), overviewUiStateStub));
    }

    @Test
    public void parseCommand_addWhenIncomeTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddCommand(income), incomeUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addWhenExpensesTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
                (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddCommand(expense), expensesUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addWhenAnalyticsTab() {
        Transaction transaction = new TransactionBuilder().build();
        assertThrows(ParseException.class, () -> parser.parseCommand(
                TransactionUtil.getAddCommand(transaction), analyticsUiStateStub));
    }

    @Test
    public void parseCommand_addExpenseWhenOverviewTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
            (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddExpenseCommand(expense),
            overviewUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addExpenseWhenIncomeTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
                (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddExpenseCommand(expense),
                        incomeUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addExpenseWhenExpensesTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
                (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddExpenseCommand(expense),
                        expensesUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addExpenseWhenAnalyticsTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
                (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddExpenseCommand(expense),
                        analyticsUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addIncomeWhenOverviewTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddIncomeCommand(income),
                overviewUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addIncomeWhenIncomeTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddIncomeCommand(income),
                        incomeUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addIncomeWhenExpensesTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddIncomeCommand(income),
                        expensesUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addIncomeWhenAnalyticsTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddIncomeCommand(income),
                        analyticsUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_clearWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, overviewUiStateStub) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, incomeUiStateStub) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3",
                incomeUiStateStub) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, expensesUiStateStub) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3",
                expensesUiStateStub) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, analyticsUiStateStub) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3",
                analyticsUiStateStub) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteWhenOverviewTab() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TRANSACTION.getOneBased(), overviewUiStateStub));
    }

    @Test
    public void parseCommand_deleteWhenIncomeTab() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TRANSACTION.getOneBased(), incomeUiStateStub);
        assertEquals(new DeleteCommand(INDEX_FIRST_TRANSACTION), command);
    }

    @Test
    public void parseCommand_deleteWhenExpensesTab() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TRANSACTION.getOneBased(), expensesUiStateStub);
        assertEquals(new DeleteCommand(INDEX_FIRST_TRANSACTION), command);
    }

    @Test
    public void parseCommand_deleteWhenAnalyticsTab() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TRANSACTION.getOneBased(), analyticsUiStateStub));
    }

    @Test
    public void parseCommand_editWhenOverviewTab() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(transaction).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TRANSACTION.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), overviewUiStateStub);
        assertEquals(new EditCommand(INDEX_FIRST_TRANSACTION, descriptor), command);
    }

    @Test
    public void parseCommand_editWhenIncomeTab() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(transaction).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TRANSACTION.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), incomeUiStateStub);
        assertEquals(new EditCommand(INDEX_FIRST_TRANSACTION, descriptor), command);
    }

    @Test
    public void parseCommand_editWhenExpensesTab() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(transaction).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TRANSACTION.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), expensesUiStateStub);
        assertEquals(new EditCommand(INDEX_FIRST_TRANSACTION, descriptor), command);
    }

    @Test
    public void parseCommand_editWhenAnalyticsTab() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(transaction).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TRANSACTION.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), analyticsUiStateStub);
        assertEquals(new EditCommand(INDEX_FIRST_TRANSACTION, descriptor), command);
    }

    @Test
    public void parseCommand_exitWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, overviewUiStateStub) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, incomeUiStateStub) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3",
                incomeUiStateStub) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, expensesUiStateStub) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3",
                expensesUiStateStub) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, analyticsUiStateStub) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3",
                analyticsUiStateStub) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findWhenOverviewTab() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")), overviewUiStateStub);
        assertEquals(new FindCommand(new TitleContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findWhenIncomeTab() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")), incomeUiStateStub);
        assertEquals(new FindCommand(new TitleContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findWhenExpensesTab() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")), expensesUiStateStub);
        assertEquals(new FindCommand(new TitleContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findWhenAnalyticsTab() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TRANSACTION.getOneBased(), analyticsUiStateStub));
    }

    @Test
    public void parseCommand_helpWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, overviewUiStateStub) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, incomeUiStateStub) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3",
                incomeUiStateStub) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, expensesUiStateStub) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3",
                expensesUiStateStub) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, analyticsUiStateStub) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3",
                analyticsUiStateStub) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListTransactionCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, incomeUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3",
                incomeUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, expensesUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3",
                expensesUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listWhenAnalyticsTab() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(ListCommand.COMMAND_WORD, analyticsUiStateStub));
    }

    @Test
    public void parseCommand_listTransactionWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListTransactionCommand);
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listTransactionWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD, incomeUiStateStub)
                instanceof ListTransactionCommand);
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD + " 3",
                incomeUiStateStub) instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listTransactionWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD, expensesUiStateStub)
                instanceof ListTransactionCommand);
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD + " 3",
                expensesUiStateStub) instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listTransactionWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD, analyticsUiStateStub)
                instanceof ListTransactionCommand);
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD + " 3",
                analyticsUiStateStub) instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listExpenseWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listExpenseWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, incomeUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD + " 3",
                incomeUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listExpenseWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, expensesUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD + " 3",
                expensesUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listExpenseWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, analyticsUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD + " 3",
                analyticsUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listIncomeWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listIncomeWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD, incomeUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD + " 3",
                incomeUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listIncomeWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD, expensesUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD + " 3",
                expensesUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listIncomeWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD, analyticsUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD + " 3",
                analyticsUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_tabWhenOverviewTab() throws Exception {
        TabCommand command = (TabCommand) parser.parseCommand(
                TabCommand.COMMAND_WORD + " 1", overviewUiStateStub);
        assertEquals(new TabCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_tabWhenIncomeTab() throws Exception {
        TabCommand command = (TabCommand) parser.parseCommand(
                TabCommand.COMMAND_WORD + " 1", incomeUiStateStub);
        assertEquals(new TabCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_tabWhenExpensesTab() throws Exception {
        TabCommand command = (TabCommand) parser.parseCommand(
                TabCommand.COMMAND_WORD + " 1", expensesUiStateStub);
        assertEquals(new TabCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_tabWhenAnalyticsTab() throws Exception {
        TabCommand command = (TabCommand) parser.parseCommand(
                TabCommand.COMMAND_WORD + " 1", analyticsUiStateStub);
        assertEquals(new TabCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_unrecognisedInputWhenOverviewTab_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", overviewUiStateStub));
    }

    @Test
    public void parseCommand_unrecognisedInputWhenIncomeTab_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", incomeUiStateStub));
    }

    @Test
    public void parseCommand_unrecognisedInputWhenExpensesTab_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", expensesUiStateStub));
    }

    @Test
    public void parseCommand_unrecognisedInputWhenAnalyticsTab_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", analyticsUiStateStub));
    }

    @Test
    public void parseCommand_unknownCommandWhenOverviewTab_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand", overviewUiStateStub));
    }

    @Test
    public void parseCommand_unknownCommandWhenIncomeTab_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand", incomeUiStateStub));
    }

    @Test
    public void parseCommand_unknownCommandWhenExpensesTab_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand", expensesUiStateStub));
    }

    @Test
    public void parseCommand_unknownCommandWhenAnalyticsTab_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand", analyticsUiStateStub));
    }

    /**
     * A default {@code UiState} stub that has all of the methods failing.
     */
    private static class UiStateStub extends UiState {
        @Override
        public Tab getCurrentTab() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentTab(Tab currentTab) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Overview' tab as the current tab.
     */
    public static class OverviewUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.OVERVIEW;
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Income' tab as the current tab.
     */
    public static class IncomeUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.INCOME;
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Expenses' tab as the current tab.
     */
    public static class ExpensesUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.EXPENSES;
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Analytics' tab as the current tab.
     */
    public static class AnalyticsUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.ANALYTICS;
        }
    }
}
