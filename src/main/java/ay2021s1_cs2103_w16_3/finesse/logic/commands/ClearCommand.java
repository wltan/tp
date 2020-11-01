package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.logic.parser.FinanceTrackerParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;

/**
 * Clears the finance tracker.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Finance tracker has been cleared!";
    public static final String MESSAGE_PRIMED = "Please enter 'clear' again to confirm your decision.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<String> recentCommands = model.getCommandHistory().recentCommands(20);
        boolean isPrimed;
        try {
            isPrimed = recentCommands.size() >= 2 && new FinanceTrackerParser()
                    .parseCommand(recentCommands.get(1), new UiState()) instanceof ClearCommand;
        } catch (ParseException e) {
            isPrimed = false;
        }
        if (isPrimed) {
            model.setFinanceTracker(new FinanceTracker());
            return new CommandResult(MESSAGE_SUCCESS, true);
        } else {
            // set primed
            return new CommandResult(MESSAGE_PRIMED);
        }
    }
}
