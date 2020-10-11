package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.Messages;
import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Deletes a transaction identified using its displayed index from the finance tracker
 * depending on the tab the user is on.
 *
 * Base class for DeleteExpenseCommand and DeleteIncomeCommand.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the transaction identified by the index number used in "
            + "the displayed transaction list on the current tab.\n"
            + "When on Income tab: Deletes from the currently displayed income list.\n"
            + "When on Expenses tab: Deletes from the currently displayed expenses list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted Transaction: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    protected Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTransaction(transactionToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
