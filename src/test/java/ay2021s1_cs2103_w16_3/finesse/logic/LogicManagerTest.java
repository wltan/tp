package ay2021s1_cs2103_w16_3.finesse.logic;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_AMY;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.TITLE_DESC_AMY;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.AMY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.FinanceTrackerParserTest.ExpensesUiStateStub;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.storage.JsonFinanceTrackerStorage;
import ay2021s1_cs2103_w16_3.finesse.storage.JsonUserPrefsStorage;
import ay2021s1_cs2103_w16_3.finesse.storage.StorageManager;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;
    private ExpensesUiStateStub expensesUiStateStub = new ExpensesUiStateStub();

    @BeforeEach
    public void setUp() {
        JsonFinanceTrackerStorage financeTrackerStorage =
                new JsonFinanceTrackerStorage(temporaryFolder.resolve("fine$$e.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(financeTrackerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listIncomeCommand = ListIncomeCommand.COMMAND_WORD;
        assertCommandSuccess(listIncomeCommand, ListIncomeCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonFinanceTrackerIoExceptionThrowingStub
        JsonFinanceTrackerStorage financeTrackerStorage =
                new JsonFinanceTrackerIoExceptionThrowingStub(
                        temporaryFolder.resolve("ioExceptionFinanceTracker.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(financeTrackerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command - adds an Expense as assertCommandFailure uses the expensesUiStateStub
        String addCommand = AddCommand.COMMAND_WORD + TITLE_DESC_AMY + AMOUNT_DESC_AMY + DATE_DESC_AMY;
        Expense expectedExpense = new TransactionBuilder(AMY).withCategories().buildExpense();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addExpense(expectedExpense);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTransactionList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand, expensesUiStateStub);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand, expensesUiStateStub));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonFinanceTrackerIoExceptionThrowingStub extends JsonFinanceTrackerStorage {
        private JsonFinanceTrackerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFinanceTracker(ReadOnlyFinanceTracker financeTracker, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
