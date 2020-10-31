package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;

/**
 * Clears the finance tracker.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Finance tracker has been cleared!";
    public static final String MESSAGE_PRIMED = "Please enter 'clear' again to confirm your decision.";

    // maintains whether or not
    private static boolean isPrimed = false;

    public static void resetPrimed() {
        isPrimed = false;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isPrimed) {
            isPrimed = false;
            model.setFinanceTracker(new FinanceTracker());
            return new CommandResult(MESSAGE_SUCCESS, true);
        } else {
            // set primed
            isPrimed = true;
            return new CommandResult(MESSAGE_PRIMED);
        }
    }
}
