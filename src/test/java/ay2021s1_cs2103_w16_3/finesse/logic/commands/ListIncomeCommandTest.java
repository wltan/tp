package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListIncomeCommand.
 */
public class ListIncomeCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_showsIncomeOnly() {
        expectedModel.updateFilteredTransactionList(Income.PREDICATE_SHOW_ALL_INCOME);
        assertCommandSuccess(new ListIncomeCommand(), model, ListIncomeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_hasSomeIncome() {
        model.addTransaction(new TransactionBuilder().buildIncome());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredTransactionList(transaction -> transaction instanceof Income);
        assertCommandSuccess(new ListIncomeCommand(), model, ListIncomeCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(model.getFilteredTransactionList().size(), 1);
    }

    @Test
    public void execute_hasSomeNonIncome() {
        model.addTransaction(new TransactionBuilder().build()); // TODO replace with expense when implemented
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredTransactionList(transaction -> transaction instanceof Income);
        assertCommandSuccess(new ListIncomeCommand(), model, ListIncomeCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(model.getFilteredTransactionList().size(), 0);
    }

}
