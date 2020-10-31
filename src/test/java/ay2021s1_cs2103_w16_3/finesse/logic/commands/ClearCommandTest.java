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

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_PRIMED, expectedModel, false);
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel, true);
    }

    @Test
    public void execute_nonEmptyFinanceTracker_success() {
        Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

        // 1st run - command is primed (model is unchanged)
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_PRIMED, expectedModel, false);
        // 2nd run - command clears data
        expectedModel.setFinanceTracker(new FinanceTracker());
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
        assertCommandSuccess(cmdParser.parseCommand("clear", uiState), model,
                ClearCommand.MESSAGE_PRIMED, expectedModel, false);

        // intermediate command 'lsi' - prime is reset
        assertCommandSuccess(cmdParser.parseCommand("lsi", uiState), model,
                new ListIncomeCommand().execute(model), expectedModel);

        // 2nd run - command is primed (model is unchanged)
        assertCommandSuccess(cmdParser.parseCommand("clear", uiState), model,
                ClearCommand.MESSAGE_PRIMED, expectedModel, false);

        // 3rd run - command clears data
        expectedModel.setFinanceTracker(new FinanceTracker());
        assertCommandSuccess(cmdParser.parseCommand("clear", uiState), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel, true);
    }

}
