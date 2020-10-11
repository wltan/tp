package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.Messages;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;

/**
 * Deletes an income identified using its displayed index from the finance tracker.
 */
public class DeleteIncomeCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted Income: %1$s";

    public DeleteIncomeCommand(DeleteCommand superCommand) {
        super(superCommand.getTargetIndex());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Income> lastShownList = model.getFilteredIncomeList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Income incomeToDelete = lastShownList.get(getTargetIndex().getZeroBased());
        model.deleteIncome(incomeToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, incomeToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIncomeCommand // instanceof handles nulls
                && getTargetIndex().equals(((DeleteIncomeCommand) other).getTargetIndex())); // state check
    }
}
