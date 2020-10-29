package ay2021s1_cs2103_w16_3.finesse.model;

import static ay2021s1_cs2103_w16_3.finesse.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.TUITION_FEES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.TitleContainsKeywordsPredicate;
import ay2021s1_cs2103_w16_3.finesse.testutil.FinanceTrackerBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;
import javafx.collections.ObservableList;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new FinanceTracker(), new FinanceTracker(modelManager.getFinanceTracker()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setFinanceTrackerFilePath(Paths.get("finance/tracker/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4, true));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setFinanceTrackerFilePath(Paths.get("new/finance/tracker/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4, true);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setFinanceTrackerFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setFinanceTrackerFilePath(null));
    }

    @Test
    public void setFinanceTrackerFilePath_validPath_setsFinanceTrackerFilePath() {
        Path path = Paths.get("finance/tracker/file/path");
        modelManager.setFinanceTrackerFilePath(path);
        assertEquals(path, modelManager.getFinanceTrackerFilePath());
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTransactionList().remove(0));
    }

    @Test
    public void getFilteredExpenseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredExpenseList().remove(0));
    }

    @Test
    public void getFilteredIncomeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredIncomeList().remove(0));
    }

    @Test
    public void getFilteredBookmarkExpenseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> modelManager.getFilteredBookmarkExpenseList().remove(0));
    }

    @Test
    public void getFilteredBookmarkIncomeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> modelManager.getFilteredBookmarkIncomeList().remove(0));
    }

    @Test
    public void getFilteredTransactionLists_addTransactions_updatesList() {
        ObservableList<Expense> expenseList = modelManager.getFilteredExpenseList();
        ObservableList<Income> incomeList = modelManager.getFilteredIncomeList();
        modelManager.addExpense(new TransactionBuilder().buildExpense());
        assertEquals(1, expenseList.size());
        assertEquals(0, incomeList.size());
        modelManager.addIncome(new TransactionBuilder().buildIncome());
        assertEquals(1, expenseList.size());
        assertEquals(1, incomeList.size());
    }

    @Test
    public void getFilteredTransactionLists_deleteTransaction_updatesList() {
        Expense e = new TransactionBuilder().buildExpense();
        modelManager.addExpense(e);
        ObservableList<Expense> expenseList = modelManager.getFilteredExpenseList();
        assertEquals(1, expenseList.size());
        modelManager.deleteTransaction(e);
        assertEquals(0, expenseList.size());
    }

    @Test
    public void getFilteredTransactionLists_editTransaction_updatesList() {
        Income i = new TransactionBuilder().buildIncome();
        Income updated = new TransactionBuilder().withTitle("foo").buildIncome();
        assertNotEquals(i.getTitle(), updated.getTitle()); // ensure no conflict
        modelManager.addIncome(i);
        ObservableList<Income> incomeList = modelManager.getFilteredIncomeList();
        assertEquals(1, incomeList.size());
        modelManager.setTransaction(i, updated);
        assertFalse(incomeList.contains(i));
        assertTrue(incomeList.contains(updated));
    }

    @Test
    public void equals() {
        FinanceTracker financeTracker = new FinanceTrackerBuilder()
                .withTransaction(BUBBLE_TEA).withTransaction(TUITION_FEES)
                .withExpense(new TransactionBuilder(BUBBLE_TEA).buildExpense())
                .withExpense(new TransactionBuilder(TUITION_FEES).buildExpense())
                .withIncome(new TransactionBuilder(BUBBLE_TEA).buildIncome())
                .withIncome(new TransactionBuilder(TUITION_FEES).buildIncome()).build();
        FinanceTracker differentFinanceTracker = new FinanceTracker();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(financeTracker, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(financeTracker, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different financeTracker -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentFinanceTracker, userPrefs)));

        String[] keywords = BUBBLE_TEA.getTitle().toString().split("\\s+");

        // different filteredTransactionList -> returns false
        modelManager.updateFilteredTransactionList(new TitleContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(financeTracker, userPrefs)));

        // different filteredExpenseList -> returns false
        modelManager.updateFilteredExpenseList(new TitleContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(financeTracker, userPrefs)));

        // different filteredIncomeList -> returns false
        modelManager.updateFilteredIncomeList(new TitleContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(financeTracker, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFinanceTrackerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(financeTracker, differentUserPrefs)));
    }
}
