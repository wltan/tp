package ay2021s1_cs2103_w16_3.finesse.logic.parser.budgetparsers;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.budget.SetSavingsGoalCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Parser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;

/**
 * Parses input arguments and creates a new SetSavingsGoalCommand object.
 */
public class SetSavingsGoalCommandParser implements Parser<SetSavingsGoalCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetSavingsGoalCommand
     * and returns a SetSavingsGoalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetSavingsGoalCommand parse(String args) throws ParseException {
        try {
            Amount amount = ParserUtil.parseAmount(args);
            return new SetSavingsGoalCommand(amount);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetSavingsGoalCommand.MESSAGE_USAGE), pe);
        }
    }

}
