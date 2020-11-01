package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.parser.FinanceTrackerParser;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;

public class ClearCommandTest {

    @Test
    public void execute_emptyFinanceTracker_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        model.getCommandHistory().addCommand("clear");
        expectedModel.getCommandHistory().addCommand("clear");
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_PRIMED, expectedModel, false);
        model.getCommandHistory().addCommand("clear");
        expectedModel.getCommandHistory().addCommand("clear");
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel, true);
    }

    @Test
    public void execute_nonEmptyFinanceTracker_success() {
        Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

        // 1st run - command is primed (model is unchanged)
        model.getCommandHistory().addCommand("clear");
        expectedModel.getCommandHistory().addCommand("clear");
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_PRIMED, expectedModel, false);
        // 2nd run - command clears data
        expectedModel.setFinanceTracker(new FinanceTracker());
        model.getCommandHistory().addCommand("clear");
        expectedModel.getCommandHistory().addCommand("clear");
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel, true);
    }

    @Test
    public void execute_nonEmptyFinanceTracker_intermediateCommand() throws Exception {
        // test to check if the clear command works across multiple uses,
        // when another command is injected in between

        Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

        FinanceTrackerParser cmdParser = new FinanceTrackerParser();
        UiState uiState = new UiState();
        uiState.setCurrentTab(UiState.Tab.INCOME);

        // 1st run - command is primed (model is unchanged)
        model.getCommandHistory().addCommand("clear");
        expectedModel.getCommandHistory().addCommand("clear");
        assertCommandSuccess(cmdParser.parseCommand("clear", uiState), model,
                ClearCommand.MESSAGE_PRIMED, expectedModel, false);

        // intermediate command 'lsi' - prime is reset
        model.getCommandHistory().addCommand("lsi");
        expectedModel.getCommandHistory().addCommand("lsi");

        // 2nd run - command is primed (model is unchanged)
        model.getCommandHistory().addCommand("clear");
        expectedModel.getCommandHistory().addCommand("clear");
        assertCommandSuccess(cmdParser.parseCommand("clear", uiState), model,
                ClearCommand.MESSAGE_PRIMED, expectedModel, false);

        // 3rd run - command clears data
        expectedModel.setFinanceTracker(new FinanceTracker());
        model.getCommandHistory().addCommand("clear");
        expectedModel.getCommandHistory().addCommand("clear");
        assertCommandSuccess(cmdParser.parseCommand("clear", uiState), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel, true);
    }

    @Test
    public void execute_nonEmptyFinanceTracker_invalidIntermediateCommand() throws Exception {
        // test to check if the clear command works across multiple uses,
        // when another (invalid) command is injected in between

        Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

        FinanceTrackerParser cmdParser = new FinanceTrackerParser();
        UiState uiState = new UiState();
        uiState.setCurrentTab(UiState.Tab.INCOME);

        // 1st run - command is primed (model is unchanged)
        model.getCommandHistory().addCommand("clear");
        expectedModel.getCommandHistory().addCommand("clear");
        assertCommandSuccess(cmdParser.parseCommand("clear", uiState), model,
                ClearCommand.MESSAGE_PRIMED, expectedModel, false);

        // invalid intermediate command 'foo' - prime is reset
        model.getCommandHistory().addCommand("foo");
        expectedModel.getCommandHistory().addCommand("foo");

        // 2nd run - command is primed (model is unchanged)
        model.getCommandHistory().addCommand("clear");
        expectedModel.getCommandHistory().addCommand("clear");
        assertCommandSuccess(cmdParser.parseCommand("clear", uiState), model,
                ClearCommand.MESSAGE_PRIMED, expectedModel, false);

        // 3rd run - command clears data
        expectedModel.setFinanceTracker(new FinanceTracker());
        model.getCommandHistory().addCommand("clear");
        expectedModel.getCommandHistory().addCommand("clear");
        assertCommandSuccess(cmdParser.parseCommand("clear", uiState), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel, true);
    }

}
